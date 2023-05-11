package net.digitalpear.pearfection;

import net.digitalpear.pearfection.common.features.PearFeatures;
import net.digitalpear.pearfection.init.*;
import net.digitalpear.pearfection.init.data.PearData;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Pearfection implements ModInitializer {
    public static final String MOD_ID = "pearfection";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static Identifier id(String name){
        return new Identifier(MOD_ID, name);
    }

    @Override
    public void onInitialize() {
        PearBlocks.init();
        PearItems.init();
        PearConfiguredFeatures.init();
        PearFeatures.init();
        PearBoatTypes.init();
        PearData.init();

        LOGGER.info(MOD_ID + " has been initialized.");
    }
}
