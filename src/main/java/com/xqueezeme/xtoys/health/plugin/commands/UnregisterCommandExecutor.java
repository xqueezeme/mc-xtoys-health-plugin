package com.xqueezeme.xtoys.health.plugin.commands;

import com.xqueezeme.xtoys.health.plugin.Utils;
import com.xqueezeme.xtoys.health.plugin.XToysEvent;
import com.xqueezeme.xtoys.health.plugin.XToysHealthPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnregisterCommandExecutor implements CommandExecutor {
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        String name = commandSender.getName();
        if(XToysHealthPlugin.configurationData != null && XToysHealthPlugin.configurationData.getPlayerMap() != null) {
            Player player = commandSender.getServer().getPlayer(name);
            if(player != null) {
                if (XToysHealthPlugin.configurationData != null &&
                        XToysHealthPlugin.configurationData.getPlayerMap() != null &&
                        XToysHealthPlugin.configurationData.getPlayerMap().containsKey(player.getUniqueId())) {
                    double health = player.getHealth();
                    double maxHealth = Utils.getMaxHealth(player);
                    XToysEvent xToysEvent = new XToysEvent(XToysEvent.Type.LEAVE, player.getName(), health, maxHealth);
                    XToysHealthPlugin.X_TOYS_EVENT_SERVICE.fire(XToysHealthPlugin.configurationData.getPlayerMap().get(player.getUniqueId()).getWebhookId(), xToysEvent);
                    XToysHealthPlugin.configurationData.getPlayerMap().remove(player.getUniqueId());
                    XToysHealthPlugin.configurationData.saveData(XToysHealthPlugin.CONFIGURATION_DATA_FILE_NAME);
                    commandSender.sendMessage("Unregistered user " + commandSender.getName());
                } else {
                    commandSender.sendMessage("You are not registered.");

                }
            }
        }
        return true;
    }
}
