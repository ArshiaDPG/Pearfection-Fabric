package net.digitalpear.pearfection.common.datagens.misc;

import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.item.Item;
import net.minecraft.resource.featuretoggle.FeatureSet;

import java.util.Set;

public class PearBlockLootTables extends BlockLootTableGenerator {
    public PearBlockLootTables(Set<Item> explosionImmuneItems, FeatureSet requiredFeatures) {
        super(explosionImmuneItems, requiredFeatures);
    }

    @Override
    public void generate() {
    }
}
