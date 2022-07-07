package com.xqueezeme.xtoys.health.plugin;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class HealthEventListener implements Listener {

    @EventHandler
    public void onEntityLoseHealth(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if(XToysHealthPlugin.configurationData != null &&
                    XToysHealthPlugin.configurationData.getPlayerMap() != null &&
                    XToysHealthPlugin.configurationData.getPlayerMap().containsKey(player.getName())) {
                double damage = event.getFinalDamage();
                double health = player.getHealth() - damage;
                XToysEvent xToysEvent = new XToysEvent(XToysEvent.Type.DAMAGE, player.getName(),health);
                xToysEvent.setAmount(damage);
                XToysHealthPlugin.X_TOYS_EVENT_SERVICE.fire(XToysHealthPlugin.configurationData.getPlayerMap().get(player.getName()), xToysEvent);
            }
        }
    }

    @EventHandler
    public void onEntityRegainHealth(EntityRegainHealthEvent event) {
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if(XToysHealthPlugin.configurationData != null &&
                    XToysHealthPlugin.configurationData.getPlayerMap() != null &&
                    XToysHealthPlugin.configurationData.getPlayerMap().containsKey(player.getName())) {
                double regainAmount = event.getAmount();
                double health = player.getHealth() + regainAmount;
                XToysEvent xToysEvent = new XToysEvent(XToysEvent.Type.HEAL, player.getName(), health);
                xToysEvent.setAmount(regainAmount);
                XToysHealthPlugin.X_TOYS_EVENT_SERVICE.fire(XToysHealthPlugin.configurationData.getPlayerMap().get(player.getName()), xToysEvent);
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent playerDeathEvent) {
        Player player = playerDeathEvent.getEntity();
        if(XToysHealthPlugin.configurationData != null &&
                XToysHealthPlugin.configurationData.getPlayerMap() != null &&
                XToysHealthPlugin.configurationData.getPlayerMap().containsKey(player.getName())) {
            double health = player.getHealth();
            XToysEvent xToysEvent = new XToysEvent(XToysEvent.Type.DEATH, player.getName(), health);
            XToysHealthPlugin.X_TOYS_EVENT_SERVICE.fire(XToysHealthPlugin.configurationData.getPlayerMap().get(player.getName()), xToysEvent);

        }
    }

    @EventHandler
    public void onPlayerSpawn(PlayerRespawnEvent playerRespawnEvent) {
        Player player = playerRespawnEvent.getPlayer();
        if(XToysHealthPlugin.configurationData != null &&
                        XToysHealthPlugin.configurationData.getPlayerMap() != null &&
                        XToysHealthPlugin.configurationData.getPlayerMap().containsKey(player.getName())) {
            double health = player.getHealth();
            XToysEvent xToysEvent = new XToysEvent(XToysEvent.Type.SPAWN, player.getName(), health);
            XToysHealthPlugin.X_TOYS_EVENT_SERVICE.fire(XToysHealthPlugin.configurationData.getPlayerMap().get(player.getName()), xToysEvent);

        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {
        Player player = playerJoinEvent.getPlayer();
        if(!player.isDead() && XToysHealthPlugin.configurationData != null &&
                XToysHealthPlugin.configurationData.getPlayerMap() != null &&
                XToysHealthPlugin.configurationData.getPlayerMap().containsKey(player.getName())) {
            double health = player.getHealth();
            XToysEvent xToysEvent = new XToysEvent(XToysEvent.Type.SPAWN, player.getName(), health);
            XToysHealthPlugin.X_TOYS_EVENT_SERVICE.fire(XToysHealthPlugin.configurationData.getPlayerMap().get(player.getName()), xToysEvent);
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent playerQuitEvent) {
        Player player = playerQuitEvent.getPlayer();
        if(!player.isDead() && XToysHealthPlugin.configurationData != null &&
                XToysHealthPlugin.configurationData.getPlayerMap() != null &&
                XToysHealthPlugin.configurationData.getPlayerMap().containsKey(player.getName())) {
            double health = player.getHealth();
            XToysEvent xToysEvent = new XToysEvent(XToysEvent.Type.LEAVE, player.getName(), health);
            XToysHealthPlugin.X_TOYS_EVENT_SERVICE.fire(XToysHealthPlugin.configurationData.getPlayerMap().get(player.getName()), xToysEvent);
        }
    }
}
