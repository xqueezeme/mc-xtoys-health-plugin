package com.xqueezeme.xtoys.health.plugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RegisterCommandExecutor implements CommandExecutor {
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        String name = commandSender.getName();
        if(XToysHealthPlugin.configurationData != null && XToysHealthPlugin.configurationData.getPlayerMap() != null) {
            if(strings.length > 0) {
                XToysHealthPlugin.configurationData.getPlayerMap().put(name, strings[0]);
                XToysHealthPlugin.configurationData.saveData(XToysHealthPlugin.CONFIGURATION_DATA_FILE_NAME);
                Player player = commandSender.getServer().getPlayer(name);
                double maxHealth = Utils.getMaxHealth(player);
                XToysEvent xToysEvent = new XToysEvent(XToysEvent.Type.SPAWN, name, Utils.round(player.getHealth(),1), maxHealth);
                XToysHealthPlugin.X_TOYS_EVENT_SERVICE.fire(s, xToysEvent);
            } else {
                commandSender.sendMessage("Please provide the XToys webhookId");
            }
        }
        return true;
    }
}
