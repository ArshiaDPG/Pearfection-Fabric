package net.digitalpear.pearfection.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

public class LampearBlock extends LanternBlock {
    protected static final VoxelShape STANDING_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D),
            Block.createCuboidShape(6.0D, 6.0D, 6.0D, 10.0D, 9.0D, 10.0D));
    protected static final VoxelShape HANGING_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(5.0D, 1.0D, 5.0D, 11.0D, 7.0D, 11.0D),
            Block.createCuboidShape(6.0D, 7.0D, 6.0D, 10.0D, 10.0D, 10.0D));

    public LampearBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(HANGING, false).with(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(HANGING) ? HANGING_SHAPE : STANDING_SHAPE;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Direction direction = attachedDirection(state).getOpposite();
        if (world.getBlockState(pos.offset(direction)).isIn(BlockTags.LEAVES)){
            return true;
        }
        else{
            return super.canPlaceAt(state, world, pos);
        }
    }
}
