package com.xqueezeme.xtoys.health.plugin;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class XToysHealthPlugin extends JavaPlugin {
    public static final String CONFIGURATION_DATA_FILE_NAME = "xtoys-health.data";
    public static ConfigurationData configurationData = null;
    private final static HealthEventListener HEALTH_EVENT_LISTENER = new HealthEventListener();
    public static XToysEventService X_TOYS_EVENT_SERVICE = null;

    @Override
    public void onEnable() {
        this.getCommand("xtoys-register").setExecutor(new RegisterCommandExecutor());
        this.getCommand("xtoys-unregister").setExecutor(new UnregisterCommandExecutor());
        getServer().getPluginManager().registerEvents(HEALTH_EVENT_LISTENER, this);
        X_TOYS_EVENT_SERVICE = new XToysEventService(getLogger());;
        getLogger().info("onEnable is called!");
        configurationData = ConfigurationData.loadData(CONFIGURATION_DATA_FILE_NAME);
    }
    @Override
    public void onDisable() {
        getLogger().info("onDisable is called!");
        this.getCommand("xtoys-register").setExecutor(null);
        this.getCommand("xtoys-unregister").setExecutor(null);
        HandlerList.unregisterAll(HEALTH_EVENT_LISTENER);
        configurationData.saveData(CONFIGURATION_DATA_FILE_NAME);

    }


}
