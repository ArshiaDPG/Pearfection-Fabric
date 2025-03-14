package net.digitalpear.pearfection.common.datagens.tags;

import net.digitalpear.pearfection.init.PearBlocks;
import net.digitalpear.pearfection.init.tags.PearItemTags;
import net.digitalpear.pearfection.init.PearItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.hecco.bountifulfares.datagen.bountifulfares.BFBlockTagProvider;
import net.hecco.bountifulfares.registry.tags.BFBlockTags;
import net.hecco.bountifulfares.registry.tags.BFItemTags;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class PearfectionItemTagProvider extends FabricTagProvider.ItemTagProvider {


    public PearfectionItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture, new BFBlockTagProvider(output, completableFuture));
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {

        getOrCreateTagBuilder(ItemTags.PLANKS).add(PearBlocks.CALLERY_PLANKS.asItem());
        getOrCreateTagBuilder(ItemTags.WOODEN_FENCES).add(PearBlocks.CALLERY_FENCE.asItem());
        getOrCreateTagBuilder(ItemTags.FENCE_GATES).add(PearBlocks.CALLERY_FENCE_GATE.asItem());
        getOrCreateTagBuilder(ItemTags.WOODEN_DOORS).add(PearBlocks.CALLERY_DOOR.asItem());
        getOrCreateTagBuilder(ItemTags.WOODEN_TRAPDOORS).add(PearBlocks.CALLERY_TRAPDOOR.asItem());
        getOrCreateTagBuilder(ItemTags.WOODEN_PRESSURE_PLATES).add(PearBlocks.CALLERY_PRESSURE_PLATE.asItem());
        getOrCreateTagBuilder(ItemTags.WOODEN_BUTTONS).add(PearBlocks.CALLERY_BUTTON.asItem());
        getOrCreateTagBuilder(ItemTags.WOODEN_SLABS).add(PearBlocks.CALLERY_SLAB.asItem());
        getOrCreateTagBuilder(ItemTags.WOODEN_STAIRS).add(PearBlocks.CALLERY_STAIRS.asItem());
        getOrCreateTagBuilder(ItemTags.LOGS_THAT_BURN).forceAddTag(PearItemTags.CALLERY_STEMS);
        getOrCreateTagBuilder(ItemTags.LEAVES).add(PearBlocks.CALLERY_LEAVES.asItem()).add(PearBlocks.FLOWERING_CALLERY_LEAVES.asItem());
        getOrCreateTagBuilder(ItemTags.SIGNS).add(PearItems.CALLERY_SIGN.asItem());
        getOrCreateTagBuilder(ItemTags.HANGING_SIGNS).add(PearItems.CALLERY_HANGING_SIGN.asItem());
        getOrCreateTagBuilder(ItemTags.SAPLINGS).add(PearBlocks.CALLERY_TWIG.asItem());


        getOrCreateTagBuilder(PearItemTags.CALLERY_STEMS)
                .add(PearBlocks.CALLERY_STEM.asItem()).add(PearBlocks.STRIPPED_CALLERY_STEM.asItem())
                .add(PearBlocks.CALLERY_WOOD.asItem()).add(PearBlocks.STRIPPED_CALLERY_WOOD.asItem());


        getOrCreateTagBuilder(BFItemTags.PICKETS).addOptional(Registries.ITEM.getId(PearBlocks.CALLERY_PICKETS.asItem()));

        getOrCreateTagBuilder(PearItemTags.C_FRUITS_PEARS).add(PearBlocks.LAMPEAR.asItem()).add(PearBlocks.COPPER_LAMPEAR.asItem());
        getOrCreateTagBuilder(PearItemTags.EATABLE_ON_DISH).add(PearBlocks.LAMPEAR.asItem()).add(PearBlocks.COPPER_LAMPEAR.asItem());
    }
}
