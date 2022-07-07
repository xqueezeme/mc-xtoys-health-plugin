package com.xqueezeme.xtoys.health.plugin;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

public class PlayerUtils {
    public static double getMaxHealth(Player player) {
        if(player != null) {
            AttributeInstance maxHeathAttribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            return maxHeathAttribute != null ? maxHeathAttribute.getValue() : null;
        }
        return 0.0d;
    }

}
