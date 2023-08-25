package net.digitalpear.pearfection.common.datagens;

import net.digitalpear.pearfection.init.PearBlocks;
import net.digitalpear.pearfection.init.PearItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.FlowerPotBlock;

public class PearfectionBlockLootTableProvider extends FabricBlockLootTableProvider {
    public PearfectionBlockLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(PearBlocks.CALLERY_STEM);
        addDrop(PearBlocks.CALLERY_WOOD);
        addDrop(PearBlocks.STRIPPED_CALLERY_STEM);
        addDrop(PearBlocks.STRIPPED_CALLERY_WOOD);

        addDrop(PearBlocks.CALLERY_PLANKS);
        addDrop(PearBlocks.CALLERY_STAIRS);
        addDrop(PearBlocks.CALLERY_SLAB, slabDrops(PearBlocks.CALLERY_SLAB));
        addDrop(PearBlocks.CALLERY_FENCE_GATE);
        addDrop(PearBlocks.CALLERY_FENCE);


        addDrop(PearBlocks.LAMPEAR_BLOCK);
        addDrop(PearBlocks.LAMPEAR);
        addDrop(PearBlocks.COPPER_LAMPEAR);

        addDrop(PearBlocks.CALLERY_SIGN, PearItems.CALLERY_SIGN);
        addDrop(PearBlocks.CALLERY_WALL_SIGN, PearItems.CALLERY_SIGN);

        addDrop(PearBlocks.CALLERY_HANGING_SIGN, PearItems.CALLERY_HANGING_SIGN);
        addDrop(PearBlocks.CALLERY_WALL_HANGING_SIGN, PearItems.CALLERY_HANGING_SIGN);


        makePottedLootTable((FlowerPotBlock) PearBlocks.POTTED_CALLERY_TWIG);
        makePottedLootTable((FlowerPotBlock) PearBlocks.POTTED_CALLERY_SPROUT);
        addDrop(PearBlocks.CALLERY_VINE, doorDrops(PearBlocks.CALLERY_VINE));


        addDrop(PearBlocks.CALLERY_LEAVES, leavesDrops(PearBlocks.CALLERY_LEAVES, PearBlocks.CALLERY_TWIG, 0.05f, 0.0625f, 0.025f, 0.083333336f, 0.1f));
        addDrop(PearBlocks.FLOWERING_CALLERY_LEAVES, leavesDrops(PearBlocks.CALLERY_LEAVES, PearBlocks.CALLERY_TWIG, 0.05f, 0.0625f, 0.025f, 0.083333336f, 0.1f));
    }

    public void makePottedLootTable(FlowerPotBlock flowerPotBlock){
        addPottedPlantDrops(flowerPotBlock);
        addDrop(flowerPotBlock.getContent());
    }


}
