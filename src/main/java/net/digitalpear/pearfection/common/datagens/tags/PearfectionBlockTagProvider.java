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
import net.minecraft.registry.tag.TagEntry;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class PearfectionBlockTagProvider extends FabricTagProvider.BlockTagProvider {

    public PearfectionBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {

        /*
            VANILLA
         */
        valueLookupBuilder(BlockTags.HOE_MINEABLE).add(PearBlocks.LAMPEAR).add(PearBlocks.LAMPEAR_BASE_BLOCK).add(PearBlocks.CALLERY.getLeaves());

        valueLookupBuilder(BlockTags.LOGS_THAT_BURN).forceAddTag(PearBlockTags.CALLERY_STEMS);
        valueLookupBuilder(BlockTags.PLANKS).add(PearBlocks.CALLERY.getPlanks());
        valueLookupBuilder(BlockTags.WOODEN_STAIRS).add(PearBlocks.CALLERY.getStairs());
        valueLookupBuilder(BlockTags.WOODEN_SLABS).add(PearBlocks.CALLERY.getSlab());
        valueLookupBuilder(BlockTags.WOODEN_FENCES).add(PearBlocks.CALLERY.getFence());
        valueLookupBuilder(BlockTags.FENCE_GATES).add(PearBlocks.CALLERY.getFenceGate());
        valueLookupBuilder(BlockTags.WOODEN_DOORS).add(PearBlocks.CALLERY.getDoor());
        valueLookupBuilder(BlockTags.WOODEN_TRAPDOORS).add(PearBlocks.CALLERY.getTrapDoor());
        valueLookupBuilder(BlockTags.LEAVES).add(PearBlocks.CALLERY.getLeaves()).add(PearBlocks.FLOWERING_CALLERY_LEAVES);
        valueLookupBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(PearBlocks.CALLERY.getPressurePlate());
        valueLookupBuilder(BlockTags.BUTTONS).add(PearBlocks.CALLERY.getButton());
        valueLookupBuilder(BlockTags.WALL_SIGNS).add(PearBlocks.CALLERY.getWallSign());
        valueLookupBuilder(BlockTags.STANDING_SIGNS).add(PearBlocks.CALLERY.getSign());
        valueLookupBuilder(BlockTags.CEILING_HANGING_SIGNS).add(PearBlocks.CALLERY.getHangingSign());
        valueLookupBuilder(BlockTags.WALL_HANGING_SIGNS).add(PearBlocks.CALLERY.getWallHangingSign());
        valueLookupBuilder(BlockTags.ALL_HANGING_SIGNS).add(PearBlocks.CALLERY.getHangingSign()).add(PearBlocks.CALLERY.getWallHangingSign());
        valueLookupBuilder(BlockTags.SAPLINGS).add(PearBlocks.CALLERY_SPROUT);
        valueLookupBuilder(BlockTags.FLOWER_POTS).add(PearBlocks.POTTED_CALLERY_TWIG).add(PearBlocks.POTTED_CALLERY_SPROUT);
        valueLookupBuilder(BlockTags.SMALL_FLOWERS).add(PearBlocks.CALLERY_SPROUT);
        valueLookupBuilder(BlockTags.FLOWERS).add(PearBlocks.CALLERY_VINE);
        valueLookupBuilder(BlockTags.WOODEN_SHELVES).add(PearBlocks.CALLERY.getShelf());

        /*
            Good idea or no?
         */
        valueLookupBuilder(BlockTags.DAMPENS_VIBRATIONS).add(PearBlocks.LAMPEAR_BASE_BLOCK);
        valueLookupBuilder(BlockTags.OCCLUDES_VIBRATION_SIGNALS).add(PearBlocks.LAMPEAR_BASE_BLOCK);


        /*
            PEARFECTION
         */
        valueLookupBuilder(PearBlockTags.CALLERY_STEMS)
                .add(PearBlocks.CALLERY.getLog()).add(PearBlocks.CALLERY.getStrippedLog())
                .add(PearBlocks.CALLERY.getWood()).add(PearBlocks.CALLERY.getStrippedWood());

        valueLookupBuilder(PearBlockTags.HUGE_PEAR_CANNOT_REPLACE)
                .add(Blocks.SCULK_CATALYST)
                .add(Blocks.SCULK_SHRIEKER)
                .forceAddTag(BlockTags.BEACON_BASE_BLOCKS)
                .forceAddTag(BlockTags.FEATURES_CANNOT_REPLACE);

        valueLookupBuilder(PearBlockTags.PEAR_GROWABLE_ON)
                .forceAddTag(BlockTags.DIRT)
                .forceAddTag(PearBlockTags.ENORMOUS_PEAR_GROWABLE_ON)
                .forceAddTag(BlockTags.SAND)
                .add(Blocks.SCULK)
                .add(Blocks.FARMLAND);

        getTagBuilder(PearBlockTags.ENORMOUS_PEAR_GROWABLE_ON)
                .add(getId(Blocks.MOSS_BLOCK))
                .add(getId(Blocks.PALE_MOSS_BLOCK))
                .addOptional(Identifier.of("biomesoplenty", "glowing_moss_block"))
        ;

    }
    public static TagEntry getId(Block block){
        return TagEntry.create(Registries.BLOCK.getId(block));
    }
}
