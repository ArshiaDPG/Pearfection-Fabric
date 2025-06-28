package net.digitalpear.pearfection.common.datagens.tags;

import net.digitalpear.pearfection.init.PearBlocks;
import net.digitalpear.pearfection.init.tags.PearItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class PearfectionItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public PearfectionItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture, new PearfectionBlockTagProvider(output, registriesFuture));
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        valueLookupBuilder(ItemTags.PLANKS).add(PearBlocks.CALLERY.getPlanks().asItem());
        valueLookupBuilder(ItemTags.WOODEN_FENCES).add(PearBlocks.CALLERY.getFence().asItem());
        valueLookupBuilder(ItemTags.FENCE_GATES).add(PearBlocks.CALLERY.getFenceGate().asItem());
        valueLookupBuilder(ItemTags.WOODEN_DOORS).add(PearBlocks.CALLERY.getDoor().asItem());
        valueLookupBuilder(ItemTags.WOODEN_TRAPDOORS).add(PearBlocks.CALLERY.getTrapDoor().asItem());
        valueLookupBuilder(ItemTags.WOODEN_PRESSURE_PLATES).add(PearBlocks.CALLERY.getPressurePlate().asItem());
        valueLookupBuilder(ItemTags.WOODEN_BUTTONS).add(PearBlocks.CALLERY.getButton().asItem());
        valueLookupBuilder(ItemTags.WOODEN_SLABS).add(PearBlocks.CALLERY.getSlab().asItem());
        valueLookupBuilder(ItemTags.WOODEN_STAIRS).add(PearBlocks.CALLERY.getStairs().asItem());
        valueLookupBuilder(ItemTags.LOGS_THAT_BURN).forceAddTag(PearItemTags.CALLERY_STEMS);
        valueLookupBuilder(ItemTags.LEAVES).add(PearBlocks.CALLERY.getLeaves().asItem()).add(PearBlocks.FLOWERING_CALLERY_LEAVES.asItem());
        valueLookupBuilder(ItemTags.SIGNS).add(PearBlocks.CALLERY.getSignItem().asItem());
        valueLookupBuilder(ItemTags.HANGING_SIGNS).add(PearBlocks.CALLERY.getHangingSignItem().asItem());
        valueLookupBuilder(ItemTags.SAPLINGS).add(PearBlocks.CALLERY_TWIG.asItem());


        valueLookupBuilder(PearItemTags.CALLERY_STEMS)
                .add(PearBlocks.CALLERY.getLog().asItem()).add(PearBlocks.CALLERY.getStrippedLog().asItem())
                .add(PearBlocks.CALLERY.getWood().asItem()).add(PearBlocks.CALLERY.getStrippedWood().asItem());

        valueLookupBuilder(ConventionalItemTags.STRIPPED_LOGS).add(PearBlocks.CALLERY.getStrippedLog().asItem());
        valueLookupBuilder(ConventionalItemTags.STRIPPED_WOODS).add(PearBlocks.CALLERY.getStrippedWood().asItem());

        valueLookupBuilder(ConventionalItemTags.FRUIT_FOODS).add(PearBlocks.LAMPEAR.asItem()).add(PearBlocks.COPPER_LAMPEAR.asItem());

        valueLookupBuilder(PearItemTags.EATABLE_ON_DISH).add(PearBlocks.LAMPEAR.asItem()).add(PearBlocks.COPPER_LAMPEAR.asItem());
    }
}
