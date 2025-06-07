package me.matt.conquerregen;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownyCommandAddonAPI;
import me.matt.conquerregen.commands.ConfigReload;
import me.matt.conquerregen.listeners.*;
import me.matt.conquerregen.files.Config;
import me.matt.conquerregen.towny.Command;
import org.bukkit.plugin.java.JavaPlugin;

public final class ConquerRegen extends JavaPlugin {

    private static ConquerRegen plugin;

    public Config config;

    @Override
    public void onEnable() {

        plugin = this;
        this.config = new Config(this);
        Settings.initSettings();

        //register commands
        this.getCommand("conquerregen").setExecutor(new ConfigReload());

        if (getServer().getPluginManager().getPlugin("Towny") != null) {
            TownyCommandAddonAPI.addSubCommand(TownyCommandAddonAPI.CommandType.TOWN_TOGGLE, "regen", new Command());
        }

        //register events
        this.getServer().getPluginManager().registerEvents(new Explode(), this);
        this.getServer().getPluginManager().registerEvents(new EntityDamage(), this);
        this.getServer().getPluginManager().registerEvents(new EntityDeath(), this);
        this.getServer().getPluginManager().registerEvents(new HangingBreak(), this);

    }

    @Override
    public void onDisable() {

    }


    public static ConquerRegen getPlugin() {
        return plugin;
    }
}
