package me.lel.name;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class DataManager {

    private FileConfiguration config;
    private File file;
    private Main main;

    public DataManager(String filename) {
        this.main = Main.getInstance();
        this.file = new File(main.getDataFolder(), filename);

        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (Exception e) {
                main.getLogger().log(Level.CONFIG, "Could not create file " + filename);
                e.printStackTrace();
            }
        }
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public void reloadFile() {
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getFile() {
        return config;
    }

    public boolean saveFile() {
        try {
            config.save(file);
        } catch (IOException e) {
            main.getLogger().log(Level.CONFIG, "Could not save file " + file.getName());
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
