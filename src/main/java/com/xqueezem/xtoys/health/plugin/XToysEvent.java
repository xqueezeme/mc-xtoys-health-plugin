package com.xqueezem.xtoys.health.plugin;

public class XToysEvent {
    private Type type;
    private String playerName;
    private double health;
    private double amount;

    public XToysEvent(Type type, String playerName, double health) {
        this.type = type;
        this.playerName = playerName;
        this.health = health;
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

    public static enum Type {
        DAMAGE,
        HEAL,
        DEATH,
        LEAVE, SPAWN
    }
}
