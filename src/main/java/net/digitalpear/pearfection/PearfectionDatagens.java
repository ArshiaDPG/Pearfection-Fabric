package net.digitalpear.pearfection;

import net.digitalpear.pearfection.common.datagens.*;
import net.digitalpear.pearfection.common.datagens.tags.PearfectionBiomeTagProvider;
import net.digitalpear.pearfection.common.datagens.tags.PearfectionBlockTagProvider;
import net.digitalpear.pearfection.common.datagens.tags.PearfectionItemTagProvider;
import net.digitalpear.pearfection.common.datagens.worldgen.PearConfiguredFeatureProvider;
import net.digitalpear.pearfection.init.PearConfiguredFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class PearfectionDatagens implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.createPack().addProvider(PearfectionModelProvider::new);
        fabricDataGenerator.createPack().addProvider(PearfectionLanguageProvider::new);
        fabricDataGenerator.createPack().addProvider(PearfectionBlockLootTableProvider::new);
        fabricDataGenerator.createPack().addProvider(PearRecipeProvider::new);

        fabricDataGenerator.createPack().addProvider(PearConfiguredFeatureProvider::new);

        fabricDataGenerator.createPack().addProvider(PearfectionBlockTagProvider::new);
        fabricDataGenerator.createPack().addProvider(PearfectionItemTagProvider::new);
        fabricDataGenerator.createPack().addProvider(PearfectionBiomeTagProvider::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, PearConfiguredFeatures::bootstrap);
    }
}
