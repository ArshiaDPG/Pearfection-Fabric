package net.digitalpear.pearfection.common.datagens;

import net.digitalpear.pearfection.init.PearBlocks;
import net.digitalpear.pearfection.init.data.Woodset;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class PearfectionBlockLootTableProvider extends FabricBlockLootTableProvider {

    public PearfectionBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        makeWoodLoot(PearBlocks.CALLERY, PearBlocks.CALLERY_TWIG);

        addDrop(PearBlocks.LAMPEAR_BLOCK);
        addDrop(PearBlocks.LAMPEAR);
        addDrop(PearBlocks.COPPER_LAMPEAR);

        makePottedLootTable((FlowerPotBlock) PearBlocks.POTTED_CALLERY_TWIG);
        makePottedLootTable((FlowerPotBlock) PearBlocks.POTTED_CALLERY_SPROUT);
        addDrop(PearBlocks.CALLERY_VINE, doorDrops(PearBlocks.CALLERY_VINE));


        addDrop(PearBlocks.FLOWERING_CALLERY_LEAVES, leavesDrops(PearBlocks.FLOWERING_CALLERY_LEAVES, PearBlocks.CALLERY_TWIG, 0.05f, 0.0625f, 0.025f, 0.083333336f, 0.1f));
    }
    public void makeWoodLoot(Woodset woodset, Block sapling){
        addDrop(woodset.getPlanks());
        addDrop(woodset.getStairs());
        addDrop(woodset.getSlab(), slabDrops(woodset.getSlab()));
        addDrop(woodset.getFence());
        addDrop(woodset.getFenceGate());
        addDrop(woodset.getButton());
        addDrop(woodset.getPressurePlate());
        addDrop(woodset.getLog());

        if (woodset.getWoodPreset() == Woodset.WoodPreset.BAMBOO) {
            addDrop(woodset.getMosaic());
            addDrop(woodset.getMosaicStairs());
            addDrop(woodset.getMosaicSlab(), slabDrops(woodset.getMosaic()));
        }
        else{
            addDrop(woodset.getWood());
            addDrop(woodset.getStrippedLog());
            addDrop(woodset.getStrippedWood());
        }

        addDrop(woodset.getTrapDoor());
        addDrop(woodset.getDoor(), doorDrops(woodset.getDoor()));
        if (woodset.isOverworldTreeWood()){
            addDrop(woodset.getLeaves(), leavesDrops(woodset.getLeaves(), sapling, 0.05f, 0.0625f, 0.025f, 0.083333336f, 0.1f));
        }
        addDrop(woodset.getSign());
        addDrop(woodset.getHangingSign());
    }
    public void makePottedLootTable(FlowerPotBlock flowerPotBlock){
        addPottedPlantDrops(flowerPotBlock);
        addDrop(flowerPotBlock.getContent());
    }
}
