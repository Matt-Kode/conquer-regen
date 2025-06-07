package me.matt.conquerregen.towny;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Town;
import me.matt.conquerregen.Utils;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String s, String[] strings) {

        if (sender instanceof Player) {

            Player p = (Player) sender;
            Town t = TownyAPI.getInstance().getTown(p);

            if (strings.length == 0) {
                if (MetaData.getRegenBoolean(t)) {
                    MetaData.setRegenBoolean(t, false);
                    sender.sendMessage(Utils.format("&cRegen in " + t.getName() + " is now Disabled."));
                } else {
                    MetaData.setRegenBoolean(t, true);
                    sender.sendMessage(Utils.format("&cRegen in " + t.getName() + " is now Enabled."));
                }
                return true;

            }

            if (strings.length > 0) {
                if (strings[0].equalsIgnoreCase("on")) {
                    if (MetaData.getRegenBoolean(t)) {
                        sender.sendMessage(Utils.format("&cRegen in " + t.getName() + " is now Enabled."));
                        return true;
                    }
                    MetaData.setRegenBoolean(t, true);
                    sender.sendMessage(Utils.format("&cRegen in " + t.getName() + " is now Enabled."));
                    return true;
                }

                if (strings[0].equalsIgnoreCase("off")) {
                    if (!MetaData.getRegenBoolean(t)) {
                        sender.sendMessage(Utils.format("&cRegen in " + t.getName() + " is now Disabled."));
                        return true;
                    }
                    MetaData.setRegenBoolean(t, false);
                    sender.sendMessage(Utils.format("&cRegen in " + t.getName() + " is now Disabled."));
                    return true;
                }
            }

        }
        return false;
    }
}
