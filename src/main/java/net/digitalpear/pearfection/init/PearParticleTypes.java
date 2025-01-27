package net.digitalpear.pearfection.init;

import net.digitalpear.pearfection.Pearfection;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class PearParticleTypes {

    public static final SimpleParticleType CALLERY_LEAF = register("callery_leaf", false);
    public static final SimpleParticleType CALLERY_FLOWER = register("callery_flower", false);
    private static SimpleParticleType register(String name, boolean alwaysShow) {
        return Registry.register(Registries.PARTICLE_TYPE, Pearfection.id(name), FabricParticleTypes.simple(alwaysShow));
    }
}
