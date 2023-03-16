package net.digitalpear.pearfection.init.data;

import net.digitalpear.pearfection.init.PearBlocks;
import net.digitalpear.snifferiety.mapcollection.SnifferSeedRegistry;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;

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

        registry.add(PearBlocks.LAMPEAR_BLOCK, 10 * 200);
    }
    public static void registerComposting(){
        CompostingChanceRegistry.INSTANCE.add(PearBlocks.CALLERY_LEAVES, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(PearBlocks.FLOWERING_CALLERY_LEAVES, 0.3F);

        CompostingChanceRegistry.INSTANCE.add(PearBlocks.CALLERY_TWIG, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(PearBlocks.CALLERY_SPROUT, 0.3F);
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


        SnifferSeedRegistry.register(PearBlocks.CALLERY_TWIG.asItem(), 10);
    }
}
