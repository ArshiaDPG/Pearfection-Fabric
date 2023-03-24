package net.digitalpear.pearfection;

import net.digitalpear.pearfection.common.datagens.*;
import net.digitalpear.pearfection.common.datagens.tags.PearfectionBiomeTagGen;
import net.digitalpear.pearfection.common.datagens.tags.PearfectionBlockTagGen;
import net.digitalpear.pearfection.common.datagens.tags.PearfectionItemTagGen;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class PearfectionDatagens implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {

        fabricDataGenerator.createPack().addProvider(PearfectionModelGen::new);
        fabricDataGenerator.createPack().addProvider(PearfectionLanguageGen::new);
        fabricDataGenerator.createPack().addProvider(PearfectionBlockLootTableGen::new);
        fabricDataGenerator.createPack().addProvider(PearRecipeGen::new);

        fabricDataGenerator.createPack().addProvider(PearfectionBlockTagGen::new);
        fabricDataGenerator.createPack().addProvider(PearfectionItemTagGen::new);
        fabricDataGenerator.createPack().addProvider(PearfectionBiomeTagGen::new);
    }
}
