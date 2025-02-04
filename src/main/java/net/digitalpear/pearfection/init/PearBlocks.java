package net.digitalpear.pearfection.init;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.digitalpear.nears.init.data.NFoodComponents;
import net.digitalpear.pearfection.Pearfection;
import net.digitalpear.pearfection.common.blocks.*;
import net.digitalpear.pearfection.init.data.PearConsumableComponents;
import net.digitalpear.pearfection.init.data.PearFoodComponents;
import net.digitalpear.pearfection.init.data.Woodset;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.*;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.Direction;

import java.util.Map;
import java.util.function.Function;

public class PearBlocks {
    public static MapColor calleryColor = MapColor.YELLOW;
    public static MapColor callerySideColor = MapColor.BROWN;

    private static RegistryKey<Block> keyOf(String name) {
        return RegistryKey.of(RegistryKeys.BLOCK, Pearfection.id(name));
    }

    public static Item createBlockItem(Block block, Item.Settings item){
        return Items.register(block, item);
    }

    public static Block createBlockWithItem(String blockID, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings){
        Block block = Blocks.register(keyOf(blockID), factory, settings);
        Items.register(block);
        return block;
    }
    public static Block createBlockWithoutItem(String blockID, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings){
        return Blocks.register(keyOf(blockID), factory, settings);
    }

    public static AbstractBlock.Settings createLampearSettings(){
        return AbstractBlock.Settings.create().nonOpaque().strength(0.5f, 0.1f).mapColor(calleryColor).sounds(PearSoundEvents.BLOCK_SOUND_PEAR);
    }

    public static Block createLampear(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings, FoodComponent foodComponent, ConsumableComponent component, Rarity rarity){
        Block block = createLampearWithoutItem(name, factory, settings);
        createBlockItem(block, new Item.Settings().food(foodComponent, component).rarity(rarity));
        return block;
    }
    public static Block createLampear(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings, FoodComponent foodComponent, ConsumableComponent component){
        return createLampear(name, factory, settings, foodComponent, component, Rarity.COMMON);
    }
    public static Block createLampearWithoutItem(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings){
        return Blocks.register(keyOf(name), factory, settings);
    }




    public static final Block CALLERY_VINE = createBlockWithItem("callery_vine", CalleryVineBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_SAPLING).sounds(BlockSoundGroup.CHERRY_SAPLING).offset(AbstractBlock.OffsetType.XZ).mapColor(MapColor.EMERALD_GREEN));

    public static final Block CALLERY_SPROUT = createBlockWithItem("callery_sprout", CallerySrpoutBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_SAPLING).sounds(BlockSoundGroup.CHERRY_SAPLING).offset(AbstractBlock.OffsetType.XZ).mapColor(MapColor.EMERALD_GREEN));
    public static final Block POTTED_CALLERY_SPROUT = createBlockWithoutItem("potted_callery_sprout", settings -> new FlowerPotBlock(CALLERY_SPROUT, settings), AbstractBlock.Settings.copy(Blocks.POTTED_ACACIA_SAPLING));

    public static final Block CALLERY_TWIG = createBlockWithItem("callery_twig", CalleryTwigBlock::new, AbstractBlock.Settings.copy(Blocks.DEAD_BUSH).offset(AbstractBlock.OffsetType.XZ).mapColor(MapColor.BROWN));
    public static final Block POTTED_CALLERY_TWIG = createBlockWithoutItem("potted_callery_twig", settings -> new FlowerPotBlock(CALLERY_TWIG, settings), AbstractBlock.Settings.copy(Blocks.POTTED_ACACIA_SAPLING));

    public static final Block LAMPEAR = createLampear("lampear", LampearBlock::new,
            createLampearSettings().luminance((state) -> 8),
            PearFoodComponents.LAMPEAR, PearConsumableComponents.LAMPEAR);

    public static final Block COPPER_LAMPEAR = createLampear("copper_lampear", LampearBlock::new,
            createLampearSettings().luminance((state) -> 14).strength(0.7f, 0.3f).mapColor(Blocks.COPPER_BLOCK.getDefaultMapColor()),
            PearFoodComponents.COPPER_LAMPEAR, PearConsumableComponents.COPPER_LAMPEAR, Rarity.RARE);


    public static final Block LAMPEAR_BLOCK = createBlockWithItem("lampear_block", PearBaseBlock::new, AbstractBlock.Settings.create()
            .mapColor(state -> state.get(MushroomBlock.UP) ? calleryColor : MapColor.WHITE)
            .sounds(PearSoundEvents.BLOCK_SOUND_PEAR)
            .strength(0.75f).luminance(state -> 12)
    );

    public static final Woodset CALLERY = new Woodset(Pearfection.id("callery"), calleryColor, callerySideColor, new Woodset.Settings.Builder().logName("stem").leaveSoundGroup(BlockSoundGroup.LEAF_LITTER).woodPreset(Woodset.WoodPreset.FANCY));

    public static final Block FLOWERING_CALLERY_LEAVES = createBlockWithItem("flowering_callery_leaves", settings -> new CalleryLeavesBlock(0.01F, PearParticleTypes.CALLERY_FLOWER, settings, true), AbstractBlock.Settings.copy(CALLERY.getLeaves()).mapColor(MapColor.PALE_GREEN));

    public static void init() {

    }
}
