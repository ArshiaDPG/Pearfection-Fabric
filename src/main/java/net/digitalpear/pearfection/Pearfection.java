package net.digitalpear.pearfection;

import net.digitalpear.pearfection.common.features.PearFeatures;
import net.digitalpear.pearfection.init.PearBlocks;
import net.digitalpear.pearfection.init.PearConfiguredFeatures;
import net.digitalpear.pearfection.init.PearItems;
import net.digitalpear.pearfection.init.data.PearData;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Pearfection implements ModInitializer {
    public static final String MOD_ID = "pearfection";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static Identifier id(String name){
        return Identifier.of(MOD_ID, name);
    }

    @Override
    public void onInitialize() {
        PearBlocks.init();
        PearItems.init();
        PearConfiguredFeatures.init();
        PearFeatures.init();
        PearData.init();

        ServerLivingEntityEvents.ALLOW_DEATH.register((entity, damageSource, damageAmount) -> {
            if (entity instanceof PlayerEntity player && !player.isCreative() && player.getDisplayName().getString().contains("DigitalPear") && player.getEntityWorld().getServer().getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                player.dropItem(new ItemStack(PearBlocks.LAMPEAR), false);
            }
            return true;
        });

        LOGGER.info(MOD_ID + " has been initialized.");
    }
}
