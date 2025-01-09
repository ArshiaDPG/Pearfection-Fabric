package net.digitalpear.pearfection.common.datagens.tags;

import net.digitalpear.pearfection.init.PearBlocks;
import net.digitalpear.pearfection.init.tags.PearBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class PearfectionBlockTagProvider extends FabricTagProvider<Block> {
    public PearfectionBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, Registries.BLOCK.getKey(), registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {

        /*
            VANILLA
         */
        getOrCreateTagBuilder(BlockTags.HOE_MINEABLE).add(PearBlocks.LAMPEAR).add(PearBlocks.LAMPEAR_BLOCK).add(PearBlocks.CALLERY.getLeaves());

        getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN).forceAddTag(PearBlockTags.CALLERY_STEMS);
        getOrCreateTagBuilder(BlockTags.PLANKS).add(PearBlocks.CALLERY.getPlanks());
        getOrCreateTagBuilder(BlockTags.WOODEN_STAIRS).add(PearBlocks.CALLERY.getStairs());
        getOrCreateTagBuilder(BlockTags.WOODEN_SLABS).add(PearBlocks.CALLERY.getSlab());
        getOrCreateTagBuilder(BlockTags.WOODEN_FENCES).add(PearBlocks.CALLERY.getFence());
        getOrCreateTagBuilder(BlockTags.FENCE_GATES).add(PearBlocks.CALLERY.getFenceGate());
        getOrCreateTagBuilder(BlockTags.WOODEN_DOORS).add(PearBlocks.CALLERY.getDoor());
        getOrCreateTagBuilder(BlockTags.WOODEN_TRAPDOORS).add(PearBlocks.CALLERY.getTrapDoor());
        getOrCreateTagBuilder(BlockTags.LEAVES).add(PearBlocks.CALLERY.getLeaves()).add(PearBlocks.FLOWERING_CALLERY_LEAVES);
        getOrCreateTagBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(PearBlocks.CALLERY.getPressurePlate());
        getOrCreateTagBuilder(BlockTags.BUTTONS).add(PearBlocks.CALLERY.getButton());
        getOrCreateTagBuilder(BlockTags.WALL_SIGNS).add(PearBlocks.CALLERY.getWallSign());
        getOrCreateTagBuilder(BlockTags.STANDING_SIGNS).add(PearBlocks.CALLERY.getSign());
        getOrCreateTagBuilder(BlockTags.CEILING_HANGING_SIGNS).add(PearBlocks.CALLERY.getHangingSign());
        getOrCreateTagBuilder(BlockTags.WALL_HANGING_SIGNS).add(PearBlocks.CALLERY.getWallHangingSign());
        getOrCreateTagBuilder(BlockTags.ALL_HANGING_SIGNS).add(PearBlocks.CALLERY.getHangingSign()).add(PearBlocks.CALLERY.getWallHangingSign());
        getOrCreateTagBuilder(BlockTags.SAPLINGS).add(PearBlocks.CALLERY_SPROUT);
        getOrCreateTagBuilder(BlockTags.FLOWER_POTS).add(PearBlocks.POTTED_CALLERY_TWIG).add(PearBlocks.POTTED_CALLERY_SPROUT);
        getOrCreateTagBuilder(BlockTags.SMALL_FLOWERS).add(PearBlocks.CALLERY_SPROUT);
        getOrCreateTagBuilder(BlockTags.FLOWERS).add(PearBlocks.CALLERY_VINE);

        /*
            Good idea or no?
         */
        getOrCreateTagBuilder(BlockTags.DAMPENS_VIBRATIONS).add(PearBlocks.LAMPEAR_BLOCK);
        getOrCreateTagBuilder(BlockTags.OCCLUDES_VIBRATION_SIGNALS).add(PearBlocks.LAMPEAR_BLOCK);


        /*
            PEARFECTION
         */
        getOrCreateTagBuilder(PearBlockTags.CALLERY_STEMS)
                .add(PearBlocks.CALLERY.getLog()).add(PearBlocks.CALLERY.getStrippedLog())
                .add(PearBlocks.CALLERY.getWood()).add(PearBlocks.CALLERY.getStrippedWood());

        getOrCreateTagBuilder(PearBlockTags.HUGE_PEAR_CANNOT_REPLACE)
                .add(Blocks.SCULK_CATALYST)
                .add(Blocks.SCULK_SHRIEKER)
                .forceAddTag(BlockTags.BEACON_BASE_BLOCKS)
                .forceAddTag(BlockTags.FEATURES_CANNOT_REPLACE);

        getOrCreateTagBuilder(PearBlockTags.PEAR_GROWABLE_ON)
                .forceAddTag(BlockTags.DIRT)
                .forceAddTag(PearBlockTags.ENORMOUS_PEAR_GROWABLE_ON)
                .forceAddTag(BlockTags.SAND)
                .add(Blocks.SCULK)
                .add(Blocks.FARMLAND);

        getOrCreateTagBuilder(PearBlockTags.ENORMOUS_PEAR_GROWABLE_ON)
                .add(Blocks.MOSS_BLOCK)
                .add(Blocks.PALE_MOSS_BLOCK)
                .addOptional(Identifier.of("biomesoplenty", "glowing_moss_block"))
        ;

    }
}
