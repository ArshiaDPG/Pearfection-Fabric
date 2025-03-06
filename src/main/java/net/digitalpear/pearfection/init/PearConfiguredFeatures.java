package net.digitalpear.pearfection.init;

import net.digitalpear.pearfection.Pearfection;
import net.digitalpear.pearfection.common.blocks.LampearBlock;
import net.digitalpear.pearfection.common.features.HugePearFeatureConfig;
import net.digitalpear.pearfection.common.features.PearFeatures;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class PearConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?, ?>> HUGE_PEAR = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Pearfection.id("huge_pear"));
    public static final RegistryKey<ConfiguredFeature<?, ?>> ENORMOUS_PEAR = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Pearfection.id("enormous_pear"));

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> featureRegisterable) {
        ConfiguredFeatures.register(featureRegisterable, HUGE_PEAR, PearFeatures.HUGE_PEAR,
                new HugePearFeatureConfig(
                        BlockStateProvider.of(PearBlocks.CALLERY.getLog()),
                        BlockStateProvider.of(PearBlocks.CALLERY.getLeaves()),
                        BlockStateProvider.of(PearBlocks.FLOWERING_CALLERY_LEAVES),
                        BlockStateProvider.of(PearBlocks.LAMPEAR_BLOCK),
                        BlockStateProvider.of(PearBlocks.LAMPEAR.getDefaultState().with(LampearBlock.HANGING, true))
                ));
        ConfiguredFeatures.register(featureRegisterable, ENORMOUS_PEAR, PearFeatures.ENORMOUS_PEAR,
                new HugePearFeatureConfig(
                        BlockStateProvider.of(PearBlocks.CALLERY.getLog()),
                        BlockStateProvider.of(PearBlocks.CALLERY.getLeaves()),
                        BlockStateProvider.of(PearBlocks.FLOWERING_CALLERY_LEAVES),
                        BlockStateProvider.of(PearBlocks.LAMPEAR_BLOCK),
                        BlockStateProvider.of(PearBlocks.LAMPEAR.getDefaultState().with(LampearBlock.HANGING, true))
                ));
    }

    public static void init(){
    }
}
