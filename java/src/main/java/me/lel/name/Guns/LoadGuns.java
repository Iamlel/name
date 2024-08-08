package me.lel.name.Guns;

import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;

import me.lel.name.DataManager;
import me.lel.name.Main;

public class LoadGuns {

    public static void load() {
        Main main = Main.getInstance();
        DataManager guns = main.getGuns();
        FileConfiguration gunFile = guns.getFile();
        boolean example = false;

        Gun.clearGuns();
        for (int id : gunFile.getKeys(false).stream().mapToInt(Integer::parseInt).toArray()) {
            if (id < 0) {
                if (id == -1) { example = true; }
                else { main.getLogger().log(Level.WARNING, "Invalid gun id: " + id); }
                continue;
            }
            new Gun(id, 
                            gunFile.getDouble(id + ".damage"), 
                            gunFile.getLong(id + ".fireRate"),
                            gunFile.getInt(id + ".clipSize"),
                            gunFile.getLong(id + ".reloadTime"), 
                            gunFile.getLong(id + ".bulletSpeed"), 
                            gunFile.getInt(id + ".bulletSize"),
                            gunFile.getInt(id + ".bulletDispersion"),
                            gunFile.getInt(id + ".bulletAmount"),
                            gunFile.getDouble(id + ".bulletDistance"),
                            gunFile.getString(id + ".name"), 
                            gunFile.getStringList(id + ".description"));

        }

        if (!example) {
            for (String value : exampleGun) { gunFile.set(value, 1); }
            gunFile.set("-1.description", exampleDescription);
            guns.saveFile();
            guns.reloadFile();
        }
    }

    private final static String[] exampleGun = {
        "-1.damage",
        "-1.fireRate",
        "-1.clipSize",
        "-1.reloadTime",
        "-1.bulletSpeed",
        "-1.bulletSize",
        "-1.bulletDispersion",
        "-1.bulletAmount",
        "-1.bulletDistance",
        "-1.name",
    };
    
    private final static String[] exampleDescription = {
        "This is an example gun",
        "It does 1 damage",
        "It has a range of 1",
    };
}
