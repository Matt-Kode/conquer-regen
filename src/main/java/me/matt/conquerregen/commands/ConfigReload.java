package me.matt.conquerregen.commands;

import me.matt.conquerregen.ConquerRegen;
import me.matt.conquerregen.Settings;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ConfigReload implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)  {
        if (label.equalsIgnoreCase("conquerregen")) {
            if (args.length == 0) {
                return true;
            }
            if (args[0].equalsIgnoreCase("reload")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8(&a&l!&8)&a ConquerRegen config reloaded!"));
                ConquerRegen.getPlugin().config.reloadConfig();
                Settings.initSettings();
                return true;
            }
        }
        return false;
    }


}
