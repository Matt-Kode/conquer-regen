package me.matt.conquerregen.listeners;

import me.matt.conquerregen.ConquerRegen;
import me.matt.conquerregen.Cache;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class EntityDamage implements Listener {


    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void entitydamage(EntityDamageEvent e) {

        if (e.getEntity() instanceof Player) {
            return;
        }

        if (e.getDamage() == 0.0) {
            return;
        }

        if (!(e.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION || e.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)) {
            return;
        }

        if (e.getEntity() instanceof ArmorStand) {

            for (String world : ConquerRegen.getPlugin().config.getConfig().getStringList("disable_in_worlds")) {
                if (e.getEntity().getLocation().getWorld().getName().equalsIgnoreCase(world)) {
                    return;
                }
            }
                ArmorStand stand = (ArmorStand) e.getEntity();
                ItemStack[] armor = new ItemStack[6];

                armor[0] = stand.getEquipment().getHelmet();
                armor[1] = stand.getEquipment().getChestplate();
                armor[2] = stand.getEquipment().getLeggings();
                armor[3] = stand.getEquipment().getBoots();
                armor[4] = stand.getEquipment().getItemInMainHand();
                armor[5] = stand.getEquipment().getItemInOffHand();

                Location loc = e.getEntity().getLocation();

            Cache.armorstands.put(e.getEntity().getUniqueId() + " " + loc.getWorld().getName() + " " + loc.getX() + " " + loc.getY() + " " + loc.getZ(), armor);
        }
        }


        }



