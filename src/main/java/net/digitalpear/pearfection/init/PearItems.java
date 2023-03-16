package net.digitalpear.pearfection.init;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import com.terraformersmc.terraform.boat.impl.item.TerraformBoatItem;
import net.digitalpear.pearfection.Pearfection;
import net.digitalpear.pearfection.init.data.PearFoodComponents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.util.Identifier;


public class PearItems {

    public static Item createItem(String blockID, Item item){
        return Registry.register(Registries.ITEM, new Identifier(Pearfection.MOD_ID, blockID), item);
    }
    public static Item createBoatItem(TerraformBoatType type) {
        return new TerraformBoatItem(TerraformBoatTypeRegistry.INSTANCE.getKey(type).get(), false, new Item.Settings().maxCount(1));
    }
    public static Item createChestBoatItem(TerraformBoatType type) {
        return new TerraformBoatItem(TerraformBoatTypeRegistry.INSTANCE.getKey(type).get(), true, new Item.Settings().maxCount(1));
    }

    public static Item createSignItem(Block sign, Block wallSign) {
        return new SignItem(new FabricItemSettings().maxCount(16), sign, wallSign);
    }
    public static Item createHangingSignItem(Block sign, Block wallSign) {
        return new HangingSignItem(sign, wallSign, new FabricItemSettings().maxCount(16).requires(FeatureFlags.UPDATE_1_20));
    }

    public static final Item CALLERY_BOAT = createItem("callery_boat", createBoatItem(PearBoatTypes.CALLERY));
    public static final Item CALLERY_CHEST_BOAT = createItem("callery_chest_boat", createChestBoatItem(PearBoatTypes.CALLERY));

    public static final Item CALLERY_SIGN = createItem("callery_sign", createSignItem(PearBlocks.CALLERY_SIGN, PearBlocks.CALLERY_WALL_SIGN));
    public static final Item CALLERY_HANGING_SIGN = createItem("callery_hanging_sign", createHangingSignItem(PearBlocks.CALLERY_HANGING_SIGN, PearBlocks.CALLERY_WALL_HANGING_SIGN));

    public static final Item PEAR_TART = createItem("pear_tart", new Item(new Item.Settings().food(PearFoodComponents.PEAR_TART)));


    public static void init(){
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
            entries.addAfter(Items.MANGROVE_CHEST_BOAT, CALLERY_CHEST_BOAT);
            entries.addAfter(Items.MANGROVE_CHEST_BOAT, CALLERY_BOAT);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
            entries.addAfter(Items.MANGROVE_BUTTON, PearBlocks.CALLERY_BUTTON);
            entries.addAfter(Items.MANGROVE_BUTTON, PearBlocks.CALLERY_PRESSURE_PLATE);
            entries.addAfter(Items.MANGROVE_BUTTON, PearBlocks.CALLERY_TRAPDOOR);
            entries.addAfter(Items.MANGROVE_BUTTON, PearBlocks.CALLERY_DOOR);
            entries.addAfter(Items.MANGROVE_BUTTON, PearBlocks.CALLERY_FENCE_GATE);
            entries.addAfter(Items.MANGROVE_BUTTON, PearBlocks.CALLERY_FENCE);
            entries.addAfter(Items.MANGROVE_BUTTON, PearBlocks.CALLERY_SLAB);
            entries.addAfter(Items.MANGROVE_BUTTON, PearBlocks.CALLERY_STAIRS);
            entries.addAfter(Items.MANGROVE_BUTTON, PearBlocks.CALLERY_PLANKS);
            entries.addAfter(Items.MANGROVE_BUTTON, PearBlocks.STRIPPED_CALLERY_WOOD);
            entries.addAfter(Items.MANGROVE_BUTTON, PearBlocks.CALLERY_WOOD);
            entries.addAfter(Items.MANGROVE_BUTTON, PearBlocks.STRIPPED_CALLERY_STEM);
            entries.addAfter(Items.MANGROVE_BUTTON, PearBlocks.CALLERY_STEM);
            if (FabricLoader.getInstance().isModLoaded("wilderwild")){
                entries.addAfter(PearBlocks.CALLERY_STEM, PearBlocks.HOLLOWED_CALLERY_STEM);
            }
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> {
            entries.addAfter(Items.MANGROVE_PROPAGULE, PearBlocks.CALLERY_VINE);
            entries.addAfter(PearBlocks.CALLERY_VINE, PearBlocks.CALLERY_SPROUT);
            entries.addAfter(PearBlocks.CALLERY_SPROUT, PearBlocks.CALLERY_TWIG);

            entries.addAfter(Items.RED_MUSHROOM_BLOCK, PearBlocks.LAMPEAR_BLOCK);
            entries.addAfter(Items.MANGROVE_LEAVES, PearBlocks.CALLERY_LEAVES);
            entries.addAfter(PearBlocks.CALLERY_LEAVES, PearBlocks.FLOWERING_CALLERY_LEAVES);

            entries.addAfter(Items.MANGROVE_LOG, PearBlocks.CALLERY_STEM);
            if (FabricLoader.getInstance().isModLoaded("wilderwild")){
                entries.addAfter(PearBlocks.CALLERY_STEM, PearBlocks.HOLLOWED_CALLERY_STEM);
            }
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> {
            entries.addAfter(Items.ENCHANTED_GOLDEN_APPLE, PearBlocks.COPPER_LAMPEAR);
            entries.addAfter(Items.ENCHANTED_GOLDEN_APPLE, PearBlocks.LAMPEAR);
            entries.addAfter(Items.PUMPKIN_PIE, PEAR_TART);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> {
            entries.addAfter(Items.SOUL_LANTERN, PearBlocks.COPPER_LAMPEAR);
            entries.addAfter(Items.SOUL_LANTERN, PearBlocks.LAMPEAR);
            entries.addAfter(Items.MANGROVE_HANGING_SIGN, CALLERY_HANGING_SIGN);
            entries.addAfter(Items.MANGROVE_HANGING_SIGN, CALLERY_SIGN);

//            if (FabricLoader.getInstance().isModLoaded("charm")){
//                entries.addAfter(Blocks.BOOKSHELF, PearBlocks.CALLERY_BOOKSHELF);
//            }
        });
    }
}
