package net.digitalpear.pearfection.init;

import net.digitalpear.pearfection.Pearfection;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class PearConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?, ?>> HUGE_PEAR = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE,
            new Identifier(Pearfection.MOD_ID, "huge_pear"));

    public static void init(){
    }
}
