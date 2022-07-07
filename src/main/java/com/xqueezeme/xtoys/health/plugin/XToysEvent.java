package com.xqueezeme.xtoys.health.plugin;

public class XToysEvent {
    private Type type;
    private String playerName;
    private double health;
    private double maxHealth;
    private double amount;

    public XToysEvent(Type type, String playerName, double health, double maxHealth) {
        this.type = type;
        this.playerName = playerName;
        this.health = health;
        this.maxHealth = maxHealth;
    }


    public Type getType() {
        return type;
    }

    public String getPlayerName() {
        return playerName;
    }


    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public static enum Type {
        DAMAGE,
        HEAL,
        DEATH,
        LEAVE,
        SPAWN
    }
}
