package net.digitalpear.pearfection.common.features;

import com.mojang.serialization.Codec;
import net.digitalpear.pearfection.init.tags.PearBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
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
        return (!state.isIn(PearBlockTags.HUGE_PEAR_CANNOT_REPLACE) && state.getBlock().getHardness() < 1.5) || state.isOf(Blocks.WATER);
    }


    @Override
    public boolean generate(FeatureContext<HugePearFeatureConfig> context) {
        if (placeStem(context)) {
            placeBase(context);
            return true;
        }
        return false;
    }

    public void placeBase(FeatureContext<HugePearFeatureConfig> context){
        BlockPos blockPos = context.getOrigin().up();
        StructureWorldAccess world = context.getWorld();
        Random random = context.getRandom();
        int radius = 1;
        int height = random.nextBetween(2, 3);
        BlockStateProvider baseBlock = context.getConfig().baseBlockProvider;

        Map<Iterable<BlockPos>, BlockState> PLACEMENTS = new HashMap<>();
            /*
                Collect smaller pear layer
             */
        PLACEMENTS.put(BlockPos.iterate(blockPos.add(-radius, -1, -radius),
                blockPos.add(radius, -height, radius)), baseBlock.get(random, blockPos));
            /*
                Collect bigger pear layer
             */
        PLACEMENTS.put(BlockPos.iterate(blockPos.add(-(radius + 1), -height - 1, -(radius + 1)),
                blockPos.add(radius + 1, -(height * 4), radius + 1)), baseBlock.get(random, blockPos));
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
    }



    public boolean placeStem(FeatureContext<HugePearFeatureConfig> context){
        BlockPos blockPos = context.getOrigin().up();
        StructureWorldAccess world = context.getWorld();
        Random random = context.getRandom();
        int stemLengthMultiplier = random.nextBetween(3, 4);
        HugePearFeatureConfig config = context.getConfig();
        BlockStateProvider stem = context.getConfig().trunkProvider;
        if (blockPos.getY() <= world.getBottomY() + 1 && blockPos.getY() + stemLengthMultiplier + 1 > world.getTopY()) {
            return false;
        }
        Direction turnDirection = getRandomHorizontalDirection(random);




        Map<Iterable<BlockPos>, BlockState> PLACEMENTS = new HashMap<>();



        /*
            Collect stems and place foliage
         */
        PLACEMENTS.put(BlockPos.iterate(blockPos, blockPos.offset(Direction.UP, 2)),
                stem.get(random, blockPos).with(PillarBlock.AXIS, Direction.Axis.Y));

        placeFoliage(world, random, blockPos.offset(Direction.UP, 2 + stemLengthMultiplier).offset(turnDirection), config);
        PLACEMENTS.put(BlockPos.iterate(blockPos.offset(Direction.UP, 2).offset(turnDirection), blockPos.offset(Direction.UP, 2 + stemLengthMultiplier).offset(turnDirection)),
                stem.get(random, blockPos).with(PillarBlock.AXIS, Direction.Axis.Y));

        BlockPos newPos = blockPos.offset(Direction.UP, 2 + stemLengthMultiplier).offset(turnDirection);
        placeFoliage(world, random, newPos, config);
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
        placeFoliage(world, random, newPos.offset(turnDirection, 2 + stemLengthMultiplier), config);

        return true;
    }

    public void placeFoliage(StructureWorldAccess world, Random random, BlockPos blockPos, HugePearFeatureConfig config){
        int x = random.nextBetween(1, 2);
        int y = 1;
        int z = random.nextBetween(1, 2);
        float radius = (float)(x + y + z) * 0.333F + 0.5F;


        for (BlockPos currentBlockPos : BlockPos.iterate(blockPos.add(-x, -y, -z), blockPos.add(x, y, z))) {
            /*
                Place leaves
            */
            if (currentBlockPos.getSquaredDistance(blockPos) <= (double) (radius * radius) && world.getBlockState(currentBlockPos).isAir()) {
                BlockStateProvider leaves = random.nextBoolean() ? config.floweringFoliageProvider : config.foliageProvider;
                world.setBlockState(currentBlockPos, leaves.get(random, currentBlockPos), 2);
            }
        }
        for (BlockPos currentBlockPos : BlockPos.iterate(blockPos.add(-x, -y, -z).down(), blockPos.add(x, y, z).down())) {
            if (world.getBlockState(currentBlockPos).isAir() && random.nextFloat() < 0.6f) {
                /*
                    Place leaves
                 */
                BlockStateProvider leaves = random.nextBoolean() ? config.floweringFoliageProvider : config.foliageProvider;
                world.setBlockState(currentBlockPos, leaves.get(random, currentBlockPos), 2);

                /*
                    Place fruit
                 */
                if (world.getBlockState(currentBlockPos.down()).isAir() && random.nextFloat() < 0.2){
                    BlockStateProvider fruit = random.nextFloat() < 0.25 ? config.rareFruitProvider : config.fruitProvider;
                    world.setBlockState(currentBlockPos.down(), fruit.get(random, currentBlockPos.down()), 2);
                }
            }
        }
    }

    public static Direction getRandomHorizontalDirection(Random random){
        Direction direction = Direction.random(random);
        while (direction.getAxis().isVertical()){
            direction = Direction.random(random);
        }
        return direction;
    }

}
