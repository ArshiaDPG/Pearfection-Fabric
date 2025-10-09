package net.digitalpear.pearfection.init.data;

import net.digitalpear.pearfection.init.PearBlocks;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.ItemEntry;

public class PearData {
    public static void registerFlammableBlock(){
        FlammableBlockRegistry instance = FlammableBlockRegistry.getDefaultInstance();
        instance.add(PearBlocks.LAMPEAR, 30, 40);
        instance.add(PearBlocks.LAMPEAR_BASE_BLOCK, 7, 7);
        instance.add(PearBlocks.CALLERY_TWIG, 30, 40);
        instance.add(PearBlocks.CALLERY_SPROUT, 30, 40);
        instance.add(PearBlocks.CALLERY_VINE, 30, 40);
        instance.add(PearBlocks.FLOWERING_CALLERY_LEAVES, 35, 55);
    }

    public static void registerStrippables(){
    }

    public static void registerFuels(){
        FuelRegistryEvents.BUILD.register((builder, context) -> {
            builder.add(PearBlocks.LAMPEAR_BASE_BLOCK, 2000);
            builder.add(PearBlocks.CALLERY_VINE, 200);
            builder.add(PearBlocks.CALLERY_SPROUT, 150);
            builder.add(PearBlocks.CALLERY_TWIG, 100);
        });
    }

    public static void registerComposting(){
        CompostingChanceRegistry registry = CompostingChanceRegistry.INSTANCE;
        registry.add(PearBlocks.CALLERY.getLeaves(), 0.3F);
        registry.add(PearBlocks.FLOWERING_CALLERY_LEAVES, 0.3F);

        registry.add(PearBlocks.CALLERY_TWIG, 0.3F);
        registry.add(PearBlocks.CALLERY_SPROUT, 0.4F);
        registry.add(PearBlocks.CALLERY_VINE, 0.7F);

        registry.add(PearBlocks.LAMPEAR_BASE_BLOCK, 0.85F);

        registry.add(PearBlocks.LAMPEAR, 0.65F);
        registry.add(PearBlocks.COPPER_LAMPEAR, 1F);
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
        LootTableEvents.MODIFY.register((registryKey, builder, lootTableSource, wrapperLookup) -> {
            if (lootTableSource.isBuiltin() && LootTables.SNIFFER_DIGGING_GAMEPLAY.equals(registryKey)) {
                builder.modifyPools(builder1 -> builder1.with(ItemEntry.builder(PearBlocks.CALLERY_TWIG)));
            }
        });
    }
}
