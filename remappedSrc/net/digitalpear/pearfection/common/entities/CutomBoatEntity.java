package net.digitalpear.pearfection.common.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.world.World;

public class CutomBoatEntity extends BoatEntity {
    public CutomBoatEntity(EntityType<? extends BoatEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public Type getVariant() {

        return super.getVariant();
    }
}
