package net.digitalpear.pearfection.common.features;

import com.mojang.serialization.Codec;
import net.digitalpear.pearfection.common.blocks.CalleryLeavesBlock;
import net.digitalpear.pearfection.init.tags.PearBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.HashMap;
import java.util.Map;

public class HugePearFeature extends Feature<HugePearFeatureConfig> {
    public HugePearFeature(Codec<HugePearFeatureConfig> configCodec) {
        super(configCodec);
    }

    public static boolean isReplaceable(BlockState state) {
        return !state.isIn(PearBlockTags.HUGE_PEAR_CANNOT_REPLACE) && state.getBlock().getHardness() < 1.5;
    }


    @Override
    public boolean generate(FeatureContext<HugePearFeatureConfig> context) {

        BlockPos blockPos = context.getOrigin();
        World world = context.getWorld().toServerWorld();
        Random random = context.getRandom();
        HugePearFeatureConfig config = context.getConfig();
        BlockStateProvider stem = context.getConfig().trunkProvider;
        BlockStateProvider baseBlock = context.getConfig().baseBlockProvider;

        int stemLengthMultiplier = random.nextBetween(3, 4);
        int radius = 1;
        int height = random.nextBetween(2, 3);

        Direction turnDirection = getHorizontal(random);

        Map<Iterable<BlockPos>, BlockState> PLACEMENTS = new HashMap<>();


        if (blockPos.getY() <= world.getBottomY() + 1 && blockPos.getY() + height + stemLengthMultiplier + 1 > world.getTopY()) {
            return false;
        }

        /*
            Collect smaller pear layer
         */
        PLACEMENTS.put(BlockPos.iterate(blockPos.add(-radius, -1, -radius),
                blockPos.add(radius, -height, radius)), baseBlock.get(random, blockPos));

        /*
            Collect bigger pear layer
         */
        PLACEMENTS.put(BlockPos.iterate(blockPos.add(-(radius + 1), -height-1, -(radius + 1)),
                blockPos.add(radius + 1, -(height * 4), radius + 1)), baseBlock.get(random, blockPos));

        /*
            Collect stems and place foliage
         */
        PLACEMENTS.put(BlockPos.iterate(blockPos, blockPos.offset(Direction.UP, 2)),
                stem.get(random, blockPos).with(PillarBlock.AXIS, Direction.Axis.Y));

        placeFoliage(world, blockPos.offset(Direction.UP, 2 + stemLengthMultiplier).offset(turnDirection), config);
        PLACEMENTS.put(BlockPos.iterate(blockPos.offset(Direction.UP, 2).offset(turnDirection), blockPos.offset(Direction.UP, 2 + stemLengthMultiplier).offset(turnDirection)),
                stem.get(random, blockPos).with(PillarBlock.AXIS, Direction.Axis.Y));

        BlockPos newPos = blockPos.offset(Direction.UP, 2 + stemLengthMultiplier).offset(turnDirection);
        placeFoliage(world, newPos, config);
        PLACEMENTS.put(BlockPos.iterate(newPos, newPos.offset(turnDirection, 1 + stemLengthMultiplier)), stem.get(random, blockPos).with(PillarBlock.AXIS, turnDirection.getAxis()));



        /*
            Place all collected maps
         */
        PLACEMENTS.forEach((iterator, state) -> {
            for (BlockPos currentPos : iterator) {
                if (isReplaceable(world.getBlockState(currentPos))) {
                    world.setBlockState(currentPos, state, 2);
                }
            }
        });
        world.setBlockState(newPos.offset(turnDirection, 2 + stemLengthMultiplier).down(), stem.get(random, blockPos).with(PillarBlock.AXIS, Direction.Axis.Y), 2);
        placeFoliage(world, newPos.offset(turnDirection, 2 + stemLengthMultiplier), config);

        return true;
    }

    public static Direction getHorizontal(Random random){
        Direction direction = Direction.random(random);
        while (direction.getAxis().isVertical()){
            direction = Direction.random(random);
        }
        return direction;
    }

    public static void placeFoliage(World world, BlockPos blockPos, HugePearFeatureConfig config){
        int x = world.random.nextBetween(1, 2);
        int y = 1;
        int z = world.random.nextBetween(1, 2);
        float radius = (float)(x + y + z) * 0.333F + 0.5F;

        for (BlockPos currentBlockPos : BlockPos.iterate(blockPos.add(-x, -y, -z), blockPos.add(x, y, z))) {
            if (currentBlockPos.getSquaredDistance(blockPos) <= (double) (radius * radius) && world.getBlockState(currentBlockPos).isAir()) {
                BlockStateProvider leaves = world.random.nextBoolean() ? config.floweringFoliageProvider : config.foliageProvider;
                world.setBlockState(currentBlockPos, leaves.get(world.random, currentBlockPos), 2);
            }
        }
        for (BlockPos currentBlockPos : BlockPos.iterate(blockPos.add(-x, -y, -z).down(), blockPos.add(x, y, z).down())) {
            if (world.getBlockState(currentBlockPos).isAir() && world.random.nextFloat() < 0.6f) {
                /*
                    Place leaves
                 */
                BlockStateProvider leaves = world.random.nextBoolean() ? config.floweringFoliageProvider : config.foliageProvider;
                world.setBlockState(currentBlockPos, leaves.get(world.random, currentBlockPos), 2);

                /*
                    Place fruit
                 */
                if (world.getBlockState(currentBlockPos.down()).isAir() && world.random.nextFloat() < 0.1){
                    world.setBlockState(currentBlockPos.down(), config.fruitProvider.get(world.random, currentBlockPos.down()), 2);
                }
            }
        }
    }

}
