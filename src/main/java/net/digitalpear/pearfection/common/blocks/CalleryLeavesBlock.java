package net.digitalpear.pearfection.common.blocks;

import net.digitalpear.pearfection.init.PearBlocks;
import net.digitalpear.pearfection.init.PearParticleTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.UntintedParticleLeavesBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.OptionalInt;

public class CalleryLeavesBlock extends UntintedParticleLeavesBlock implements Fertilizable {

    boolean bearsFruit;

    public CalleryLeavesBlock(float leafParticleChance, Settings settings, boolean bearsFruit) {
        this(leafParticleChance, PearParticleTypes.CALLERY_LEAF, settings, bearsFruit);
    }

    public CalleryLeavesBlock(float leafParticleChance, ParticleEffect particle, Settings settings, boolean bearsFruit) {
        super(leafParticleChance, particle, settings);
        this.bearsFruit = bearsFruit;
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.setBlockState(pos, updateDistanceFromLogs(state, world, pos), 3);
    }

    private static BlockState updateDistanceFromLogs(BlockState state, WorldAccess world, BlockPos pos) {
        int i = 7;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        Direction[] var5 = Direction.values();

        for (Direction direction : var5) {
            mutable.set(pos, direction);
            i = Math.min(i, getDistanceFromLog(world.getBlockState(mutable)) + 1);
            if (i == 1) {
                break;
            }
        }

        return state.with(DISTANCE, i);
    }

    private static int getDistanceFromLog(BlockState state) {
        return getOptionalDistanceFromLog(state).orElse(7);
    }

    public static OptionalInt getOptionalDistanceFromLog(BlockState state) {
        if (state.isIn(BlockTags.LOGS) || state.isOf(PearBlocks.LAMPEAR_BLOCK)) {
            return OptionalInt.of(0);
        } else {
            return state.contains(DISTANCE) ? OptionalInt.of(state.get(DISTANCE)) : OptionalInt.empty();
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return updateDistanceFromLogs(super.getPlacementState(ctx), ctx.getWorld(), ctx.getBlockPos());
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return this.bearsFruit && world.getBlockState(pos.down()).isAir();
    }

    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public BlockPos getFertilizeParticlePos(BlockPos pos) {
        return pos.down();
    }

    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        world.setBlockState(pos.down(), PearBlocks.LAMPEAR.getDefaultState().with(LampearBlock.HANGING, true), 2);
    }
}