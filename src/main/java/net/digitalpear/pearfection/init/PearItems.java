package net.digitalpear.pearfection.init;


import net.digitalpear.pearfection.Pearfection;
import net.digitalpear.pearfection.init.data.PearConsumableComponents;
import net.digitalpear.pearfection.init.data.PearFoodComponents;
import net.digitalpear.pearfection.init.data.Woodset;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;


public class PearItems {
    private static RegistryKey<Item> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.ITEM, Pearfection.id(id));
    }
    public static Item createItem(String itemName, Item.Settings settings){
        return Items.register(keyOf(itemName), Item::new, settings);
    }

    public static final Item PEAR_TART = createItem("pear_tart", new Item.Settings().food(PearFoodComponents.PEAR_TART, PearConsumableComponents.PEAR_TART));

    public static void init(){
        Woodset.addToBuildingTab(Items.MANGROVE_BUTTON, PearBlocks.CALLERY);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> {
            entries.addAfter(Items.MANGROVE_PROPAGULE, PearBlocks.CALLERY_VINE);
            entries.addAfter(PearBlocks.CALLERY_VINE, PearBlocks.CALLERY_SPROUT);
            entries.addAfter(PearBlocks.CALLERY_SPROUT, PearBlocks.CALLERY_TWIG);

            entries.addAfter(Items.RED_MUSHROOM_BLOCK, PearBlocks.LAMPEAR_BLOCK);
            entries.addAfter(Items.MANGROVE_LEAVES, PearBlocks.CALLERY.getLeaves());
            entries.addAfter(PearBlocks.CALLERY.getLeaves(), PearBlocks.FLOWERING_CALLERY_LEAVES);

            entries.addAfter(Items.MANGROVE_LOG, PearBlocks.CALLERY.getLog());
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> {
            entries.addAfter(Items.ENCHANTED_GOLDEN_APPLE, PearBlocks.COPPER_LAMPEAR);
            entries.addAfter(Items.ENCHANTED_GOLDEN_APPLE, PearBlocks.LAMPEAR);
            entries.addAfter(Items.PUMPKIN_PIE, PEAR_TART);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> {
            entries.addAfter(Items.SOUL_LANTERN, PearBlocks.LAMPEAR);
            entries.addAfter(PearBlocks.LAMPEAR, PearBlocks.COPPER_LAMPEAR);

            entries.addAfter(Items.MANGROVE_HANGING_SIGN, PearBlocks.CALLERY.getSignItem(), PearBlocks.CALLERY.getHangingSignItem());
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.addAfter(Items.MANGROVE_CHEST_BOAT, PearBlocks.CALLERY.getBoatItem(), PearBlocks.CALLERY.getChestBoatItem());
        });
    }
}
