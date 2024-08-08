package me.lel.name.Waves;

import org.bukkit.entity.EntityType;

public enum PossibleEntities {
    WITHER_SKELETON(10),
    STRAY(5),
    SKELETON(4),
    DROWNED(2),
    ZOMBIE(1),
    HUSK(1);

    private final int points;

    private PossibleEntities(int points) {
        this.points = points;
    }

    public EntityType getEntityType() {
        return EntityType.valueOf(this.name());
    }

    public int getValue() { return points; }
}