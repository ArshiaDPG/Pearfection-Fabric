package net.digitalpear.pearfection.common.features;

import net.digitalpear.pearfection.Pearfection;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

public class PearFeatures {
    public static final Feature<HugeLampearFeatureConfig> HUGE_LAMPEAR = register("huge_lampear", new HugeLampearFeature(HugeLampearFeatureConfig.CODEC));
    public static final Feature<HugeLampearFeatureConfig> ENORMOUS_LAMPEAR = register("enormous_lampear", new EnormousLampearFeature(HugeLampearFeatureConfig.CODEC));

    private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return Registry.register(Registries.FEATURE, Pearfection.id(name), feature);
    }

    public static void init() {
    }
}
