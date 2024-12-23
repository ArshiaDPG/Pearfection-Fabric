package net.digitalpear.pearfection.common.datagens.tags;

import net.digitalpear.pearfection.init.PearBlocks;
import net.digitalpear.pearfection.init.tags.PearItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class PearfectionItemTagProvider extends FabricTagProvider<Item> {
    public PearfectionItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, Registries.ITEM.getKey(), registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {

        getOrCreateTagBuilder(ItemTags.PLANKS).add(PearBlocks.CALLERY.getPlanks().asItem());
        getOrCreateTagBuilder(ItemTags.WOODEN_FENCES).add(PearBlocks.CALLERY.getFence().asItem());
        getOrCreateTagBuilder(ItemTags.FENCE_GATES).add(PearBlocks.CALLERY.getFenceGate().asItem());
        getOrCreateTagBuilder(ItemTags.WOODEN_DOORS).add(PearBlocks.CALLERY.getDoor().asItem());
        getOrCreateTagBuilder(ItemTags.WOODEN_TRAPDOORS).add(PearBlocks.CALLERY.getTrapDoor().asItem());
        getOrCreateTagBuilder(ItemTags.WOODEN_PRESSURE_PLATES).add(PearBlocks.CALLERY.getPressurePlate().asItem());
        getOrCreateTagBuilder(ItemTags.WOODEN_BUTTONS).add(PearBlocks.CALLERY.getButton().asItem());
        getOrCreateTagBuilder(ItemTags.WOODEN_SLABS).add(PearBlocks.CALLERY.getSlab().asItem());
        getOrCreateTagBuilder(ItemTags.WOODEN_STAIRS).add(PearBlocks.CALLERY.getStairs().asItem());
        getOrCreateTagBuilder(ItemTags.LOGS_THAT_BURN).forceAddTag(PearItemTags.CALLERY_STEMS);
        getOrCreateTagBuilder(ItemTags.LEAVES).add(PearBlocks.CALLERY.getLeaves().asItem()).add(PearBlocks.FLOWERING_CALLERY_LEAVES.asItem());
        getOrCreateTagBuilder(ItemTags.SIGNS).add(PearBlocks.CALLERY.getSignItem().asItem());
        getOrCreateTagBuilder(ItemTags.HANGING_SIGNS).add(PearBlocks.CALLERY.getHangingSignItem().asItem());
        getOrCreateTagBuilder(ItemTags.SAPLINGS).add(PearBlocks.CALLERY_TWIG.asItem());


        getOrCreateTagBuilder(PearItemTags.CALLERY_STEMS)
                .add(PearBlocks.CALLERY.getLog().asItem()).add(PearBlocks.CALLERY.getStrippedLog().asItem())
                .add(PearBlocks.CALLERY.getWood().asItem()).add(PearBlocks.CALLERY.getStrippedWood().asItem());


        getOrCreateTagBuilder(PearItemTags.C_FRUITS_PEARS).add(PearBlocks.LAMPEAR.asItem()).add(PearBlocks.COPPER_LAMPEAR.asItem());
        getOrCreateTagBuilder(PearItemTags.EATABLE_ON_DISH).add(PearBlocks.LAMPEAR.asItem()).add(PearBlocks.COPPER_LAMPEAR.asItem());
    }
}
