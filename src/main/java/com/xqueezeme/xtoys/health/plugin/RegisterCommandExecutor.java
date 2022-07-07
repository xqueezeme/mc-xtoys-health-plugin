package com.xqueezeme.xtoys.health.plugin;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RegisterCommandExecutor implements CommandExecutor {
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        String name = commandSender.getName();
        if(XToysHealthPlugin.configurationData != null && XToysHealthPlugin.configurationData.getPlayerMap() != null) {
            XToysHealthPlugin.configurationData.getPlayerMap().put(name, s);
            XToysHealthPlugin.configurationData.saveData(XToysHealthPlugin.CONFIGURATION_DATA_FILE_NAME);
            Player player = commandSender.getServer().getPlayer(name);
            double maxHealth = PlayerUtils.getMaxHealth(player);
            XToysEvent xToysEvent = new XToysEvent(XToysEvent.Type.SPAWN, name, player.getHealth(), maxHealth);
            XToysHealthPlugin.X_TOYS_EVENT_SERVICE.fire(s, xToysEvent);
        }
        return true;
    }
}
