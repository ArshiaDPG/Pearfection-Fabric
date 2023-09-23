package net.digitalpear.pearfection.common.blocks;

import net.digitalpear.pearfection.init.PearBlocks;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class CalleryTwigBlock extends PlantBlock implements Fertilizable {
    public static final int MAX_AGE = 7;
    public static final IntProperty AGE = Properties.AGE_7;
    protected static final VoxelShape SHAPE = Block.createCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 8.0D, 11.0D);

    public CalleryTwigBlock(Settings settings) {
        super(settings.ticksRandomly());
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));
    }


    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
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
        Vec3d vec3d = state.getModelOffset(world, pos);
        return SHAPE.offset(vec3d.x, vec3d.y, vec3d.z);
    }

    protected int getGrowthAmount(World world) {
        return world.random.nextBetween(1, 3);
    }

    public void applyGrowth(World world, BlockPos pos, BlockState state) {
        int currentAge = state.get(AGE) + this.getGrowthAmount(world);
        if (currentAge > MAX_AGE) {
            currentAge = MAX_AGE;
        }
        world.setBlockState(pos, getStateWithProperties(state).with(AGE, currentAge), 2);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(AGE) <= MAX_AGE;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextFloat() > 0.4) {
            grow(world, random, pos, state);
        }
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return false;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        if (state.get(AGE) < MAX_AGE){
            applyGrowth(world, pos, state);
        }
        else{
            world.setBlockState(pos, PearBlocks.CALLERY_SPROUT.getDefaultState());
        }
    }
}