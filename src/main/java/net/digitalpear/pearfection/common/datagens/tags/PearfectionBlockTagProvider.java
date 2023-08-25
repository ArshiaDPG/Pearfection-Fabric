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

import java.util.concurrent.CompletableFuture;

public class PearfectionBlockTagProvider extends FabricTagProvider<Block> {
    /**
     * Constructs a new {@link FabricTagProvider} with the default computed path.
     *
     * <p>Common implementations of this class are provided.
     *
     * @param output           the {@link FabricDataOutput} instance
     * @param registriesFuture the backing registry for the tag type
     */
    public PearfectionBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, Registries.BLOCK.getKey(), registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {

        /*
            VANILLA
         */
        getOrCreateTagBuilder(BlockTags.HOE_MINEABLE).add(PearBlocks.LAMPEAR).add(PearBlocks.LAMPEAR_BLOCK).add(PearBlocks.CALLERY_LEAVES);

        getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN).forceAddTag(PearBlockTags.CALLERY_STEMS);
        getOrCreateTagBuilder(BlockTags.PLANKS).add(PearBlocks.CALLERY_PLANKS);
        getOrCreateTagBuilder(BlockTags.WOODEN_STAIRS).add(PearBlocks.CALLERY_STAIRS);
        getOrCreateTagBuilder(BlockTags.WOODEN_SLABS).add(PearBlocks.CALLERY_SLAB);
        getOrCreateTagBuilder(BlockTags.WOODEN_FENCES).add(PearBlocks.CALLERY_FENCE);
        getOrCreateTagBuilder(BlockTags.FENCE_GATES).add(PearBlocks.CALLERY_FENCE_GATE);
        getOrCreateTagBuilder(BlockTags.WOODEN_DOORS).add(PearBlocks.CALLERY_DOOR);
        getOrCreateTagBuilder(BlockTags.WOODEN_TRAPDOORS).add(PearBlocks.CALLERY_TRAPDOOR);
        getOrCreateTagBuilder(BlockTags.LEAVES).add(PearBlocks.CALLERY_LEAVES).add(PearBlocks.FLOWERING_CALLERY_LEAVES);
        getOrCreateTagBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(PearBlocks.CALLERY_PRESSURE_PLATE);
        getOrCreateTagBuilder(BlockTags.BUTTONS).add(PearBlocks.CALLERY_BUTTON);
        getOrCreateTagBuilder(BlockTags.WALL_SIGNS).add(PearBlocks.CALLERY_WALL_SIGN);
        getOrCreateTagBuilder(BlockTags.STANDING_SIGNS).add(PearBlocks.CALLERY_SIGN);
        getOrCreateTagBuilder(BlockTags.CEILING_HANGING_SIGNS).add(PearBlocks.CALLERY_HANGING_SIGN);
        getOrCreateTagBuilder(BlockTags.WALL_HANGING_SIGNS).add(PearBlocks.CALLERY_WALL_HANGING_SIGN);
        getOrCreateTagBuilder(BlockTags.ALL_HANGING_SIGNS).add(PearBlocks.CALLERY_HANGING_SIGN).add(PearBlocks.CALLERY_WALL_HANGING_SIGN);
        getOrCreateTagBuilder(BlockTags.SAPLINGS).add(PearBlocks.CALLERY_SPROUT);
        getOrCreateTagBuilder(BlockTags.FLOWER_POTS).add(PearBlocks.POTTED_CALLERY_TWIG).add(PearBlocks.POTTED_CALLERY_SPROUT);
        getOrCreateTagBuilder(BlockTags.SMALL_FLOWERS).add(PearBlocks.CALLERY_SPROUT);
        getOrCreateTagBuilder(BlockTags.TALL_FLOWERS).add(PearBlocks.CALLERY_VINE);

        /*
            Good idea or no?
         */
        getOrCreateTagBuilder(BlockTags.DAMPENS_VIBRATIONS).add(PearBlocks.LAMPEAR_BLOCK);
        getOrCreateTagBuilder(BlockTags.OCCLUDES_VIBRATION_SIGNALS).add(PearBlocks.LAMPEAR_BLOCK);


        /*
            PEARFECTION
         */
        getOrCreateTagBuilder(PearBlockTags.CALLERY_STEMS)
                .add(PearBlocks.CALLERY_STEM).add(PearBlocks.STRIPPED_CALLERY_STEM)
                .add(PearBlocks.CALLERY_WOOD).add(PearBlocks.STRIPPED_CALLERY_WOOD);

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
                .add(Blocks.MOSS_BLOCK);
    }
}
