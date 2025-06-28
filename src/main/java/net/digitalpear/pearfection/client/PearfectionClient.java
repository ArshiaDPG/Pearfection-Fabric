package net.digitalpear.pearfection.client;

import net.digitalpear.pearfection.init.PearBlocks;
import net.digitalpear.pearfection.init.PearParticleTypes;
import net.digitalpear.pearfection.init.data.Woodset;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.particle.LeavesParticle;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.render.entity.RaftEntityRenderer;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.RaftEntityModel;
import net.minecraft.util.Identifier;

import java.util.Objects;

@Environment(EnvType.CLIENT)
public class PearfectionClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.putBlocks(BlockRenderLayer.CUTOUT,
                PearBlocks.LAMPEAR, PearBlocks.COPPER_LAMPEAR,
                PearBlocks.CALLERY_TWIG, PearBlocks.CALLERY_SPROUT, PearBlocks.CALLERY_VINE,
                PearBlocks.POTTED_CALLERY_TWIG, PearBlocks.POTTED_CALLERY_SPROUT);

        registerBoatModels(PearBlocks.CALLERY);

        ParticleFactoryRegistry.getInstance().register(PearParticleTypes.CALLERY_FLOWER, LeavesParticle.CherryLeavesFactory::new);
        ParticleFactoryRegistry.getInstance().register(PearParticleTypes.CALLERY_LEAF, LeavesParticle.CherryLeavesFactory::new);
    }
    public static void registerBoatModels(Woodset woodset){
        if (!woodset.getWoodsetSettings().hasBoats()){
            return;
        }
        Identifier layerName = woodset.getNameID().withPrefixedPath("boat/");
        Identifier chestLayerName = woodset.getNameID().withPrefixedPath("chest_boat/");

        final EntityModelLayer BOAT_MODEL_LAYER = new EntityModelLayer(layerName, "main");
        final EntityModelLayer CHEST_BOAT_MODEL_LAYER = new EntityModelLayer(chestLayerName, "main");

        final boolean raft = Objects.equals(woodset.getWoodsetSettings().getBoatType(), Woodset.Settings.BoatType.RAFT);

        EntityModelLayerRegistry.registerModelLayer(BOAT_MODEL_LAYER, raft ? RaftEntityModel::getTexturedModelData : BoatEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(woodset.getBoat(), ctx -> raft ? new RaftEntityRenderer(ctx, BOAT_MODEL_LAYER) : new BoatEntityRenderer(ctx, BOAT_MODEL_LAYER));

        EntityModelLayerRegistry.registerModelLayer(CHEST_BOAT_MODEL_LAYER, raft ? RaftEntityModel::getChestTexturedModelData : BoatEntityModel::getChestTexturedModelData);
        EntityRendererRegistry.register(woodset.getChestBoat(), ctx -> raft ? new RaftEntityRenderer(ctx, CHEST_BOAT_MODEL_LAYER) : new BoatEntityRenderer(ctx, CHEST_BOAT_MODEL_LAYER));
    }
}
