package net.digitalpear.pearfection.common.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.floatprovider.ConstantFloatProvider;
import net.minecraft.util.math.floatprovider.FloatProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class HugePearFeatureConfig implements FeatureConfig {
    public static final Codec<HugePearFeatureConfig> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(
                    BlockStateProvider.TYPE_CODEC.fieldOf("stem_provider").forGetter((config) -> config.trunkProvider),
                    BlockStateProvider.TYPE_CODEC.fieldOf("foliage_provider").forGetter((config) -> config.foliageProvider),
                    BlockStateProvider.TYPE_CODEC.fieldOf("flowering_foliage_provider").forGetter((config) -> config.floweringFoliageProvider),
                    BlockStateProvider.TYPE_CODEC.fieldOf("base_block_provider").forGetter((config) -> config.baseBlockProvider),
                    BlockStateProvider.TYPE_CODEC.fieldOf("fruit_provider").forGetter((config) -> config.fruitProvider),
                    BlockStateProvider.TYPE_CODEC.fieldOf("rare_fruit_provider").forGetter((config) -> config.rareFruitProvider))
                    .apply(instance, HugePearFeatureConfig::new));



    public final BlockStateProvider trunkProvider;
    public final BlockStateProvider foliageProvider;
    public final BlockStateProvider floweringFoliageProvider;
    public final BlockStateProvider baseBlockProvider;
    public final BlockStateProvider fruitProvider;
    public final BlockStateProvider rareFruitProvider;


    public HugePearFeatureConfig(BlockStateProvider stemProvider,
                                 BlockStateProvider foliageProvider,
                                 BlockStateProvider floweringFoliageProvider,
                                 BlockStateProvider baseBlockProvider,
                                 BlockStateProvider fruitProvider) {
        this.trunkProvider = stemProvider;
        this.foliageProvider = foliageProvider;
        this.floweringFoliageProvider = floweringFoliageProvider;
        this.baseBlockProvider = baseBlockProvider;
        this.fruitProvider = fruitProvider;
        this.rareFruitProvider = fruitProvider;
    }
    public HugePearFeatureConfig(BlockStateProvider stemProvider,
                                 BlockStateProvider foliageProvider,
                                 BlockStateProvider floweringFoliageProvider,
                                 BlockStateProvider baseBlockProvider,
                                 BlockStateProvider fruitProvider,
                                 BlockStateProvider rareFruitProvider) {
        this.trunkProvider = stemProvider;
        this.foliageProvider = foliageProvider;
        this.floweringFoliageProvider = floweringFoliageProvider;
        this.baseBlockProvider = baseBlockProvider;
        this.fruitProvider = fruitProvider;
        this.rareFruitProvider = rareFruitProvider;
    }

}
