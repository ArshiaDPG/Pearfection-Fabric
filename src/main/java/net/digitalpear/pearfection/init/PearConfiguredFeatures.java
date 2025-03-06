package net.digitalpear.pearfection.init;

import net.digitalpear.pearfection.Pearfection;
import net.digitalpear.pearfection.common.blocks.LampearBlock;
import net.digitalpear.pearfection.common.features.HugeLampearFeatureConfig;
import net.digitalpear.pearfection.common.features.PearFeatures;
import net.minecraft.block.BlockState;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.collection.Pool;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

public class PearConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?, ?>> HUGE_LAMPEAR = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Pearfection.id("huge_lampear"));
    public static final RegistryKey<ConfiguredFeature<?, ?>> ENORMOUS_LAMPEAR = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Pearfection.id("enormous_lampear"));

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> featureRegisterable) {
        ConfiguredFeatures.register(featureRegisterable, HUGE_LAMPEAR, PearFeatures.HUGE_LAMPEAR,
                createDefaultConfig(UniformIntProvider.create(3, 4))
        );
        ConfiguredFeatures.register(featureRegisterable, ENORMOUS_LAMPEAR, PearFeatures.ENORMOUS_LAMPEAR,
                createDefaultConfig(UniformIntProvider.create(32, 35))
        );
    }

    public static HugeLampearFeatureConfig createDefaultConfig(IntProvider heightProvider){
        Pool.Builder<BlockState> LEAVES = new Pool.Builder<BlockState>()
                .add(PearBlocks.CALLERY.getLeaves().getDefaultState(), 2)
                .add(PearBlocks.FLOWERING_CALLERY_LEAVES.getDefaultState(), 1);
        Pool.Builder<BlockState> FRUITS = new Pool.Builder<BlockState>()
                .add(PearBlocks.LAMPEAR.getDefaultState().with(LampearBlock.HANGING, true), 200)
                .add(PearBlocks.COPPER_LAMPEAR.getDefaultState().with(LampearBlock.HANGING, true), 1);

        return new HugeLampearFeatureConfig(
                SimpleBlockStateProvider.of(PearBlocks.CALLERY.getLog()),
                new WeightedBlockStateProvider(LEAVES),
                SimpleBlockStateProvider.of(PearBlocks.LAMPEAR_BLOCK),
                new WeightedBlockStateProvider(FRUITS),
                heightProvider
        );
    }

    public static void init(){
    }
}
