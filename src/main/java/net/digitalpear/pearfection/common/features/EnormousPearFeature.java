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

public class EnormousPearFeature extends Feature<HugePearFeatureConfig> {
    public EnormousPearFeature(Codec<HugePearFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<HugePearFeatureConfig> context) {
        BlockPos origin = context.getOrigin().up();
        StructureWorldAccess world = context.getWorld();
        Random random = context.getRandom();

        int baseMaxY = 35;
        int maxY = origin.getY() < (world.getTopY() - baseMaxY) ? baseMaxY : world.getTopY() - origin.getY() - baseMaxY/2;
        int height = random.nextBetween(maxY/2, maxY);
        HugePearFeatureConfig config = context.getConfig();

        if (!(origin.getY() > world.getBottomY() + 4) && !(origin.getY() < world.getTopY() - baseMaxY)){
            return false;
        }
        Map<BlockPos, BlockState> placements = new HashMap<>();

        BlockPos currentPos = origin;
        Direction direction = getRandomHorizontalDirection(random);


        BlockPos.iterate(origin, origin.add(1, height/2, 1)).forEach(pos -> {
            if (HugePearFeature.isReplaceable(world.getBlockState(pos))){
                placements.put(pos, config.trunkProvider.get(random, pos));
            }
        });
        /*
            Place Trunk
         */
        for (int i = 0; i < height/2; i++){
            for (BlockPos pos : BlockPos.iterate(currentPos.add(0, 0, 0), currentPos.add(1, 0, 1))) {
                placeBlock(world, pos, config.trunkProvider.get(random, pos));

                if (currentPos.getY() > origin.getY() + height/4 && random.nextInt(100) < 2){
                    placeBranch(world, pos, config);
                }
            }


            if (currentPos.getY() > origin.getY() + height/4 && random.nextInt(100) < 25){
                currentPos = currentPos.offset(direction);
                if (random.nextBoolean()){
                    placeFoliage(world, currentPos.up(), config, 1, 1);
                }
            }
            else{
                currentPos = currentPos.up();
            }
        }
        placeFoliage(world, currentPos.up(), config, 2, 3);
        for (int i = 0; i < height/2; i++){
            for (BlockPos pos : BlockPos.iterate(currentPos, currentPos.up().offset(direction.rotateYClockwise()))) {
                placeBlock(world, pos, config.trunkProvider.get(random, pos).with(PillarBlock.AXIS, direction.getAxis()));
                if (random.nextInt(100) < 5){
                    placeBranch(world, pos, config);
                }
            }


            if (random.nextInt(100) < 15){
                currentPos = currentPos.up();
                if (random.nextBoolean()){
                    placeFoliage(world, currentPos.up(), config, 1, 1);
                }
            }
            else{
                currentPos = currentPos.offset(direction);
            }
        }
        currentPos = currentPos.offset(direction);
        placeFoliage(world, currentPos, config, 1, 2);
        for (BlockPos pos : BlockPos.iterate(currentPos.add(0, -2, 0), currentPos.add(1, -2, 1))) {
            placeBlock(world, pos, config.trunkProvider.get(random, pos));
        }
        placeFoliage(world, currentPos.up(), config, 2, 3);


        for (BlockPos pos : BlockPos.iterate(origin.add(-1, -1, -1), origin.add(2, -2, 2))) {
            placeBlock(world, pos, config.baseBlockProvider.get(random, pos));
        }
        for (BlockPos pos : BlockPos.iterate(origin.add(-2, -3, -2), origin.add(3, -7, 3))) {
            placeBlock(world, pos, config.baseBlockProvider.get(random, pos));
        }
        return true;
    }






    public void placeBlock(StructureWorldAccess world, BlockPos pos, BlockState state){
        if (HugePearFeature.isReplaceable(world.getBlockState(pos))){
            world.setBlockState(pos, state, 3);
        }
    }
    public void placeBranch(StructureWorldAccess world, BlockPos pos, HugePearFeatureConfig config){
        Random random = world.getRandom();
        Direction direction = Direction.byId(random.nextBetween(2, 5));
        BlockPos currentPos = pos;
        for (int i = 0; i < random.nextBetween(3, 5); i++){
            placeBlock(world, currentPos, config.trunkProvider.get(random, currentPos).with(PillarBlock.AXIS, direction.getAxis()));
            currentPos = currentPos.offset(direction);
            if (random.nextFloat() < 0.3){
                currentPos = currentPos.up();
            }
        }
        placeBigPear(world, currentPos.down(), config);
    }

    public void placeBigPear(StructureWorldAccess world, BlockPos pos, HugePearFeatureConfig config){
        Random random = world.getRandom();
        placeBlock(world, pos, config.trunkProvider.get(random, pos.down()));
        placeFoliage(world, pos, config, 1, 1);
        placeBlock(world, pos.down(), config.baseBlockProvider.get(random, pos.down()));

        for (BlockPos pos1 : BlockPos.iterate(pos.down(1).add(-1, 0, -1), pos.down(2).add(1, 0, 1))) {
            placeBlock(world, pos1, config.baseBlockProvider.get(random, pos1));
        }
    }
    public void placeLeaf(StructureWorldAccess world, BlockPos pos, HugePearFeatureConfig config){
        placeBlock(world, pos, world.getRandom().nextBoolean() ? config.foliageProvider.get(world.getRandom(), pos) : config.floweringFoliageProvider.get(world.getRandom(), pos));
    }

    public void placeFoliage(StructureWorldAccess world, BlockPos blockPos, HugePearFeatureConfig config, int min, int max){
        Random random = world.getRandom();
        int width = random.nextBetween(min, max);
        int y = 1;
        float radius = (float)(width + y + width) * 0.333F + 0.5F;


        for (BlockPos currentBlockPos : BlockPos.iterate(blockPos.add(-width, -y, -width), blockPos.add(width, y, width))) {
            /*
                Place leaves
            */
            if (currentBlockPos.getSquaredDistance(blockPos) <= (double) (radius * radius) && world.getBlockState(currentBlockPos).isAir()) {
                placeLeaf(world, currentBlockPos, config);
            }
        }
        for (BlockPos currentBlockPos : BlockPos.iterate(blockPos.add(-width, -y, -width).down(), blockPos.add(width, y, width).down())) {
            if (world.getBlockState(currentBlockPos).isAir() && random.nextFloat() < 0.6f) {
                /*
                    Place leaves
                 */
                placeLeaf(world, currentBlockPos, config);

                /*
                    Place fruit
                 */
                if (random.nextFloat() < 0.4){
                    for (int i = 0; i < random.nextInt(3); i++){
                        if (world.getBlockState(currentBlockPos.down(i)).isAir()){
                            currentBlockPos = currentBlockPos.down(i);
                            placeLeaf(world, currentBlockPos, config);
                        }
                    }
                }
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
