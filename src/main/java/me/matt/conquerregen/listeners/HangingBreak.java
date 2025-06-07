package me.matt.conquerregen.listeners;

import me.matt.conquerregen.ConquerRegen;
import me.matt.conquerregen.Settings;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakEvent;

public class HangingBreak implements Listener {


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onhangingbreak(HangingBreakEvent e) {

        if (!Settings.HANGINGPROTECTION) {
            return;
        }

        if (!(e.getCause() == HangingBreakEvent.RemoveCause.EXPLOSION || e.getCause() == HangingBreakEvent.RemoveCause.PHYSICS)) {
            return;
        }

        for (String world : ConquerRegen.getPlugin().config.getConfig().getStringList("disable_in_worlds")) {
            if (e.getEntity().getLocation().getWorld().getName().equalsIgnoreCase(world)) {
                return;
            }
        }

        e.setCancelled(true);

    }
}
