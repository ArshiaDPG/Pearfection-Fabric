package net.digitalpear.pearfection.common.datagens;

import net.digitalpear.pearfection.init.PearBlocks;
import net.digitalpear.pearfection.init.PearItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class PearfectionLanguageProvider extends FabricLanguageProvider {
    public PearfectionLanguageProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {

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
        translationBuilder.add(PearBlocks.CALLERY_STEM, "Callery Stem");
        translationBuilder.add(PearBlocks.STRIPPED_CALLERY_STEM, "Stripped Callery Stem");
        translationBuilder.add(PearBlocks.CALLERY_WOOD, "Callery Wood");
        translationBuilder.add(PearBlocks.STRIPPED_CALLERY_WOOD, "Stripped Callery Wood");


        translationBuilder.add(PearBlocks.CALLERY_PLANKS, "Callery Planks");
        translationBuilder.add(PearBlocks.CALLERY_SLAB, "Callery Slab");
        translationBuilder.add(PearBlocks.CALLERY_STAIRS, "Callery Stairs");

        translationBuilder.add(PearBlocks.CALLERY_FENCE, "Callery Fence");
        translationBuilder.add(PearBlocks.CALLERY_FENCE_GATE, "Callery Fence Gate");

        translationBuilder.add(PearBlocks.CALLERY_HANGING_SIGN, "Callery Hanging Sign");
        translationBuilder.add(PearBlocks.CALLERY_SIGN, "Callery Sign");

        translationBuilder.add(PearBlocks.CALLERY_LEAVES, "Callery Leaves");
        translationBuilder.add(PearBlocks.FLOWERING_CALLERY_LEAVES, "Flowering Callery Leaves");


        translationBuilder.add(PearBlocks.CALLERY_BUTTON, "Callery Button");
        translationBuilder.add(PearBlocks.CALLERY_DOOR, "Callery Door");
        translationBuilder.add(PearBlocks.CALLERY_TRAPDOOR, "Callery Trapdoor");
        translationBuilder.add(PearBlocks.CALLERY_PRESSURE_PLATE, "Callery Pressure Plate");

        translationBuilder.add(PearItems.CALLERY_BOAT, "Callery Boat");
        translationBuilder.add(PearItems.CALLERY_CHEST_BOAT, "Callery Boat with Chest");

        /*
            Misc
         */
        translationBuilder.add("subtitles.block.pear.step", "Footsteps");

        translationBuilder.add(PearItems.PEAR_TART, "Pear Tart");
    }
}
