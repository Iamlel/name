package me.lel.name.events;

import java.util.HashMap;

import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import me.lel.name.Main;
import me.lel.name.Guns.Gun;
import me.lel.name.utils.Chat;
import me.lel.name.utils.UUID;

public class Click implements Listener {

    private HashMap<Player, HashMap<Integer, Long>> cooldowns = new HashMap<Player, HashMap<Integer, Long>>();
    private HashMap<Player, HashMap<Integer, Boolean>> reloads = new HashMap<Player, HashMap<Integer, Boolean>>();
    
    @EventHandler
    public void onClickEvent(PlayerInteractEvent event) {
        if (event.getAction() != Action.PHYSICAL) {
            final Player player = event.getPlayer();

            final NamespacedKey key = NamespacedKey.fromString("gunid", Main.getInstance());
            final NamespacedKey clipsizeKey = NamespacedKey.fromString("shots", Main.getInstance());

            final ItemStack item = player.getInventory().getItemInMainHand();
            ItemMeta meta = item.getItemMeta();

            if (meta != null) {
                final PersistentDataContainer container = meta.getPersistentDataContainer();

                if (container.has(key)) {
                    final Gun gun = Gun.getGuns().get(container.get(key, PersistentDataType.INTEGER));
                    boolean bool = true;
                    final int currentClip = container.get(clipsizeKey, PersistentDataType.INTEGER);
                    
                    if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        if (reloads.containsKey(player)) {
                            if (reloads.get(player).containsKey(container.get(NamespacedKey.fromString("UUID", Main.getInstance()), PersistentDataType.INTEGER))) {
                                if (!reloads.get(player).get(container.get(NamespacedKey.fromString("UUID", Main.getInstance()), PersistentDataType.INTEGER))) {
                                    bool = false;
                                }
                            }
                        }

                        if (bool) {
                            if (currentClip < gun.getClipSize()) {
                                bool = true;
                                if (cooldowns.containsKey(player)) {
                                    if (container.get(NamespacedKey.fromString("dateFactor", Main.getInstance()), PersistentDataType.BYTE).equals(UUID.getDateFactorOutdated())) {
                                        if (reloads.get(player).containsKey(container.get(NamespacedKey.fromString("UUID", Main.getInstance()), PersistentDataType.INTEGER))) {
                                            if ((cooldowns.get(player).get(container.get(NamespacedKey.fromString("UUID", Main.getInstance()), PersistentDataType.INTEGER)) / 1000) + gun.getFireRate() 
                                                - (System.currentTimeMillis() / 1000) <= 0) {
                                                gun.shootGun(player);
                                                Chat.broadcastMessage(currentClip + "");
                                                meta.getPersistentDataContainer().set(clipsizeKey, PersistentDataType.INTEGER, currentClip + 1);
                                            } else { bool = false; }
                                        }
                                    } else {
                                        meta.getPersistentDataContainer().set(NamespacedKey.fromString("dateFactor", Main.getInstance()), PersistentDataType.BYTE, UUID.getDateFactorOutdated());
                                        meta.getPersistentDataContainer().set(NamespacedKey.fromString("UUID", Main.getInstance()), PersistentDataType.INTEGER, UUID.newUUID());
                                    }
                                }
                                
                                if (bool) {
                                    final HashMap<Integer, Long> itemCooldown = new HashMap<>();
                                    itemCooldown.put(container.get(NamespacedKey.fromString("UUID", Main.getInstance()), PersistentDataType.INTEGER), System.currentTimeMillis());
                                    cooldowns.put(player, itemCooldown);
                                }
                            } else { player.sendMessage(Chat.getComponent("&cYou need to reload!"));}
                        }
                        
                    } else if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {                    
                        if (reloads.containsKey(player)) {
                            if (container.get(NamespacedKey.fromString("dateFactor", Main.getInstance()), PersistentDataType.BYTE).equals(UUID.getDateFactorOutdated())) {
                                if (!reloads.get(player).get(container.get(NamespacedKey.fromString("UUID", Main.getInstance()), PersistentDataType.INTEGER))) {
                                    bool = false;
                                }
                            } else {
                                meta.getPersistentDataContainer().set(NamespacedKey.fromString("dateFactor", Main.getInstance()), PersistentDataType.BYTE, UUID.getDateFactorOutdated());
                                meta.getPersistentDataContainer().set(NamespacedKey.fromString("UUID", Main.getInstance()), PersistentDataType.INTEGER, UUID.newUUID());
                            }
                        }
                        
                        if (bool) {
                            HashMap<Integer, Boolean> itemReload = new HashMap<>();
                            itemReload.put(container.get(NamespacedKey.fromString("UUID", Main.getInstance()), PersistentDataType.INTEGER), false);
                            reloads.put(player, itemReload);
                            meta.getPersistentDataContainer().set(clipsizeKey, PersistentDataType.INTEGER, 0);

                            final long time = Math.max(1, Math.floorDiv(gun.getReloadTime(), 10));
                            new BukkitRunnable() {
                                long i = 1;
                                
                                public void run() {
                                    if (i > time) { 
                                        itemReload.put(container.get(NamespacedKey.fromString("UUID", Main.getInstance()), PersistentDataType.INTEGER), true);
                                        reloads.put(player, itemReload);
                                        this.cancel();
                                    }
                                    player.playSound(player, Sound.BLOCK_SAND_BREAK, SoundCategory.BLOCKS, 10, 10);
                                    i++;
                                }
                            }.runTaskTimerAsynchronously(Main.getInstance(), 0L, 10L);
                        }
                    }
                }
                player.getInventory().getItemInMainHand().setItemMeta(meta);
            }
        }
    }
}
