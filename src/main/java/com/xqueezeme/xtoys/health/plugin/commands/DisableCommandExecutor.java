package com.xqueezeme.xtoys.health.plugin.commands;

import com.xqueezeme.xtoys.health.plugin.Utils;
import com.xqueezeme.xtoys.health.plugin.XToysHealthPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DisableCommandExecutor implements CommandExecutor {
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        String name = commandSender.getName();
        if(XToysHealthPlugin.configurationData != null && XToysHealthPlugin.configurationData.getPlayerMap() != null) {
            Player player = commandSender.getServer().getPlayer(name);
            if(player != null && XToysHealthPlugin.configurationData != null &&
                    XToysHealthPlugin.configurationData.getPlayerMap() != null &&
                    XToysHealthPlugin.configurationData.getPlayerMap().containsKey(player.getUniqueId())) {
                XToysHealthPlugin.configurationData.getPlayerMap().get(player.getUniqueId()).setDisabled(true);
                XToysHealthPlugin.configurationData.saveData(XToysHealthPlugin.CONFIGURATION_DATA_FILE_NAME);

                commandSender.sendMessage("Disabled the XToys Health Plugin for " + name);
            } else {
                commandSender.sendMessage("You are not registered.");

            }
        }
        return true;
    }
}
