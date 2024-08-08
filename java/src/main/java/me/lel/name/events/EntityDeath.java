package me.lel.name.events;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.persistence.PersistentDataType;

import me.lel.name.Main;
import me.lel.name.Waves.Waves;
import me.lel.name.commands.zombiesurvival.ZombieSurvival;
import me.lel.name.utils.Chat;

public class EntityDeath implements Listener {
    
    @EventHandler
    private void onEntityDeathEvent(EntityDeathEvent event) {
        Entity mob = event.getEntity();
        NamespacedKey key = NamespacedKey.fromString("ownerid", Main.getInstance());
        if (mob.getPersistentDataContainer().has(key)) {
            String owner = event.getEntity().getPersistentDataContainer().get(key, PersistentDataType.STRING);
            Waves wave = ZombieSurvival.getWaves().get(owner);
            int number = wave.removeMonster();
            Chat.broadcastMessage(number + "");
            if (number <= 0) {
                wave.startWave();
            }
        }
    }

}
