package com.xqueezeme.xtoys.health.plugin.commands;

import com.xqueezeme.xtoys.health.plugin.PlayerConfiguration;
import com.xqueezeme.xtoys.health.plugin.Utils;
import com.xqueezeme.xtoys.health.plugin.XToysEvent;
import com.xqueezeme.xtoys.health.plugin.XToysHealthPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnableCommandExecutor implements CommandExecutor {
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        String name = commandSender.getName();
        if (XToysHealthPlugin.configurationData != null && XToysHealthPlugin.configurationData.getPlayerMap() != null) {
            Player player = commandSender.getServer().getPlayer(name);
            if (player != null) {
                if (XToysHealthPlugin.configurationData.getPlayerMap().containsKey(player.getUniqueId())) {
                    PlayerConfiguration playerConfiguration = XToysHealthPlugin.configurationData.getPlayerMap().get(player.getUniqueId());
                    playerConfiguration.setDisabled(false);
                    XToysHealthPlugin.configurationData.saveData(XToysHealthPlugin.CONFIGURATION_DATA_FILE_NAME);
                    double maxHealth = Utils.getMaxHealth(player);
                    XToysEvent xToysEvent = new XToysEvent(XToysEvent.Type.SPAWN, name, Utils.round(player.getHealth(), 1), maxHealth);
                    XToysHealthPlugin.X_TOYS_EVENT_SERVICE.fire(s, xToysEvent);
                }
            }
        }
        return true;
    }
}
