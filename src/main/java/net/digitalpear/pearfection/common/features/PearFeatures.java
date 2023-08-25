package net.digitalpear.pearfection.common.features;

import net.digitalpear.pearfection.Pearfection;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

public class PearFeatures {
    public static final Feature<HugePearFeatureConfig> HUGE_PEAR = register("huge_pear", new HugePearFeature(HugePearFeatureConfig.CODEC));
    public static final Feature<HugePearFeatureConfig> ENORMOUS_PEAR = register("enormous_pear", new EnormousPearFeature(HugePearFeatureConfig.CODEC));


    private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return Registry.register(Registries.FEATURE, new Identifier(Pearfection.MOD_ID, name), feature);
    }

    public static void init() {
    }
}
