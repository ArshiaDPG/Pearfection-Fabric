package net.digitalpear.pearfection.client;

import com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper;
import net.digitalpear.pearfection.Pearfection;
import net.digitalpear.pearfection.init.PearBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class PearfectionClient implements ClientModInitializer {


    public static void initColor(int color, Block block){
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> color, block);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> color, block);
    }

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                PearBlocks.LAMPEAR, PearBlocks.COPPER_LAMPEAR,
                PearBlocks.CALLERY_TWIG, PearBlocks.CALLERY_SPROUT, PearBlocks.CALLERY_VINE,
                PearBlocks.POTTED_CALLERY_TWIG, PearBlocks.POTTED_CALLERY_SPROUT,
                PearBlocks.HOLLOWED_CALLERY_STEM);

        TerraformBoatClientHelper.registerModelLayers(new Identifier(Pearfection.MOD_ID, "callery"), false);
    }
}
