package fr.supermax_8;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;

public final class RecursiveRTP extends JavaPlugin {

    public static String errormessage;
    public static String errormessage2;
    public static int radius;
    public static int iteration;

    public static HashSet<String> worlds = new HashSet<>();

    @Override
    public void onEnable() {
        getCommand("rtp").setExecutor(new RTPCommand());

        saveDefaultConfig();
        errormessage = getConfig().getString("errormessage");
        errormessage2 = getConfig().getString("errormessage2");
        radius = getConfig().getInt("radius");
        iteration = getConfig().getInt("iteration");
        worlds.addAll(getConfig().getStringList("worlds"));

        new Metrics(this, 17235);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


}