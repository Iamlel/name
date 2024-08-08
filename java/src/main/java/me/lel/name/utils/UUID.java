package me.lel.name.utils;

import org.bukkit.configuration.file.FileConfiguration;

import me.lel.name.DataManager;

public class UUID {

    private static int currentID = 0;
    private static byte dateFactor;
    
    public static int newUUID() {
        return (currentID++);
    }

    public static byte getDateFactorOutdated() {
        return dateFactor;
    }

    public static void newDateFactor(DataManager manager) {
        FileConfiguration file = manager.getFile();
        dateFactor = (byte) (1 - file.getInt("dateFactor"));
        file.set("dateFactor", dateFactor);
        manager.saveFile();
    }
}
