package net.digitalpear.pearfection.common.blocks.compat;

import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.NotNull;

/*
    Taken from https://github.com/FrozenBlock/WilderWild/blob/master/src/main/java/net/frozenblock/wilderwild/block/HollowedLogBlock.java
    for compat purposes
 */
public class HollowedLogBlock extends PillarBlock implements Waterloggable {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    protected static final VoxelShape X_SHAPE = VoxelShapes.union(Block.createCuboidShape(0, 0, 0, 16, 16, 3), Block.createCuboidShape(0, 13, 0, 16, 16, 16), Block.createCuboidShape(0, 0, 13, 16, 16, 16), Block.createCuboidShape(0, 0, 0, 16, 3, 16));
    protected static final VoxelShape Y_SHAPE = VoxelShapes.union(Block.createCuboidShape(0, 0, 0, 16, 16, 3), Block.createCuboidShape(0, 0, 0, 3, 16, 16), Block.createCuboidShape(0, 0, 13, 16, 16, 16), Block.createCuboidShape(13, 0, 0, 16, 16, 16));
    protected static final VoxelShape Z_SHAPE = VoxelShapes.union(Block.createCuboidShape(13, 0, 0, 16, 16, 16), Block.createCuboidShape(0, 0, 0, 3, 16, 16), Block.createCuboidShape(0, 13, 0, 16, 16, 16), Block.createCuboidShape(0, 0, 0, 16, 3, 16));
    protected static final VoxelShape RAYCAST_SHAPE = VoxelShapes.fullCube();

    public HollowedLogBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false).with(AXIS, Direction.Axis.Y));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, @NotNull BlockView level, @NotNull BlockPos pos, @NotNull ShapeContext context) {
        return switch (state.get(AXIS)) {
            default -> X_SHAPE;
            case Y -> Y_SHAPE;
            case Z -> Z_SHAPE;
        };
    }

    @Override
    public VoxelShape getRaycastShape(@NotNull BlockState state, @NotNull BlockView level, @NotNull BlockPos pos) {
        return RAYCAST_SHAPE;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(AXIS, ctx.getPlayerLookDirection().getAxis()).with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).isOf(Fluids.WATER));
    }



    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull WorldAccess level, @NotNull BlockPos currentPos, @NotNull BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            level.scheduleFluidTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(level));
        }
        level.scheduleBlockTick(currentPos, this, 1);
        return super.getStateForNeighborUpdate(state, direction, neighborState, level, currentPos, neighborPos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }


    @Override
    protected void appendProperties(StateManager.@NotNull Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(WATERLOGGED);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }


    @Override
    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return !(Boolean) state.get(WATERLOGGED);
    }

    @Override
    public boolean hasSidedTransparency(@NotNull BlockState state) {
        return true;
    }
}