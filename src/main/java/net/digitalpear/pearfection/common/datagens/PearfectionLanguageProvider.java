package net.digitalpear.pearfection.common.datagens;

import net.digitalpear.pearfection.init.PearBlocks;
import net.digitalpear.pearfection.init.PearItems;
import net.digitalpear.pearfection.init.tags.PearBlockTags;
import net.digitalpear.pearfection.init.tags.PearItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class PearfectionLanguageProvider extends FabricLanguageProvider {

    public PearfectionLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        /*
            Lampears
         */
        translationBuilder.add(PearBlocks.LAMPEAR, "Lampear");
        translationBuilder.add(PearBlocks.COPPER_LAMPEAR, "Copper Lampear");

        translationBuilder.add(PearBlocks.LAMPEAR_BLOCK, "Lampear Block");

        /*
            Plants
         */
        translationBuilder.add(PearBlocks.CALLERY_VINE, "Callery Vine");

        translationBuilder.add(PearBlocks.CALLERY_SPROUT, "Callery Sprout");
        translationBuilder.add(PearBlocks.POTTED_CALLERY_SPROUT, "Potted Callery Sprout");

        translationBuilder.add(PearBlocks.CALLERY_TWIG, "Callery Twig");
        translationBuilder.add(PearBlocks.POTTED_CALLERY_TWIG, "Potted Callery Twig");



        /*
            Callery Woodset
         */
        translationBuilder.add(PearBlocks.CALLERY.getLog(), "Callery Stem");
        translationBuilder.add(PearBlocks.CALLERY.getStrippedLog(), "Stripped Callery Stem");
        translationBuilder.add(PearBlocks.CALLERY.getWood(), "Callery Wood");
        translationBuilder.add(PearBlocks.CALLERY.getStrippedWood(), "Stripped Callery Wood");


        translationBuilder.add(PearBlocks.CALLERY.getPlanks(), "Callery Planks");
        translationBuilder.add(PearBlocks.CALLERY.getSlab(), "Callery Slab");
        translationBuilder.add(PearBlocks.CALLERY.getStairs(), "Callery Stairs");

        translationBuilder.add(PearBlocks.CALLERY.getFence(), "Callery Fence");
        translationBuilder.add(PearBlocks.CALLERY.getFenceGate(), "Callery Fence Gate");

        translationBuilder.add(PearBlocks.CALLERY.getHangingSign(), "Callery Hanging Sign");
        translationBuilder.add(PearBlocks.CALLERY.getSign(), "Callery Sign");

        translationBuilder.add(PearBlocks.CALLERY.getLeaves(), "Callery Leaves");
        translationBuilder.add(PearBlocks.FLOWERING_CALLERY_LEAVES, "Flowering Callery Leaves");

        translationBuilder.add(PearBlocks.CALLERY.getSignItem(), "Callery Sign");
        translationBuilder.add(PearBlocks.CALLERY.getHangingSignItem(), "Callery Hanging Sign");

        translationBuilder.add(PearBlocks.CALLERY.getButton(), "Callery Button");
        translationBuilder.add(PearBlocks.CALLERY.getDoor(), "Callery Door");
        translationBuilder.add(PearBlocks.CALLERY.getTrapDoor(), "Callery Trapdoor");
        translationBuilder.add(PearBlocks.CALLERY.getPressurePlate(), "Callery Pressure Plate");

        translationBuilder.add(PearBlocks.CALLERY.getBoatItem(), "Callery Boat");
        translationBuilder.add(PearBlocks.CALLERY.getChestBoatItem(), "Callery Boat with Chest");

//        translationBuilder.add(PearBlocks.CALLERY_PICKETS, "Callery Pickets");
        translationBuilder.add(PearBlocks.CALLERY.getBoat(), "Callery Boat");
        translationBuilder.add(PearBlocks.CALLERY.getChestBoat(), "Callery Boat with Chest");

        /*
            Misc
         */
        translationBuilder.add(PearItemTags.CALLERY_STEMS, "Callery Stems");
        translationBuilder.add(PearBlockTags.CALLERY_STEMS, "Callery Stems");
        translationBuilder.add(PearBlockTags.HUGE_PEAR_CANNOT_REPLACE, "Huge Pears Cannot Replace");
        translationBuilder.add(PearBlockTags.ENORMOUS_PEAR_GROWABLE_ON, "Enormous Pear Growable On");

        translationBuilder.add("subtitles.block.pear.step", "Footsteps");

        translationBuilder.add(PearItems.PEAR_TART, "Pear Tart");
    }
}
