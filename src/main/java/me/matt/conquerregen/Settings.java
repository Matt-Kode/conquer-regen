package me.matt.conquerregen;

public class Settings {

    public static Boolean IGNORETILESTATES;
    public static int SPEED;
    public static int DELAY;
    public static Boolean HANGINGPROTECTION;
    public static boolean ITEMSADDER;

    public static void initSettings() {

        IGNORETILESTATES = ConquerRegen.getPlugin().config.getConfig().getBoolean("ignore_tilestates");
        SPEED = ConquerRegen.getPlugin().config.getConfig().getInt("speed");
        DELAY = ConquerRegen.getPlugin().config.getConfig().getInt("delay");
        HANGINGPROTECTION = ConquerRegen.getPlugin().config.getConfig().getBoolean("hanging_protection");

    }




}
