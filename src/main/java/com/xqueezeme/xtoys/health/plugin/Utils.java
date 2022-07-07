package com.xqueezeme.xtoys.health.plugin;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Utils {
    public static double getMaxHealth(Player player) {
        if(player != null) {
            AttributeInstance maxHeathAttribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            return maxHeathAttribute != null ? maxHeathAttribute.getValue() : null;
        }
        return 0.0d;
    }
    public static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    public static boolean isPlayerEnabledXToys(UUID playerId) {
        return XToysHealthPlugin.configurationData != null &&
                XToysHealthPlugin.configurationData.getPlayerMap() != null &&
                XToysHealthPlugin.configurationData.getPlayerMap().containsKey(playerId) &&
                !XToysHealthPlugin.configurationData.getPlayerMap().get(playerId).isDisabled();
    }
}
