package net.digitalpear.pearfection.init;

import net.digitalpear.pearfection.Pearfection;
import net.digitalpear.pearfection.common.blocks.CallerySrpoutBlock;
import net.digitalpear.pearfection.common.blocks.CalleryTwigBlock;
import net.digitalpear.pearfection.common.blocks.CalleryVineBlock;
import net.digitalpear.pearfection.common.blocks.LampearBlock;
import net.digitalpear.pearfection.init.data.PearFoodComponents;
import net.digitalpear.pearfection.init.data.Woodset;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeRegistry;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class PearBlocks {
    public static MapColor calleryColor = MapColor.YELLOW;
    public static MapColor callerySideColor = MapColor.BROWN;


    public static final WoodType CALLERY_WOOD_TYPE = WoodTypeRegistry.register(Pearfection.id("callery"),
            BlockSetTypeRegistry.registerWood(Pearfection.id("callery")), BlockSoundGroup.CHERRY_WOOD,
            BlockSoundGroup.CHERRY_WOOD_HANGING_SIGN,
            SoundEvents.BLOCK_CHERRY_WOOD_FENCE_GATE_CLOSE,
            SoundEvents.BLOCK_CHERRY_WOOD_FENCE_GATE_OPEN);


    public static BlockItem createBlockItem(String blockID, Block block){
        return Registry.register(Registries.ITEM, new Identifier(Pearfection.MOD_ID, blockID), new BlockItem(block, new Item.Settings()));
    }
    public static BlockItem createBlockItem(String blockID, Block block, Item.Settings item){
        return Registry.register(Registries.ITEM, new Identifier(Pearfection.MOD_ID, blockID), new BlockItem(block, item));
    }

    public static Block createBlockWithItem(String blockID, Block block){
        createBlockItem(blockID, block);
        return Registry.register(Registries.BLOCK, new Identifier(Pearfection.MOD_ID, blockID), block);
    }
    public static Block createBlockWithoutItem(String blockID, Block block){
        return Registry.register(Registries.BLOCK, new Identifier(Pearfection.MOD_ID, blockID), block);
    }

    public static Block createLampear(String name, Block block, FoodComponent foodComponent, Rarity rarity){
        createBlockItem(name, block, new Item.Settings().food(foodComponent).rarity(rarity));
        return Registry.register(Registries.BLOCK, new Identifier(Pearfection.MOD_ID, name), block);
    }
    public static Block createLampear(String name, Block block, FoodComponent foodComponent){
        return createLampear(name, block, foodComponent, Rarity.COMMON);
    }


    public static final Block CALLERY_VINE = createBlockWithItem("callery_vine", new CalleryVineBlock(AbstractBlock.Settings.copy(Blocks.OAK_SAPLING).sounds(BlockSoundGroup.CHERRY_SAPLING).offset(AbstractBlock.OffsetType.XZ).mapColor(MapColor.EMERALD_GREEN)));

    public static final Block CALLERY_SPROUT = createBlockWithItem("callery_sprout", new CallerySrpoutBlock(AbstractBlock.Settings.copy(Blocks.OAK_SAPLING).sounds(BlockSoundGroup.CHERRY_SAPLING).offset(AbstractBlock.OffsetType.XZ).mapColor(MapColor.EMERALD_GREEN)));
    public static final Block POTTED_CALLERY_SPROUT = createBlockWithoutItem("potted_callery_sprout", new FlowerPotBlock(CALLERY_SPROUT, AbstractBlock.Settings.copy(Blocks.POTTED_ACACIA_SAPLING)));

    public static final Block CALLERY_TWIG = createBlockWithItem("callery_twig", new CalleryTwigBlock(AbstractBlock.Settings.copy(Blocks.DEAD_BUSH).offset(AbstractBlock.OffsetType.XZ).mapColor(MapColor.BROWN)));
    public static final Block POTTED_CALLERY_TWIG = createBlockWithoutItem("potted_callery_twig", new FlowerPotBlock(CALLERY_TWIG, AbstractBlock.Settings.copy(Blocks.POTTED_ACACIA_SAPLING)));

    public static final Block LAMPEAR = createLampear("lampear", new LampearBlock(AbstractBlock.Settings.create()
            .luminance((state) -> 8).nonOpaque().strength(0.5f, 0.1f).mapColor(calleryColor).sounds(PearSoundEvents.BLOCK_SOUND_PEAR)),
            PearFoodComponents.LAMPEAR);

    public static final Block COPPER_LAMPEAR = createLampear("copper_lampear", new LampearBlock(AbstractBlock.Settings.create()
            .luminance((state) -> 14).nonOpaque().strength(0.7f, 0.3f).mapColor(Blocks.COPPER_BLOCK.getDefaultMapColor())
                    .sounds(PearSoundEvents.BLOCK_SOUND_PEAR)), PearFoodComponents.COPPER_LAMPEAR, Rarity.RARE);


    public static final Block LAMPEAR_BLOCK = createBlockWithItem("lampear_block", new MushroomBlock(AbstractBlock.Settings.create()
            .mapColor(state -> state.get(MushroomBlock.UP) ? calleryColor : MapColor.WHITE)
            .sounds(PearSoundEvents.BLOCK_SOUND_PEAR)
            .strength(0.75f).luminance(state -> 10)));


    public static final Woodset CALLERY = new Woodset("callery", calleryColor, callerySideColor, CALLERY_WOOD_TYPE);

    public static final Block CALLERY_STEM = CALLERY.createLog(true, false, false);
    public static final Block STRIPPED_CALLERY_STEM = CALLERY.createLog(true, true, false);

    public static final Block HOLLOWED_CALLERY_STEM = CALLERY.createHollowedLog(CALLERY_STEM);

    public static final Block CALLERY_LEAVES = CALLERY.createLeaves();
    public static final Block FLOWERING_CALLERY_LEAVES = CALLERY.createFloweringLeaves(MapColor.PALE_GREEN);

    public static final Block CALLERY_WOOD = CALLERY.createLog(false, false, true);
    public static final Block STRIPPED_CALLERY_WOOD = CALLERY.createLog(false, true, true);

    public static final Block CALLERY_PLANKS = CALLERY.createPlanks();
    public static final Block CALLERY_STAIRS = CALLERY.createStairs();
    public static final Block CALLERY_SLAB = CALLERY.createSlab();

    public static final Block CALLERY_BUTTON = CALLERY.createButton();
    public static final Block CALLERY_PRESSURE_PLATE = CALLERY.createPressurePlate();

    public static final Block CALLERY_FENCE = CALLERY.createFence();
    public static final Block CALLERY_FENCE_GATE = CALLERY.createFenceGate();

    public static final Block CALLERY_DOOR = CALLERY.createDoor();
    public static final Block CALLERY_TRAPDOOR = CALLERY.createTrapDoor();

    public static final Block CALLERY_SIGN = CALLERY.createSign();
    public static final Block CALLERY_WALL_SIGN = CALLERY.createWallSign();

    public static final Block CALLERY_HANGING_SIGN = CALLERY.createHangingSign();
    public static final Block CALLERY_WALL_HANGING_SIGN = CALLERY.createWallHangingSign();


    public static void init() {
    }
}
