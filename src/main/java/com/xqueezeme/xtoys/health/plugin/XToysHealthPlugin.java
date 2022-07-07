package com.xqueezeme.xtoys.health.plugin;

import com.xqueezeme.xtoys.health.plugin.commands.DisableCommandExecutor;
import com.xqueezeme.xtoys.health.plugin.commands.EnableCommandExecutor;
import com.xqueezeme.xtoys.health.plugin.commands.RegisterCommandExecutor;
import com.xqueezeme.xtoys.health.plugin.commands.UnregisterCommandExecutor;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class XToysHealthPlugin extends JavaPlugin {
    public static final String CONFIGURATION_DATA_FILE_NAME = "xtoys-health-plugin.data";
    public static ConfigurationData configurationData = null;
    private final HealthEventListener HEALTH_EVENT_LISTENER = new HealthEventListener(this);
    public static XToysEventService X_TOYS_EVENT_SERVICE = null;

    @Override
    public void onEnable() {
        this.getCommand("xtoys-register").setExecutor(new RegisterCommandExecutor());
        this.getCommand("xtoys-unregister").setExecutor(new UnregisterCommandExecutor());
        this.getCommand("xtoys-enable").setExecutor(new EnableCommandExecutor());
        this.getCommand("xtoys-disable").setExecutor(new DisableCommandExecutor());

        getServer().getPluginManager().registerEvents(HEALTH_EVENT_LISTENER, this);
        X_TOYS_EVENT_SERVICE = new XToysEventService(getLogger());;
        configurationData = ConfigurationData.loadData(CONFIGURATION_DATA_FILE_NAME);
    }

    @Override
    public void onDisable() {
        this.getCommand("xtoys-register").setExecutor(null);
        this.getCommand("xtoys-unregister").setExecutor(null);
        this.getCommand("xtoys-enable").setExecutor(null);
        this.getCommand("xtoys-disable").setExecutor(null);

        HandlerList.unregisterAll(HEALTH_EVENT_LISTENER);
        configurationData.saveData(CONFIGURATION_DATA_FILE_NAME);
    }
}
