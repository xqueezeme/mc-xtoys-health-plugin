package com.xqueezeme.xtoys.health.plugin;

import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class ConfigurationData implements Serializable {
    private static transient final long serialVersionUID = -1681012206529286330L;

    private Map<UUID, PlayerConfiguration> playerMap = new HashMap<>();

    public ConfigurationData() {
    }

    public Map<UUID, PlayerConfiguration> getPlayerMap() {
        return playerMap;
    }

    public void setPlayerMap(Map<UUID, PlayerConfiguration> playerMap) {
        this.playerMap = playerMap;
    }


    public boolean saveData(String filePath) {
        try {
            BukkitObjectOutputStream out = new BukkitObjectOutputStream(new GZIPOutputStream(new FileOutputStream(filePath)));
            out.writeObject(this);
            out.close();
            return true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    public static ConfigurationData loadData(String filePath) {
        try {
            BukkitObjectInputStream in = new BukkitObjectInputStream(new GZIPInputStream(new FileInputStream(filePath)));
            ConfigurationData data = (ConfigurationData) in.readObject();
            in.close();
            return data;
        } catch (ClassNotFoundException | IOException e) {
            return new ConfigurationData();
        }
    }
}
