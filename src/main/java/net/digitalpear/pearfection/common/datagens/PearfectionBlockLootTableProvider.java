package net.digitalpear.pearfection.common.datagens;

import net.digitalpear.pearfection.common.blocks.compat.PicketsBlock;
import net.digitalpear.pearfection.init.PearBlocks;
import net.digitalpear.pearfection.init.PearItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.loot.v2.FabricLootTableBuilder;
import net.hecco.bountifulfares.datagen.bountifulfares.BFBlockLootTableProvider;
import net.hecco.bountifulfares.registry.util.BFLootTableModifiers;
import net.minecraft.block.Block;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class PearfectionBlockLootTableProvider extends FabricBlockLootTableProvider {


    public PearfectionBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
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
        addDrop(PearBlocks.FLOWERING_CALLERY_LEAVES, leavesDrops(PearBlocks.FLOWERING_CALLERY_LEAVES, PearBlocks.CALLERY_TWIG, 0.05f, 0.0625f, 0.025f, 0.083333336f, 0.1f));

        picketsDrops(PearBlocks.CALLERY_PICKETS);
    }
    public void picketsDrops(Block block) {
        this.addDrop(block, LootTable.builder()
                .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).conditionally(BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(net.hecco.bountifulfares.block.custom.PicketsBlock.NORTH, true))).with(this.applyExplosionDecay(block, ItemEntry.builder(block))))
                .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).conditionally(BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(net.hecco.bountifulfares.block.custom.PicketsBlock.EAST, true))).with(this.applyExplosionDecay(block, ItemEntry.builder(block))))
                .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).conditionally(BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(net.hecco.bountifulfares.block.custom.PicketsBlock.SOUTH, true))).with(this.applyExplosionDecay(block, ItemEntry.builder(block))))
                .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).conditionally(BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(net.hecco.bountifulfares.block.custom.PicketsBlock.WEST, true))).with(this.applyExplosionDecay(block, ItemEntry.builder(block)))));
    }
    public void makePottedLootTable(FlowerPotBlock flowerPotBlock){
        addPottedPlantDrops(flowerPotBlock);
        addDrop(flowerPotBlock.getContent());
    }


}
