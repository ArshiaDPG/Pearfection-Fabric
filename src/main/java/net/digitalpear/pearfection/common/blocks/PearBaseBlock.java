package net.digitalpear.pearfection.common.blocks;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.block.BlockState;
import net.minecraft.block.MushroomBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Map;

public class PearBaseBlock extends MushroomBlock {
    public static final Map<Direction, BooleanProperty> DIRECTION_TO_FACE = ImmutableMap.copyOf(Maps.newEnumMap(Map.of(Direction.NORTH, NORTH, Direction.EAST, EAST, Direction.SOUTH, SOUTH, Direction.WEST, WEST, Direction.UP, UP, Direction.DOWN, DOWN)));
    public PearBaseBlock(Settings settings) {
        super(settings);
    }


    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (stack.isIn(ItemTags.AXES) && state.get(DIRECTION_TO_FACE.get(hit.getSide()))){
            world.setBlockState(pos, state.with(DIRECTION_TO_FACE.get(hit.getSide()), false));
            player.swingHand(hand);
            stack.damage(1, player, LivingEntity.getSlotForHand(hand));
            world.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS);
            return ItemActionResult.SUCCESS;
        }
        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }
}
