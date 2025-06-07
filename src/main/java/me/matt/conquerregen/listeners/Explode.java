package me.matt.conquerregen.listeners;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Town;
import me.matt.conquerregen.ConquerRegen;
import me.matt.conquerregen.Cache;
import me.matt.conquerregen.Settings;
import me.matt.conquerregen.towny.MetaData;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class Explode implements Listener {


    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onexplode(EntityExplodeEvent e) {

        if (!checkWorldRegen(e.getLocation())) {
            return;
        }

        if (!checkTownRegen(e.getLocation())) {
                return;
        }

        List<BlockState> blocks = blockFilter(e.blockList());
        Set<BlockState> validtilestates = null;
        if (Settings.IGNORETILESTATES) {
            validtilestates = tileStateFinder(e.blockList());
        }

        setBlocksToAir(blocks);

        if (Settings.IGNORETILESTATES) {
            tileStateReupdate(validtilestates);
        }

        regenerator(blocks);

        e.setYield(0);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onblockexplode(BlockExplodeEvent e) {

        if (!checkWorldRegen(e.getBlock().getLocation())) {
            return;
        }

        if (!checkTownRegen(e.getBlock().getLocation())) {
            return;
        }

        List<BlockState> blocks = blockFilter(e.blockList());
        Set<BlockState> validtilestates = null;

        if (Settings.IGNORETILESTATES) {
            validtilestates = tileStateFinder(e.blockList());
        }

        setBlocksToAir(blocks);

        if (Settings.IGNORETILESTATES) {
            tileStateReupdate(validtilestates);
        }

        regenerator(blocks);

        e.setYield(0);
    }


    private void tryArmorStands() {
        for (Map.Entry<String, ItemStack[]> map : Cache.armorstands.entrySet()) {
            String[] loc = map.getKey().split(" ");
            ItemStack[] armorset = map.getValue();

            World world = Bukkit.getWorld(loc[1]);

            Location location = new Location(world, Double.parseDouble(loc[2]), Double.parseDouble(loc[3]), Double.parseDouble(loc[4]));

            ArmorStand stand = (ArmorStand) world.spawnEntity(location, EntityType.ARMOR_STAND);

            stand.getEquipment().setHelmet(armorset[0]);
            stand.getEquipment().setChestplate(armorset[1]);
            stand.getEquipment().setLeggings(armorset[2]);
            stand.getEquipment().setBoots(armorset[3]);
            stand.getEquipment().setItemInMainHand(armorset[4], true);
            stand.getEquipment().setItemInOffHand(armorset[5], true);
        }
        Cache.armorstands.clear();

    }


    private void tryOtherEntities() {
        for (Map.Entry<String, EntityType> map : Cache.entities.entrySet()) {

            String[] loc = map.getKey().split(" ");
            EntityType entity = map.getValue();

            World world = Bukkit.getWorld(loc[1]);

            Location location = new Location(world, Double.parseDouble(loc[2]), Double.parseDouble(loc[3]), Double.parseDouble(loc[4]));

            world.spawnEntity(location, entity);

        }
        Cache.entities.clear();
    }

    public List<BlockState> blockFilter(List<Block> blockslist) {
        List<BlockState> blocks = new ArrayList<>();
        List<String> blacklist = ConquerRegen.getPlugin().config.getConfig().getStringList("block_blacklist");
        for (Block b : blockslist) {
            if (b.getState() instanceof TileState && Settings.IGNORETILESTATES) {
                continue;
            }
            int i = 1;
            for (String m : blacklist) {
                if (b.getType().equals(Material.valueOf(m))) {
                    break;
                }
                if (i++ == blacklist.toArray().length) {
                    blocks.add(b.getState());
                    Cache.blocks.add(b.getState());
                }
            }
        }
        return blocks;
    }

    public Set<BlockState> tileStateFinder(List<Block> blockslist) {
        Set<BlockState> tilestates = new HashSet<>();
            for (Block b : blockslist) {
                if (b.getState() instanceof TileState) {
                    tilestates.add(b.getState());
                }
            }
        return tilestates;
    }

    public void setBlocksToAir(List<BlockState> blockslist) {
        for (BlockState b : blockslist) {
            if (b.getLocation().getBlock().getType() != Material.AIR) {
                Block block = b.getBlock();
                block.setType(Material.AIR);
                block.setMetadata("Conquer_Regen", new FixedMetadataValue(ConquerRegen.getPlugin(), "air_block"));
            }
        }
    }

    public void tileStateReupdate(Set<BlockState> tilestates) {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (BlockState state : tilestates) {
                    state.update(true, false);
                    tilestates.remove(state);
                }
            }
        }.runTaskLater(ConquerRegen.getPlugin(), 2);
    }

    public void regenerator(List<BlockState> blockslist) {
        List<BlockState> blocks = new ArrayList<>(blockslist);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (Cache.blocks.isEmpty()) {
                    tryArmorStands();
                    tryOtherEntities();
                    cancel();
                    return;
                }
                if (!blocks.isEmpty()) {
                    BlockState block = blocks.iterator().next();
                    if (block.getBlock().hasMetadata("Conquer_Regen")) {
                        block.update(true,false);
                    }
                    blocks.remove(block);
                    //blockcache is to ensure that entities spawn in after blockstates r regened
                    Cache.blocks.remove(block);
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(ConquerRegen.getPlugin(), Settings.DELAY, Settings.SPEED);
    }

    public boolean checkTownRegen(Location loc) {
        if (ConquerRegen.getPlugin().config.getConfig().getBoolean("towny-integration")) {

            Town t = TownyAPI.getInstance().getTown(loc);

            if (t != null) {
                if (!MetaData.getRegenBoolean(t)) {
                    return false;
                }
            }
        }
        return true;
    }

    public Boolean checkWorldRegen(Location loc) {
        for (String s :
                ConquerRegen.getPlugin().config.getConfig().getStringList("disable_in_worlds")) {

            if (s.equalsIgnoreCase(loc.getWorld().getName())) {
                return false;
            }

        }
        return true;
    }
    }


