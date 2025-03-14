package net.digitalpear.pearfection.common.blocks.compat;

import com.google.common.collect.ImmutableMap;
import com.ibm.icu.impl.Pair;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Map;


/*
    Code taken from:
    https://github.com/Heccology/Bountiful-Fares/blob/main/src/main/java/net/hecco/bountifulfares/block/custom/PicketsBlock.java
    I did not write anything in this class.
 */
public class PicketsBlock extends Block implements Waterloggable {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final BooleanProperty NORTH = Properties.NORTH;
    public static final BooleanProperty EAST = Properties.EAST;
    public static final BooleanProperty SOUTH = Properties.SOUTH;
    public static final BooleanProperty WEST = Properties.WEST;
    public static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 14.0, 16.0, 8.0, 16.0);
    public static final VoxelShape EAST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 2.0, 8.0, 16.0);
    public static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 2.0);
    public static final VoxelShape WEST_SHAPE = Block.createCuboidShape(14.0, 0.0, 0.0, 16.0, 8.0, 16.0);
    public static final VoxelShape NORTH_COLL = Block.createCuboidShape(0.0, 0.0, 14.0, 16.0, 10.0, 16.0);
    public static final VoxelShape EAST_COLL = Block.createCuboidShape(0.0, 0.0, 0.0, 2.0, 10.0, 16.0);
    public static final VoxelShape SOUTH_COLL = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 10.0, 2.0);
    public static final VoxelShape WEST_COLL = Block.createCuboidShape(14.0, 0.0, 0.0, 16.0, 10.0, 16.0);

    public static final ImmutableMap<BooleanProperty, Pair<VoxelShape, VoxelShape>> SHAPE_FROM_DIRECTION = new ImmutableMap.Builder<BooleanProperty, Pair<VoxelShape, VoxelShape>>()
            .put(NORTH, Pair.of(NORTH_SHAPE, NORTH_COLL))
            .put(SOUTH, Pair.of(SOUTH_SHAPE, SOUTH_COLL))
            .put(EAST, Pair.of(EAST_SHAPE, EAST_COLL))
            .put(WEST, Pair.of(WEST_SHAPE, WEST_COLL))
            .build();

    public PicketsBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false).with(WATERLOGGED, false));
    }

    @Override
    public boolean isEnabled(FeatureSet enabledFeatures) {
        return FabricLoader.getInstance().isModLoaded("bountifulfares");
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape shape = VoxelShapes.empty();
        for (Map.Entry<BooleanProperty, Pair<VoxelShape, VoxelShape>> entry : SHAPE_FROM_DIRECTION.entrySet()) {
            BooleanProperty b = entry.getKey();
            VoxelShape p = entry.getValue().first;

            if (state.get(b)){
                shape = VoxelShapes.union(shape, p);
            }
        }
        if (shape.isEmpty()){
            return super.getOutlineShape(state, world, pos, context);
        }
        return shape;
    }

    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape shape = VoxelShapes.empty();
        for (Map.Entry<BooleanProperty, Pair<VoxelShape, VoxelShape>> entry : SHAPE_FROM_DIRECTION.entrySet()) {
            BooleanProperty b = entry.getKey();
            VoxelShape p = entry.getValue().second;

            if (state.get(b)){
                shape = VoxelShapes.union(shape, p);
            }
        }
        if (shape.isEmpty()){
            return super.getOutlineShape(state, world, pos, context);
        }
        return shape;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, WATERLOGGED);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction playerDir = ctx.getHorizontalPlayerFacing().getOpposite();
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        if (blockState.isOf(this)) {
            Direction facingDirection = this.getFacingDirection(blockState);
            return facingDirection != playerDir ? super.getStateWithProperties(blockState).with(this.getFacingProperty(playerDir), true) : (BlockState)super.getPlacementState(ctx).with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
        } else {
            return super.getDefaultState().with(this.getFacingProperty(playerDir), true).with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
        }
    }

    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        Direction playerDir = context.getHorizontalPlayerFacing().getOpposite();
        Direction facingDirection = this.getFacingDirection(state);
        return !context.shouldCancelInteraction() && context.getStack().getItem() == this.asItem() && facingDirection != playerDir || super.canReplace(state, context);
    }

    private Direction getFacingDirection(BlockState blockState) {
        if (blockState.get(NORTH)) {
            return Direction.NORTH;
        } else if (blockState.get(EAST)) {
            return Direction.EAST;
        } else if (blockState.get(SOUTH)) {
            return Direction.SOUTH;
        } else {
            return blockState.get(WEST) ? Direction.WEST : Direction.NORTH;
        }
    }

    private Property<Boolean> getFacingProperty(Direction direction) {
        return switch (direction) {
            case EAST -> EAST;
            case SOUTH -> SOUTH;
            case WEST -> WEST;
            default -> NORTH;
        };
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return switch (rotation) {
            case CLOCKWISE_180 ->
                    state.with(NORTH, state.get(SOUTH)).with(EAST, state.get(WEST)).with(SOUTH, state.get(NORTH)).with(WEST, state.get(EAST));
            case COUNTERCLOCKWISE_90 ->
                    state.with(NORTH, state.get(EAST)).with(EAST, state.get(SOUTH)).with(SOUTH, state.get(WEST)).with(WEST, state.get(NORTH));
            case CLOCKWISE_90 ->
                    state.with(NORTH, state.get(WEST)).with(EAST, state.get(NORTH)).with(SOUTH, state.get(EAST)).with(WEST, state.get(SOUTH));
            default -> state;
        };
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        BlockState var10000 = switch (mirror) {
            case LEFT_RIGHT ->
                    state.with(NORTH, state.get(SOUTH)).with(SOUTH, state.get(NORTH));
            case FRONT_BACK ->
                    state.with(EAST, state.get(WEST)).with(WEST, state.get(EAST));
            default -> super.mirror(state, mirror);
        };

        return var10000;
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }
    @Override
    protected boolean canPathfindThrough(BlockState state, NavigationType type) {
        return false;
    }
}
