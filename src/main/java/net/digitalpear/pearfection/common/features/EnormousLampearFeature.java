package net.digitalpear.pearfection.common.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnormousLampearFeature extends Feature<HugeLampearFeatureConfig> {
    public Direction branchDisallowedDirection = Direction.UP;
    public EnormousLampearFeature(Codec<HugeLampearFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<HugeLampearFeatureConfig> context) {
        BlockPos origin = context.getOrigin().up();
        StructureWorldAccess world = context.getWorld();
        Random random = context.getRandom();
        HugeLampearFeatureConfig config = context.getConfig();

        int baseMaxY = config.height.get(random);
        int maxY = origin.getY() < (world.getTopY() - baseMaxY) ? baseMaxY : world.getTopY() - origin.getY() - baseMaxY/2;
        int height = random.nextBetween(maxY/2, maxY);
        int branchChance = 5;
        int foliageChance = 5;

        if (!(origin.getY() > world.getBottomY() + 4) && !(origin.getY() < world.getTopY() - baseMaxY)){
            return false;
        }

        BlockPos currentPos = origin;
        Direction leanDirection = HugeLampearFeature.getRandomHorizontalDirection(random, Direction.UP, Direction.DOWN);

        Map<BlockPos, BlockState> placements = new HashMap<>();
        BlockPos.iterate(origin, origin.add(1, height/2, 1)).forEach(pos -> {
            if (HugeLampearFeature.isReplaceable(config.maxGrowThroughHardness.get(random), world.getBlockState(pos))){
                placements.put(pos, config.trunkProviders.get(random, pos));
            }
        });
        /*
            Place Trunk
         */
        for (int i = 0; i < height/2; i++){
            placeTrunkLayer(Direction.NORTH, Direction.Axis.Y, currentPos, context, branchChance, height);

            if (currentPos.getY() > origin.getY() + height/4 && random.nextInt(100) < foliageChance){
                currentPos = currentPos.offset(leanDirection);
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
            placeTrunkLayer(Direction.UP, leanDirection.getAxis(), currentPos, context, branchChance, height);


            if (random.nextInt(100) < 15){
                currentPos = currentPos.up();
                if (random.nextBoolean()){
                    placeFoliage(world, currentPos.up(), config, 1, 1);
                }
            }
            else{
                currentPos = currentPos.offset(leanDirection);
            }
        }
        currentPos = currentPos.offset(leanDirection);
        placeFoliage(world, currentPos, config, 1, 2);
//        for (BlockPos pos : BlockPos.iterate(currentPos.add(0, -2, 0), currentPos.add(1, -2, 1))) {
//            placeBlock(config, world, pos, config.trunkProviders.get(random, pos));
//        }
        placeTrunkLayer(Direction.NORTH, Direction.Axis.Y, currentPos, context, branchChance, height);

        placeFoliage(world, currentPos.up(), config, 2, 3);


        /*
            Place Base
         */
        for (BlockPos pos : BlockPos.iterate(origin.add(-2, -1, -2), origin.add(2, -2, 2))) {
            placeBlock(config, world, pos, config.baseBlockProviders.get(random, pos));
        }
        for (BlockPos pos : BlockPos.iterate(origin.add(-3, -3, -3), origin.add(3, -7, 3))) {
            placeBlock(config, world, pos, config.baseBlockProviders.get(random, pos));
        }
        return true;
    }


    public void placeTrunkLayer(Direction baseDirection, Direction.Axis rotationAxis, BlockPos currentPos, FeatureContext<HugeLampearFeatureConfig> context, int branchChance, int height){
        HugeLampearFeatureConfig config = context.getConfig();
        BlockPos origin = context.getOrigin();
        StructureWorldAccess world = context.getWorld();
        Random random = context.getRandom();

        List<Direction> directions = List.of(
                baseDirection,
                baseDirection.rotateClockwise(rotationAxis),
                baseDirection.rotateClockwise(rotationAxis).rotateClockwise(rotationAxis),
                baseDirection.rotateClockwise(rotationAxis).rotateClockwise(rotationAxis).rotateClockwise(rotationAxis)
        );
        BlockState state = config.trunkProviders.get(random, currentPos).withIfExists(PillarBlock.AXIS, rotationAxis);
        placeBlock(config, world, currentPos, state);
        for (Direction direction1 : directions) {
            BlockPos pos = currentPos.offset(direction1);
            placeBlock(config, world, pos, state);

            if (currentPos.getY() > origin.getY() + height/4 && random.nextInt(100) < branchChance){
                placeBranch(world, pos, config);
            }
        }
    }



    public void placeBlock(HugeLampearFeatureConfig config, StructureWorldAccess world, BlockPos pos, BlockState state){
        if (HugeLampearFeature.isReplaceable(config.maxGrowThroughHardness.get(world.getRandom()), world.getBlockState(pos))){
            world.setBlockState(pos, state, 3);
        }
    }
    public void placeBranch(StructureWorldAccess world, BlockPos pos, HugeLampearFeatureConfig config){
        Random random = world.getRandom();
        Direction direction = HugeLampearFeature.getRandomHorizontalDirection(random, Direction.UP, Direction.DOWN, branchDisallowedDirection);
        BlockPos currentPos = pos;
        UniformIntProvider branchLength = UniformIntProvider.create(3, 6);

        for (int i = 0; i < branchLength.get(random); i++){
            placeBlock(config, world, currentPos, config.trunkProviders.get(random, currentPos).withIfExists(PillarBlock.AXIS, direction.getAxis()));
            if (random.nextFloat() < 0.3){
                currentPos = currentPos.up();
                placeBlock(config, world, currentPos, config.trunkProviders.get(random, currentPos).withIfExists(PillarBlock.AXIS, direction.getAxis()));
            }
            currentPos = currentPos.offset(direction);
        }
        placeBigPear(world, currentPos.down(), config);
    }

    public void placeBigPear(StructureWorldAccess world, BlockPos pos, HugeLampearFeatureConfig config){
        Random random = world.getRandom();
        placeBlock(config, world, pos, config.trunkProviders.get(random, pos.down()));
        placeFoliage(world, pos, config, 1, 1);
        placeBlock(config, world, pos.down(), config.baseBlockProviders.get(random, pos.down()));

        for (BlockPos pos1 : BlockPos.iterate(pos.down(1).add(-1, 0, -1), pos.down(2).add(1, 0, 1))) {
            placeBlock(config, world, pos1, config.baseBlockProviders.get(random, pos1));
        }
    }
    public void placeLeaf(StructureWorldAccess world, BlockPos pos, HugeLampearFeatureConfig config){
        placeBlock(config, world, pos, config.foliageProviders.get(world.getRandom(), pos));
    }

    public void placeFoliage(StructureWorldAccess world, BlockPos blockPos, HugeLampearFeatureConfig config, int min, int max){
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
                if (random.nextFloat() < config.fruitSpawnChance.get(random)){
                    placeLeaf(world, currentBlockPos.down(), config);
                }
            }
        }
    }
}