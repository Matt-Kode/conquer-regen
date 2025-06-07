package me.matt.conquerregen.listeners;

import me.matt.conquerregen.ConquerRegen;
import me.matt.conquerregen.Cache;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.*;

public class EntityDeath implements Listener {

    public static Map<Location, EntityType> respawn = new HashMap<>();


    @EventHandler
    public void onentitydeath(EntityDeathEvent e) {

        if (e.getEntity().getLastDamageCause() == null) {
            return;
        }

        EntityDamageEvent.DamageCause cause
                = e.getEntity().getLastDamageCause().getCause();

        if (!(cause == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION || cause == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)) {
            return;
        }
        for (String world : ConquerRegen.getPlugin().config.getConfig().getStringList("disable_in_worlds")) {
            if (e.getEntity().getLocation().getWorld().getName().equalsIgnoreCase(world)) {
                return;
            }
        }

        if (e.getEntity() instanceof Player) {
            return;
        }

        if (e.getEntityType() == EntityType.DROPPED_ITEM) {
            return;
        }

        if (e.getEntityType() == EntityType.ARMOR_STAND) {
            e.getDrops().clear();
            return;
        }

        if (e.getEntity().getType() != EntityType.ARMOR_STAND) {
            e.getDrops().clear();
            Entity entity = e.getEntity();
            EntityType entitytype = e.getEntityType();
            Location loc = e.getEntity().getLocation();

            Cache.entities.put(entity.getUniqueId() + " " + loc.getWorld().getName() + " " +loc.getX() + " " + loc.getY() + " " + loc.getZ(), entitytype);


        }
                }

        }

