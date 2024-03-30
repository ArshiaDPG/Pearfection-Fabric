package net.digitalpear.pearfection.common.blocks.compat;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.resource.featuretoggle.FeatureFlag;
import net.minecraft.resource.featuretoggle.FeatureFlags;
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

import java.util.ArrayList;
import java.util.List;


/*
    Code taken from:
    https://github.com/Heccology/Bountiful-Fares/blob/main/src/main/java/net/hecco/bountifulfares/block/custom/PicketsBlock.java
    I did not write anything in this class.
 */
public class PicketsBlock extends Block implements Waterloggable {
    public static final BooleanProperty WATERLOGGED;
    public static final BooleanProperty NORTH;
    public static final BooleanProperty EAST;
    public static final BooleanProperty SOUTH;
    public static final BooleanProperty WEST;
    public static final VoxelShape NORTH_SHAPE;
    public static final VoxelShape EAST_SHAPE;
    public static final VoxelShape SOUTH_SHAPE;
    public static final VoxelShape WEST_SHAPE;
    public static final VoxelShape NORTH_COLL;
    public static final VoxelShape EAST_COLL;
    public static final VoxelShape SOUTH_COLL;
    public static final VoxelShape WEST_COLL;

    public PicketsBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false).with(WATERLOGGED, false));
    }

    @Override
    public boolean isEnabled(FeatureSet enabledFeatures) {
        return FabricLoader.getInstance().isModLoaded("bountifulfares");
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        List<VoxelShape> SHAPES = new ArrayList();
        if (state.get(NORTH)) {
            SHAPES.add(NORTH_SHAPE);
        }

        if (state.get(EAST)) {
            SHAPES.add(EAST_SHAPE);
        }

        if (state.get(SOUTH)) {
            SHAPES.add(SOUTH_SHAPE);
        }

        if (state.get(WEST)) {
            SHAPES.add(WEST_SHAPE);
        }

        if (SHAPES.isEmpty()) {
            return super.getOutlineShape(state, world, pos, context);
        } else {
            VoxelShape result = SHAPES.get(0);

            for(int i = 1; i < SHAPES.size(); ++i) {
                result = VoxelShapes.union(result, SHAPES.get(i));
            }

            return result;
        }
    }

    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        List<VoxelShape> COLLISION = new ArrayList();
        if (state.get(NORTH)) {
            COLLISION.add(NORTH_COLL);
        }

        if ((Boolean)state.get(EAST)) {
            COLLISION.add(EAST_COLL);
        }

        if ((Boolean)state.get(SOUTH)) {
            COLLISION.add(SOUTH_COLL);
        }

        if ((Boolean)state.get(WEST)) {
            COLLISION.add(WEST_COLL);
        }

        if (COLLISION.isEmpty()) {
            return super.getCollisionShape(state, world, pos, context);
        } else {
            VoxelShape result = (VoxelShape)COLLISION.get(0);

            for(int i = 1; i < COLLISION.size(); ++i) {
                result = VoxelShapes.union(result, (VoxelShape)COLLISION.get(i));
            }

            return result;
        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{NORTH, EAST, SOUTH, WEST, WATERLOGGED});
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction playerDir = ctx.getHorizontalPlayerFacing().getOpposite();
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        if (blockState.isOf(this)) {
            Direction facingDirection = this.getFacingDirection(blockState);
            return facingDirection != playerDir ? (BlockState)super.getStateWithProperties(blockState).with(this.getFacingProperty(playerDir), true) : (BlockState)super.getPlacementState(ctx).with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
        } else {
            return (BlockState)((BlockState)super.getDefaultState().with(this.getFacingProperty(playerDir), true)).with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
        }
    }

    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        Direction playerDir = context.getHorizontalPlayerFacing().getOpposite();
        Direction facingDirection = this.getFacingDirection(state);
        return !context.shouldCancelInteraction() && context.getStack().getItem() == this.asItem() && facingDirection != playerDir || super.canReplace(state, context);
    }

    private Direction getFacingDirection(BlockState blockState) {
        if ((Boolean)blockState.get(NORTH)) {
            return Direction.NORTH;
        } else if ((Boolean)blockState.get(EAST)) {
            return Direction.EAST;
        } else if ((Boolean)blockState.get(SOUTH)) {
            return Direction.SOUTH;
        } else {
            return (Boolean)blockState.get(WEST) ? Direction.WEST : Direction.NORTH;
        }
    }

    private Property<Boolean> getFacingProperty(Direction direction) {
        switch (direction) {
            case NORTH:
                return NORTH;
            case EAST:
                return EAST;
            case SOUTH:
                return SOUTH;
            case WEST:
                return WEST;
            default:
                return NORTH;
        }
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        BlockState var10000;
        switch (rotation) {
            case CLOCKWISE_180:
                var10000 = (BlockState)((BlockState)((BlockState)((BlockState)state.with(NORTH, (Boolean)state.get(SOUTH))).with(EAST, (Boolean)state.get(WEST))).with(SOUTH, (Boolean)state.get(NORTH))).with(WEST, (Boolean)state.get(EAST));
                break;
            case COUNTERCLOCKWISE_90:
                var10000 = (BlockState)((BlockState)((BlockState)((BlockState)state.with(NORTH, (Boolean)state.get(EAST))).with(EAST, (Boolean)state.get(SOUTH))).with(SOUTH, (Boolean)state.get(WEST))).with(WEST, (Boolean)state.get(NORTH));
                break;
            case CLOCKWISE_90:
                var10000 = (BlockState)((BlockState)((BlockState)((BlockState)state.with(NORTH, (Boolean)state.get(WEST))).with(EAST, (Boolean)state.get(NORTH))).with(SOUTH, (Boolean)state.get(EAST))).with(WEST, (Boolean)state.get(SOUTH));
                break;
            default:
                var10000 = state;
        }

        return var10000;
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        BlockState var10000;
        switch (mirror) {
            case LEFT_RIGHT:
                var10000 = (BlockState)((BlockState)state.with(NORTH, (Boolean)state.get(SOUTH))).with(SOUTH, (Boolean)state.get(NORTH));
                break;
            case FRONT_BACK:
                var10000 = (BlockState)((BlockState)state.with(EAST, (Boolean)state.get(WEST))).with(WEST, (Boolean)state.get(EAST));
                break;
            default:
                var10000 = super.mirror(state, mirror);
        }

        return var10000;
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if ((Boolean)state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public FluidState getFluidState(BlockState state) {
        return (Boolean)state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    static {
        WATERLOGGED = Properties.WATERLOGGED;
        NORTH = BooleanProperty.of("north");
        EAST = BooleanProperty.of("east");
        SOUTH = BooleanProperty.of("south");
        WEST = BooleanProperty.of("west");
        NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 14.0, 16.0, 8.0, 16.0);
        EAST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 2.0, 8.0, 16.0);
        SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 2.0);
        WEST_SHAPE = Block.createCuboidShape(14.0, 0.0, 0.0, 16.0, 8.0, 16.0);
        NORTH_COLL = Block.createCuboidShape(0.0, 0.0, 14.0, 16.0, 10.0, 16.0);
        EAST_COLL = Block.createCuboidShape(0.0, 0.0, 0.0, 2.0, 10.0, 16.0);
        SOUTH_COLL = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 10.0, 2.0);
        WEST_COLL = Block.createCuboidShape(14.0, 0.0, 0.0, 16.0, 10.0, 16.0);
    }
}
