package com.ak49bwl.blockelevators;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public void onEnable() {
        Elevate elevate = new Elevate();
        getServer().getPluginManager().registerEvents(elevate, this);
        Bukkit.getLogger().info("BlockElevators Loaded, prepare to elevate!");
    }
}
