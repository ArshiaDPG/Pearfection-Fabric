package net.digitalpear.pearfection.common.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.util.collection.Pool;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

import java.util.Map;
import java.util.Set;

public class HugeLampearFeatureConfig implements FeatureConfig {

    public static final Codec<HugeLampearFeatureConfig> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(
                            BlockStateProvider.TYPE_CODEC.fieldOf("stem_provider").forGetter((config) -> config.trunkProviders),
                            BlockStateProvider.TYPE_CODEC.fieldOf("foliage_provider").forGetter((config) -> config.foliageProviders),
                            BlockStateProvider.TYPE_CODEC.fieldOf("base_block_provider").forGetter((config) -> config.baseBlockProviders),
                            BlockStateProvider.TYPE_CODEC.fieldOf("fruit_provider").forGetter((config) -> config.fruitProviders),
                            IntProvider.POSITIVE_CODEC.fieldOf("height_provider").forGetter((config) -> config.height),
                            Codecs.POSITIVE_FLOAT.fieldOf("max_grow_through_hardness").orElse(1.4f).forGetter(config -> config.maxGrowThroughHardness),
                            Codecs.POSITIVE_FLOAT.fieldOf("fruit_spawn_chance").orElse(0.2f).forGetter(config -> config.fruitSpawnChance))
                .apply(instance, HugeLampearFeatureConfig::new));

    public final BlockStateProvider trunkProviders;
    public final BlockStateProvider foliageProviders;
    public final BlockStateProvider baseBlockProviders;
    public final BlockStateProvider fruitProviders;
    public final Float maxGrowThroughHardness;
    public final Float fruitSpawnChance;
    public final IntProvider height;


    public HugeLampearFeatureConfig(BlockStateProvider trunkProviders, BlockStateProvider foliageProviders, BlockStateProvider baseBlockProviders, BlockStateProvider fruitProviders, IntProvider height, Float maxGrowThroughHardness, Float fruitSpawnChance) {
        this.trunkProviders = trunkProviders;
        this.foliageProviders = foliageProviders;
        this.baseBlockProviders = baseBlockProviders;
        this.fruitProviders = fruitProviders;
        this.height = height;
        this.maxGrowThroughHardness = maxGrowThroughHardness;
        this.fruitSpawnChance = fruitSpawnChance;
    }
    public HugeLampearFeatureConfig(BlockStateProvider trunkProviders, BlockStateProvider foliageProviders, BlockStateProvider baseBlockProviders, BlockStateProvider fruitProviders, IntProvider height) {
        this(trunkProviders, foliageProviders, baseBlockProviders, fruitProviders, height, 1.4f, 0.2f);
    }

    public static WeightedBlockStateProvider convertToProvider(Map<BlockState, Integer> map){
        return new WeightedBlockStateProvider(convertToPool(map));
    }
    public static Pool<BlockState> convertToPool(Map<BlockState, Integer> map){
        Pool.Builder<BlockState> pool = Pool.builder();
        map.forEach(pool::add);
        return pool.build();
    }
}
