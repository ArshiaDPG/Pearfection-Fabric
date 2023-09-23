package net.digitalpear.pearfection.common.blocks;

import net.digitalpear.pearfection.init.PearConfiguredFeatures;
import net.digitalpear.pearfection.init.tags.PearBlockTags;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class CalleryVineBlock extends TallPlantBlock implements Fertilizable {
    public RegistryKey<ConfiguredFeature<?, ?>> hugePear;
    public RegistryKey<ConfiguredFeature<?, ?>> enormousPear;
    protected static final VoxelShape TOP = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 12.0D, 15.0D);
    protected static final VoxelShape BOTTOM = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

    public CalleryVineBlock(RegistryKey<ConfiguredFeature<?, ?>> hugePear, RegistryKey<ConfiguredFeature<?, ?>> enormousPear, Settings settings) {
        super(settings);
        this.hugePear = hugePear;
        this.enormousPear = enormousPear;
    }
    public CalleryVineBlock(RegistryKey<ConfiguredFeature<?, ?>> hugePear, Settings settings) {
        super(settings);
        this.hugePear = hugePear;
        this.enormousPear = hugePear;
    }
    public CalleryVineBlock(Settings settings) {
        super(settings.ticksRandomly());
        this.hugePear = PearConfiguredFeatures.HUGE_PEAR;
        this.enormousPear = PearConfiguredFeatures.ENORMOUS_PEAR;
    }



    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        BlockState dirt = world.getBlockState(state.get(HALF) == DoubleBlockHalf.UPPER ? pos.down(2) : pos.down());
        return dirt.isIn(PearBlockTags.PEAR_GROWABLE_ON);
    }


    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape SHAPE = state.get(HALF) == DoubleBlockHalf.UPPER ? TOP : BOTTOM;
        Vec3d vec3d = state.getModelOffset(world, pos);
        return SHAPE.offset(vec3d.x, vec3d.y, vec3d.z);
    }
    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return false;
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        BlockState dirt = world.getBlockState(state.get(HALF) == DoubleBlockHalf.UPPER ? pos.down(2) : pos.down());
        if (!world.isClient() && dirt.isIn(PearBlockTags.ENORMOUS_PEAR_GROWABLE_ON)) {
            world.syncWorldEvent(3009, pos, 0);
        }
        super.onBlockAdded(state, world, pos, oldState, notify);
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        if (pos.getY() <= world.getBottomY() + 1 && pos.getY() + 4 < world.getTopY()) {
            return;
        }
        BlockState dirt = world.getBlockState(state.get(HALF) == DoubleBlockHalf.UPPER ? pos.down(2) : pos.down());
        boolean hasSpaceForEnormous = true;

        BlockPos startingPos = state.get(HALF) == DoubleBlockHalf.UPPER ? pos.down() : pos;
        world.setBlockState(startingPos, Blocks.AIR.getDefaultState(), 1);
        world.setBlockState(startingPos.up(), Blocks.AIR.getDefaultState(), 1);

        for (BlockPos pos1 : BlockPos.iterate(pos, pos.add(1, 10, 1))) {
            if ((world.getBlockState(pos1).isIn(PearBlockTags.HUGE_PEAR_CANNOT_REPLACE) || world.getBlockState(pos1).getBlock().getHardness() >= 1.5 || world.getBlockState(pos1).isOf(Blocks.WATER))){
                hasSpaceForEnormous = false;
                break;
            }
        }

        if (hasSpaceForEnormous && dirt.isIn(PearBlockTags.ENORMOUS_PEAR_GROWABLE_ON)){
            world.getRegistryManager().getOptional(RegistryKeys.CONFIGURED_FEATURE).flatMap((registry) ->
                    registry.getEntry(enormousPear)).ifPresent((reference) ->
                    reference.value().generate(world, world.getChunkManager().getChunkGenerator(),
                            random, startingPos));
        }
        else{
            world.getRegistryManager().getOptional(RegistryKeys.CONFIGURED_FEATURE).flatMap((registry) ->
                    registry.getEntry(hugePear)).ifPresent((reference) ->
                    reference.value().generate(world, world.getChunkManager().getChunkGenerator(),
                            random, startingPos));
        }

    }
}