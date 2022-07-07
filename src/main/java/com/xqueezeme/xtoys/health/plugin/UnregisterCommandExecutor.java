package com.xqueezeme.xtoys.health.plugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnregisterCommandExecutor implements CommandExecutor {
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        String name = commandSender.getName();
        if(XToysHealthPlugin.configurationData != null && XToysHealthPlugin.configurationData.getPlayerMap() != null) {
            Player player = commandSender.getServer().getPlayer(name);
            if(XToysHealthPlugin.configurationData != null &&
                    XToysHealthPlugin.configurationData.getPlayerMap() != null &&
                    XToysHealthPlugin.configurationData.getPlayerMap().containsKey(player.getName())) {
                double health = player.getHealth();
                double maxHealth = PlayerUtils.getMaxHealth(player);
                XToysEvent xToysEvent = new XToysEvent(XToysEvent.Type.LEAVE, player.getName(), health, maxHealth);
                XToysHealthPlugin.X_TOYS_EVENT_SERVICE.fire(XToysHealthPlugin.configurationData.getPlayerMap().get(player.getName()), xToysEvent);
                XToysHealthPlugin.configurationData.getPlayerMap().remove(name);
                XToysHealthPlugin.configurationData.saveData(XToysHealthPlugin.CONFIGURATION_DATA_FILE_NAME);
            }
        }
        return true;
    }
}
