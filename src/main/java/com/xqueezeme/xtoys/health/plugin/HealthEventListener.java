package com.xqueezeme.xtoys.health.plugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.UUID;

import static com.xqueezeme.xtoys.health.plugin.Utils.isPlayerEnabledXToys;

public class HealthEventListener implements Listener {
    public static final long DELAY = 1L;
    private XToysHealthPlugin xToysHealthPlugin;

    public HealthEventListener(XToysHealthPlugin xToysHealthPlugin) {
        this.xToysHealthPlugin = xToysHealthPlugin;
    }



    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityLoseHealth(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (!player.isDead() && isPlayerEnabledXToys(player.getUniqueId())) {
                Bukkit.getScheduler().runTaskLater(xToysHealthPlugin, new Runnable() {
                    @Override
                    public void run() {
                        double damage = event.getDamage();
                        double maxHealth = Utils.getMaxHealth(player);
                        double health = Utils.round(player.getHealth(), 1);
                        if (health > 0) {
                            XToysEvent xToysEvent = new XToysEvent(XToysEvent.Type.DAMAGE, player.getName(), health, maxHealth);
                            xToysEvent.setAmount(damage);
                            XToysHealthPlugin.X_TOYS_EVENT_SERVICE.fire(XToysHealthPlugin.configurationData.getPlayerMap().get(player.getUniqueId()).getWebhookId(), xToysEvent);
                        }
                        event.setCancelled(false);
                    }
                }, DELAY);

            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityRegainHealth(EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (!player.isDead() && isPlayerEnabledXToys(player.getUniqueId())) {
                Bukkit.getScheduler().runTaskLater(xToysHealthPlugin, new Runnable() {
                    @Override
                    public void run() {
                        double regainAmount = event.getAmount();
                        double health = Utils.round(player.getHealth(), 1);
                        double maxHealth = Utils.getMaxHealth(player);

                        XToysEvent xToysEvent = new XToysEvent(XToysEvent.Type.HEAL, player.getName(), health, maxHealth);
                        xToysEvent.setAmount(regainAmount);
                        XToysHealthPlugin.X_TOYS_EVENT_SERVICE.fire(XToysHealthPlugin.configurationData.getPlayerMap().get(player.getUniqueId()).getWebhookId(), xToysEvent);
                    }
                }, DELAY);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDeath(PlayerDeathEvent playerDeathEvent) {
        Player player = playerDeathEvent.getEntity();
        if (isPlayerEnabledXToys(player.getUniqueId())) {
            Bukkit.getScheduler().runTaskLater(xToysHealthPlugin, new Runnable() {
                @Override
                public void run() {
                    double health = Utils.round(player.getHealth(), 1);
                    double maxHealth = Utils.getMaxHealth(player);

                    XToysEvent xToysEvent = new XToysEvent(XToysEvent.Type.DEATH, player.getName(), health, maxHealth);
                    XToysHealthPlugin.X_TOYS_EVENT_SERVICE.fire(XToysHealthPlugin.configurationData.getPlayerMap().get(player.getUniqueId()).getWebhookId(), xToysEvent);
                }
            }, DELAY);

        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerSpawn(PlayerRespawnEvent playerRespawnEvent) {
        Player player = playerRespawnEvent.getPlayer();
        if (isPlayerEnabledXToys(player.getUniqueId())) {
            Bukkit.getScheduler().runTaskLater(xToysHealthPlugin, new Runnable() {
                @Override
                public void run() {
                    double health = Utils.round(player.getHealth(), 1);
                    double maxHealth = Utils.getMaxHealth(player);

                    XToysEvent xToysEvent = new XToysEvent(XToysEvent.Type.SPAWN, player.getName(), health, maxHealth);
                    XToysHealthPlugin.X_TOYS_EVENT_SERVICE.fire(XToysHealthPlugin.configurationData.getPlayerMap().get(player.getUniqueId()).getWebhookId(), xToysEvent);
                }
            }, DELAY);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {
        Player player = playerJoinEvent.getPlayer();
        if (!player.isDead() && isPlayerEnabledXToys(player.getUniqueId())) {
            Bukkit.getScheduler().runTaskLater(xToysHealthPlugin, new Runnable() {
                @Override
                public void run() {
                    double health = Utils.round(player.getHealth(), 1);
                    double maxHealth = Utils.getMaxHealth(player);

                    XToysEvent xToysEvent = new XToysEvent(XToysEvent.Type.SPAWN, player.getName(), health, maxHealth);
                    XToysHealthPlugin.X_TOYS_EVENT_SERVICE.fire(XToysHealthPlugin.configurationData.getPlayerMap().get(player.getUniqueId()).getWebhookId(), xToysEvent);
                }
            }, DELAY);

        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerLeave(PlayerQuitEvent playerQuitEvent) {
        Player player = playerQuitEvent.getPlayer();
        if (!player.isDead() && isPlayerEnabledXToys(player.getUniqueId())) {
            Bukkit.getScheduler().runTaskLater(xToysHealthPlugin, new Runnable() {
                @Override
                public void run() {
                    double health = Utils.round(player.getHealth(), 1);
                    double maxHealth = Utils.getMaxHealth(player);
                    XToysEvent xToysEvent = new XToysEvent(XToysEvent.Type.LEAVE, player.getName(), health, maxHealth);
                    XToysHealthPlugin.X_TOYS_EVENT_SERVICE.fire(XToysHealthPlugin.configurationData.getPlayerMap().get(player.getUniqueId()).getWebhookId(), xToysEvent);
                }
            }, DELAY);
        }
    }


}
