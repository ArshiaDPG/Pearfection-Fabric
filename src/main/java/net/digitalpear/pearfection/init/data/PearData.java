package net.digitalpear.pearfection.init.data;

import net.digitalpear.pearfection.init.PearBlocks;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;

import java.util.HashMap;

public class PearData {
    public static void registerFlammableBlock(){
        FlammableBlockRegistry instance = FlammableBlockRegistry.getDefaultInstance();
        instance.add(PearBlocks.LAMPEAR, 30, 40);
        instance.add(PearBlocks.LAMPEAR_BLOCK, 7, 7);
        instance.add(PearBlocks.CALLERY_STEM, 5, 5);
        instance.add(PearBlocks.STRIPPED_CALLERY_STEM, 5, 5);
        instance.add(PearBlocks.CALLERY_WOOD, 5, 5);
        instance.add(PearBlocks.STRIPPED_CALLERY_WOOD, 5, 5);
        instance.add(PearBlocks.CALLERY_PLANKS, 5, 15);
        instance.add(PearBlocks.CALLERY_STAIRS, 5, 20);
        instance.add(PearBlocks.CALLERY_SLAB, 5, 20);
        instance.add(PearBlocks.CALLERY_FENCE, 5, 20);
        instance.add(PearBlocks.CALLERY_FENCE_GATE, 5, 20);
        instance.add(PearBlocks.CALLERY_LEAVES, 30, 60);
        instance.add(PearBlocks.FLOWERING_CALLERY_LEAVES, 35, 55);
    }

    public static void registerStrippables(){
        StrippableBlockRegistry.register(PearBlocks.CALLERY_STEM, PearBlocks.STRIPPED_CALLERY_STEM);
        StrippableBlockRegistry.register(PearBlocks.CALLERY_WOOD, PearBlocks.STRIPPED_CALLERY_WOOD);
    }

    public static void registerFuels(){
        FuelRegistry registry = FuelRegistry.INSTANCE;

        registry.add(PearBlocks.LAMPEAR_BLOCK, 2000);
        registry.add(PearBlocks.CALLERY_VINE, 200);
        registry.add(PearBlocks.CALLERY_SPROUT, 150);
        registry.add(PearBlocks.CALLERY_TWIG, 100);
    }

    public static void registerComposting(){
        CompostingChanceRegistry.INSTANCE.add(PearBlocks.CALLERY_LEAVES, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(PearBlocks.FLOWERING_CALLERY_LEAVES, 0.3F);

        CompostingChanceRegistry.INSTANCE.add(PearBlocks.CALLERY_TWIG, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(PearBlocks.CALLERY_SPROUT, 0.4F);
        CompostingChanceRegistry.INSTANCE.add(PearBlocks.CALLERY_VINE, 0.7F);

        CompostingChanceRegistry.INSTANCE.add(PearBlocks.LAMPEAR_BLOCK, 0.85F);

        CompostingChanceRegistry.INSTANCE.add(PearBlocks.LAMPEAR, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(PearBlocks.COPPER_LAMPEAR, 0.65F);
    }



    public static void init(){
        registerStrippables();
        registerFlammableBlock();
        registerFuels();
        registerComposting();


        /*
            Adds the callery twig to the sniffers loot table (Adds to the existing pool instead of creating a new pool).
            I'd recommend using this method if you want to add new drops quickly.
         */
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (source.isBuiltin() && LootTables.SNIFFER_DIGGING_GAMEPLAY.equals(id)) {
                tableBuilder.modifyPools(builder -> builder.with(ItemEntry.builder(PearBlocks.CALLERY_TWIG)));
            }
        });
    }
}
