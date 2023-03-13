package net.digitalpear.pearfection.common.blocks;

import net.digitalpear.pearfection.init.PearConfiguredFeatures;
import net.digitalpear.pearfection.init.tags.PearBlockTags;
import net.minecraft.block.*;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class ForgottenTwigBlock extends PlantBlock implements Fertilizable {
    public static final int MAX_AGE = 7;
    public static final IntProperty AGE = Properties.AGE_7;
    public RegistryKey<ConfiguredFeature<?, ?>> tree;
    protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);

    public ForgottenTwigBlock(RegistryKey<ConfiguredFeature<?, ?>> tree, Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));
        this.tree = tree;
    }
    public ForgottenTwigBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));
        this.tree = PearConfiguredFeatures.HUGE_PEAR;
    }
    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
        super.appendProperties(builder);
    }
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
    protected int getGrowthAmount(World world) {
        return world.random.nextBetween(1, 2);
    }

    public void applyGrowth(World world, BlockPos pos, BlockState state) {
        int currentAge = state.get(AGE) + this.getGrowthAmount(world);
        if (currentAge > MAX_AGE) {
            currentAge = MAX_AGE;
        }
        world.setBlockState(pos, getStateWithProperties(state).with(AGE, currentAge), 2);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextFloat() > 0.4) {
            grow(world, random, pos, state);
        }
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        if (state.get(AGE) < MAX_AGE){
            applyGrowth(world, pos, state);
        }
        else if (world.getBlockState(pos.down()).isIn(PearBlockTags.PEAR_GROWABLE_ON)){
            if (pos.getY() <= world.getBottomY() + 1 && pos.getY() + 4 < world.getTopY()) {
                return;
            }
            else {
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
                world.getRegistryManager().getOptional(RegistryKeys.CONFIGURED_FEATURE).flatMap((registry) ->
                        registry.getEntry(tree)).ifPresent((reference) ->
                        reference.value().generate(world, world.getChunkManager().getChunkGenerator(),
                                random, pos));
            }
        }
    }
}
