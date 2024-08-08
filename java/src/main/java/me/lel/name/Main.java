package me.lel.name;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.lel.name.Guns.LoadGuns;
import me.lel.name.commands.getgun.GetGun;
import me.lel.name.commands.getgun.GetGunTab;
import me.lel.name.commands.name.Name;
import me.lel.name.commands.name.NameTab;
import me.lel.name.commands.zombiesurvival.ZombieSurvival;
import me.lel.name.commands.zombiesurvival.ZombieSurvivalTab;
import me.lel.name.events.ChunkLoad;
import me.lel.name.events.Click;
import me.lel.name.events.EntityDeath;
import me.lel.name.events.EntityTarget;
import me.lel.name.events.PlayerJoin;
import me.lel.name.utils.UUID;

public final class Main extends JavaPlugin {

    private static Main instance;
    private DataManager guns;
    private DataManager config;
    private DataManager data;

    @Override
    public void onEnable() { // TODO MAKE MOBS TARGET NEARBY PLAYERS WHEN SPAWNED AND CHECK IF THEY ARE TARGETING THE NEAREST PLAYER EVERY SECOND
        instance = this;

        this.guns = new DataManager("guns.yml");
        this.config = new DataManager("config.yml");
        this.data = new DataManager("data.yml");
        
        getCommand("ZombieSurvival").setExecutor(new ZombieSurvival());
        getCommand("Name").setExecutor(new Name());
        getCommand("GetGun").setExecutor(new GetGun());

        getCommand("ZombieSurvival").setTabCompleter(new ZombieSurvivalTab());
        getCommand("Name").setTabCompleter(new NameTab());
        getCommand("GetGun").setTabCompleter(new GetGunTab());

        PluginManager manager = getServer().getPluginManager();
        
        manager.registerEvents(new EntityTarget(), this);
        manager.registerEvents(new EntityDeath(), this);
        manager.registerEvents(new ChunkLoad(), this);
        manager.registerEvents(new PlayerJoin(), this);
        manager.registerEvents(new Click(), this);

        LoadGuns.load();
        UUID.newDateFactor(data);
        createKeys();

        this.getLogger().info("Name Plugin enabled!");
    }
    
    @Override
    public void onDisable() {
        this.guns.saveFile();
        this.config.saveFile();
        this.data.saveFile();
        this.getLogger().info("Name Plugin disabled!");
    }

    public void createKeys() {
        new NamespacedKey(this, "ownerid");
        new NamespacedKey(this, "gunid");
        new NamespacedKey(this, "shots");
        new NamespacedKey(this, "dateFactor");
        new NamespacedKey(this, "UUID");
    }

    public static Main getInstance() {
        return instance;
    }

    public DataManager getMyConfig() {
        return config;
    }

    public DataManager getGuns() {
        return guns;
    }
}
