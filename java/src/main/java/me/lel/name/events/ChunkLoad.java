package me.lel.name.events;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

import me.lel.name.Main;

public class ChunkLoad implements Listener {
    
    @EventHandler
    public void onChunkLoadEvent(ChunkLoadEvent event) {
        for (Entity entity : event.getChunk().getEntities()) {
            NamespacedKey key = NamespacedKey.fromString("ownerid", Main.getInstance());
            if (entity.getPersistentDataContainer().has(key)) {
                entity.remove();
            }
        }
    }
}
