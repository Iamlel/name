package me.lel.name.Waves;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import me.lel.name.Main;
import me.lel.name.utils.Chat;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Random;

import org.bukkit.World;
import org.bukkit.block.Block;

public class Waves {

    private int waveNumber = 0;
    private int monstersLeft = 0;

    private final Random random;

    private final double[] locs;
    private final World world;
    private final String id;

    public Waves(double[] locs, World world, String id) {
        this.locs = locs;
        this.world = world;
        this.id = id;
        this.random = new Random();
    }

    public int removeMonster() {
        monstersLeft--;
        return monstersLeft;
    }

    public void startWave() {
        waveNumber++;
        int i = 0, j = PossibleEntities.values().length, amount;
        EntityType type;
        
        Chat.broadcastMessage(String.format("&a&lWave %d has begun!", waveNumber));

        int points = (int) (4 * (Math.sqrt((double) waveNumber) + Math.pow(waveNumber, 2)) + 10);
        for (PossibleEntities currentEntities : PossibleEntities.values()) {
            i++;
            if (i == j) {
                amount = points;
                type = currentEntities.getEntityType();
            } else {
                amount = random.nextInt(points / currentEntities.getValue());
                type = currentEntities.getEntityType();
            }

            for (int x = 0; x < amount; x++) {
                Location loc = getRandomLocation(new Location(world, locs[0], locs[1], locs[2]), new Location(world, locs[3], locs[1], locs[4])); // make sure this works

                // fix this part vvvv
                //Block spawnblock = loc.getBlock();
                //Boolean notFound = true;
                //for (int y = 1; y < 385; y++) {
                //     if (spawnblock.isSolid()) {
                //         spawnblock = spawnblock.getRelative(0, 1, 0);
                //         notFound = false;
                //     } else {    break;  }
                // }

                // if (notFound) { continue; } else { loc = spawnblock.getLocation(); }
                // ^^^^^^^^^^^

                LivingEntity currentEntity = (LivingEntity) loc.getWorld().spawnEntity(loc, type, SpawnReason.CUSTOM);
                Chat.broadcastMessage("spawned: " + currentEntities.getEntityType().name());
                currentEntity.getEquipment().clear();

                ItemStack item = new ItemStack(Material.LEATHER_HELMET);
                item.getItemMeta().setUnbreakable(true);

                currentEntity.getEquipment().setHelmet(item);
                currentEntity.customName(Chat.getComponent("Wave " + waveNumber));   
                currentEntity.setCustomNameVisible(true);
                currentEntity.getPersistentDataContainer().set(NamespacedKey.fromString("ownerid", Main.getInstance()), PersistentDataType.STRING, id);
            }

            monstersLeft += amount;
            points -= (amount * currentEntities.getValue());
        }
    }

    public Location getRandomLocation(Location loc1, Location loc2) {
        if (loc1.equals(loc2)) { return loc1; }
        double minX = Math.min(loc1.getX(), loc2.getX());
        double minY = Math.min(loc1.getY(), loc2.getY());
        double minZ = Math.min(loc1.getZ(), loc2.getZ());

        double maxX = Math.max(loc1.getX(), loc2.getX());
        double maxY = Math.max(loc1.getY(), loc2.getY());
        double maxZ = Math.max(loc1.getZ(), loc2.getZ());

        return new Location(loc1.getWorld(), randomDouble(minX, maxX), randomDouble(minY, maxY), randomDouble(minZ, maxZ));
    }

    private double randomDouble(double min, double max) {
        return min + random.nextDouble(Math.abs(max - min + 1));
    }
}
