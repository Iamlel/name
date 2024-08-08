package me.lel.name.Guns;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Particle.DustTransition;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import me.lel.name.Main;
import me.lel.name.utils.BetterVector;
import me.lel.name.utils.Chat;

public class Gun {

    private static final int bulletIterations = 1000;

    private double damage;
    private long fireRate;
    private int clipSize;
    private long reloadTime;
    private long bulletSpeed;
    private int bulletSize;
    private int bulletDispersion;
    private int bulletAmount;
    private double bulletDistance;

    private ItemStack item;

    private static Map<Integer, Gun> guns = new HashMap<Integer, Gun>();
    private static Random rng = new Random();
    
    public Gun(int id, double damage, long fireRate, int clipSize, long reloadTime, 
        long bulletSpeed, int bulletSize, int bulletDispersion, int bulletAmount, double bulletDistance, 
        String name, List<String> description) {

        this.damage = damage;
        this.fireRate = fireRate;
        this.clipSize = clipSize;
        this.reloadTime = reloadTime;
        this.bulletSpeed = bulletSpeed;
        this.bulletSize = bulletSize;
        this.bulletDispersion = bulletDispersion;
        this.bulletAmount = bulletAmount;
        this.bulletDistance = bulletDistance * 3;
        
        ItemStack newItem = new ItemStack(Material.IRON_HORSE_ARMOR);
        ItemMeta meta = newItem.getItemMeta();
        meta.displayName(Chat.getComponent(name));
        meta.lore(description.stream().map(line -> Chat.getComponent(line)).toList());
        meta.getPersistentDataContainer().set(NamespacedKey.fromString("shots", Main.getInstance()), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(NamespacedKey.fromString("gunid", Main.getInstance()), PersistentDataType.INTEGER, id);
        newItem.setItemMeta(meta);
        
        this.item = newItem;
        
        guns.put(id, this);
    }
    
    public void shootGun(Player shooter) {
        final DustTransition dustTransition = new DustTransition(Color.fromRGB(204, 0, 0), Color.fromRGB(0, 0, 0), 0.7F);
        if (bulletSpeed > 0) {
            final long time = Math.max(1, bulletSpeed % Math.min(bulletIterations, bulletSpeed)); final long time2;
            if (time == 0) { time2 = 1; }
            else { time2 = Math.floorDiv(bulletIterations, time); }
            final Location initialShotLocation = shooter.getEyeLocation();
            final World shooterWorld = initialShotLocation.getWorld();

            for (int x = 0; x < bulletAmount; x++) {  
                BetterVector bulletDirection = new BetterVector(initialShotLocation.getYaw() + rng.nextInt(bulletDispersion * -1, bulletDispersion) * 0.1, 
                    initialShotLocation.getPitch() + rng.nextInt(bulletDispersion * -1, bulletDispersion) * 0.05, 1, true);
                bulletDirection.normalize().multiply(0.3 / bulletSize);

                Location bulletLocation = initialShotLocation.clone();
                new BukkitRunnable() {
                    double i = 1;
                    
                    public void run() {
                        iterations:
                        for (long y = 0; y < time2; y++) {
                            if (i > bulletDistance) { break; }

                            for (int x2 = 1; x2 <= bulletSize; x2++) {
                                bulletDirection.normalize().multiply((0.3 / bulletSize));
                                bulletLocation.add(bulletDirection);
                                if (bulletLocation.getBlock().isSolid())  { break iterations; }

                                shooterWorld.spawnParticle(Particle.DUST_COLOR_TRANSITION, bulletLocation, 2, dustTransition);
                            }

                            for (LivingEntity e : bulletLocation.getNearbyLivingEntities(0.2, 0.2, 0.2)) {
                                if (!(e instanceof Player)) {
                                    e.damage(damage, shooter);
                                    break iterations;
                                }
                            }
                            i++;
                        }
                        this.cancel();
                    }
                }.runTaskTimer(Main.getInstance(), 0L, time);
            }
        }
	}

    public static void clearGuns() {
        guns.clear();
    }

    public ItemStack getItem() {
        return item;
    }

    public double getFireRate() {
        return fireRate;
    }
    
    public long getReloadTime() {
        return reloadTime;
    }

    public int getClipSize() {
        return clipSize;
    }

    public static Map<Integer, Gun> getGuns() {
        return guns;
    }
}