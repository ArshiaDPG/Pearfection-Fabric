package net.digitalpear.pearfection.common.datagens;

import net.digitalpear.pearfection.Pearfection;
import net.digitalpear.pearfection.common.datagens.misc.PearBlockLootTables;
import net.digitalpear.pearfection.init.PearBlocks;
import net.digitalpear.pearfection.init.PearItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.Registries;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.util.Identifier;

import java.util.Set;
import java.util.function.BiConsumer;

public class PearfectionBlockLootTableGen extends SimpleFabricLootTableProvider {
    public PearfectionBlockLootTableGen(FabricDataOutput output) {
        super(output, LootContextTypes.BLOCK);
    }

    public static final BlockLootTableGenerator BLTG = new PearBlockLootTables(Set.of(Items.NETHER_STAR), FeatureFlags.VANILLA_FEATURES);

    @Override
    public void accept(BiConsumer<Identifier, LootTable.Builder> biConsumer) {

        simpleDrop(biConsumer, PearBlocks.LAMPEAR);
        simpleDrop(biConsumer, PearBlocks.COPPER_LAMPEAR);

        simpleDrop(biConsumer, PearBlocks.LAMPEAR_BLOCK);

        biConsumer.accept(getPearfectionNamespacedBlock(PearBlocks.CALLERY_VINE), BLTG.doorDrops(PearBlocks.CALLERY_VINE));
        simpleDrop(biConsumer, PearBlocks.CALLERY_SPROUT);
        biConsumer.accept(getPearfectionNamespacedBlock(PearBlocks.POTTED_CALLERY_SPROUT), BLTG.pottedPlantDrops(PearBlocks.CALLERY_SPROUT));
        simpleDrop(biConsumer, PearBlocks.CALLERY_TWIG);
        biConsumer.accept(getPearfectionNamespacedBlock(PearBlocks.POTTED_CALLERY_TWIG), BLTG.pottedPlantDrops(PearBlocks.CALLERY_TWIG));



        simpleDrop(biConsumer, PearBlocks.CALLERY_STEM);
        simpleDrop(biConsumer, PearBlocks.STRIPPED_CALLERY_STEM);
        simpleDrop(biConsumer, PearBlocks.CALLERY_WOOD);
        simpleDrop(biConsumer, PearBlocks.STRIPPED_CALLERY_WOOD);

        simpleDrop(biConsumer, PearBlocks.CALLERY_PLANKS);
        simpleDrop(biConsumer, PearBlocks.CALLERY_STAIRS);
        makeSlab(biConsumer, PearBlocks.CALLERY_SLAB);

        simpleDrop(biConsumer, PearBlocks.CALLERY_PRESSURE_PLATE);
        simpleDrop(biConsumer, PearBlocks.CALLERY_BUTTON);

        simpleDrop(biConsumer, PearBlocks.CALLERY_FENCE);
        simpleDrop(biConsumer, PearBlocks.CALLERY_FENCE_GATE);

        makeDoor(biConsumer, PearBlocks.CALLERY_DOOR);
        simpleDrop(biConsumer, PearBlocks.CALLERY_TRAPDOOR);

        biConsumer.accept(getPearfectionNamespacedBlock(PearBlocks.CALLERY_LEAVES), BLTG.leavesDrops(PearBlocks.CALLERY_LEAVES, PearBlocks.CALLERY_TWIG, 0.05f, 0.0625f, 0.025f, 0.083333336f, 0.1f));
        biConsumer.accept(getPearfectionNamespacedBlock(PearBlocks.FLOWERING_CALLERY_LEAVES), BLTG.leavesDrops(PearBlocks.FLOWERING_CALLERY_LEAVES, PearBlocks.CALLERY_SPROUT, 0.05f, 0.0625f, 0.025f, 0.083333336f, 0.1f));

        simpleDrop(biConsumer, PearBlocks.CALLERY_SIGN, PearItems.CALLERY_SIGN);
        simpleDrop(biConsumer, PearBlocks.CALLERY_HANGING_SIGN, PearItems.CALLERY_HANGING_SIGN);

        /*
            Compat
         */
        simpleDrop(biConsumer, PearBlocks.HOLLOWED_CALLERY_STEM);
//        biConsumer.accept(getPearfectionNamespacedBlock(PearBlocks.CALLERY_BOOKSHELF), bookshelfDrop(PearBlocks.CALLERY_BOOKSHELF));

    }

    public net.minecraft.loot.LootTable.Builder bookshelfDrop(Block drop) {
        return BlockLootTableGenerator.dropsWithSilkTouch(drop, BLTG.applyExplosionDecay(drop,
                ItemEntry.builder(Items.BOOK).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(3.0f), false))));
    }
    public static void makeSlab(BiConsumer<Identifier, LootTable.Builder> biConsumer, Block slab){
        biConsumer.accept(getPearfectionNamespacedBlock(slab), BLTG.slabDrops(slab));
    }
    public static void makeDoor(BiConsumer<Identifier, LootTable.Builder> biConsumer, Block door){
        biConsumer.accept(getPearfectionNamespacedBlock(door), BLTG.doorDrops(door));
    }


    public static Identifier getPearfectionNamespacedBlock(Block name) {
        return new Identifier(Pearfection.MOD_ID, "blocks/" + Registries.BLOCK.getId(name).getPath());
    }

    public static void simpleDrop(BiConsumer<Identifier, LootTable.Builder> biConsumer, Block block){
        biConsumer.accept(getPearfectionNamespacedBlock(block), drops(block.asItem(), BLTG));
    }
    public static void simpleDrop(BiConsumer<Identifier, LootTable.Builder> biConsumer, Block block, Item drop){
        biConsumer.accept(getPearfectionNamespacedBlock(block), drops(drop, BLTG));
    }

    public static net.minecraft.loot.LootTable.Builder drops(Item drop, BlockLootTableGenerator blockLootTableGenerator) {
        return LootTable.builder().pool(blockLootTableGenerator.addSurvivesExplosionCondition(drop, LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).with(ItemEntry.builder(drop))));
    }
}
