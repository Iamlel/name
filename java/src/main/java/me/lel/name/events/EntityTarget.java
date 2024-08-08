package me.lel.name.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

//import me.lel.name.utils.NameSpacedKeys;

import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class EntityTarget implements Listener{
    
    @EventHandler
    private void onEntityTargetEvent(EntityTargetEvent event) {
        Entity entity = event.getEntity();
        //  && entity.getPersistentDataContainer().has(NameSpacedKeys.OWNERID.getKey())
        if (entity instanceof Creature) {
            if (event.getTarget() instanceof Player == false) {
                entity.getNearbyEntities(16.0D, 16.0D, 16.0D)
                    .stream()
                    .filter(e -> e instanceof Player)
                    .findFirst()
                    .ifPresentOrElse((e -> event.setTarget(e)), () -> event.setCancelled(true));
            }
        }
    }
}