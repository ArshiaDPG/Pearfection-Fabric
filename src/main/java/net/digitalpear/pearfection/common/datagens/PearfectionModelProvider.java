package net.digitalpear.pearfection.common.datagens;

import net.digitalpear.pearfection.Pearfection;
import net.digitalpear.pearfection.init.PearBlocks;
import net.digitalpear.pearfection.init.PearItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.client.data.*;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class PearfectionModelProvider extends FabricModelProvider {
    /*
        CODE FOR LAMPEAR MODEL GENERATION
     */
    public static final Model LAMPEAR_BASE = block("lampear_base", TextureKey.ALL);
    public static final Model LAMPEAR_HANGING_BASE = block("lampear_hanging_base", TextureKey.ALL);

    public static final Model PICKET_BASE = block(Identifier.of("bountifulfares", "template_pickets"), TextureKey.TEXTURE);

    public PearfectionModelProvider(FabricDataOutput output) {
        super(output);
    }

    private static Model block(String parent, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(Pearfection.id("block/" + parent)), Optional.empty(), requiredTextureKeys);
    }
    private static Model block(Identifier parent, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(parent.withPrefixedPath("block/")), Optional.empty(), requiredTextureKeys);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        registerLantern(blockStateModelGenerator, PearBlocks.LAMPEAR);
        registerLantern(blockStateModelGenerator, PearBlocks.COPPER_LAMPEAR);

        PearBlocks.CALLERY.fullWoodset(blockStateModelGenerator);

        blockStateModelGenerator.registerSimpleCubeAll(PearBlocks.FLOWERING_CALLERY_LEAVES);

        blockStateModelGenerator.registerDoubleBlock(PearBlocks.CALLERY_VINE, BlockStateModelGenerator.CrossType.NOT_TINTED);
        blockStateModelGenerator.registerFlowerPotPlant(PearBlocks.CALLERY_SPROUT, PearBlocks.POTTED_CALLERY_SPROUT, BlockStateModelGenerator.CrossType.NOT_TINTED);
        blockStateModelGenerator.registerFlowerPotPlant(PearBlocks.CALLERY_TWIG, PearBlocks.POTTED_CALLERY_TWIG, BlockStateModelGenerator.CrossType.NOT_TINTED);

        blockStateModelGenerator.registerParentedItemModel(PearBlocks.CALLERY_VINE, blockStateModelGenerator.uploadBlockItemModel(PearBlocks.CALLERY_VINE.asItem(), PearBlocks.CALLERY_VINE, "_top"));
        blockStateModelGenerator.registerItemModel(PearBlocks.CALLERY_SPROUT);
        blockStateModelGenerator.registerItemModel(PearBlocks.CALLERY_TWIG);

        registerPearBlock(blockStateModelGenerator, PearBlocks.LAMPEAR_BLOCK);


        /*
            MOD COMPAT BLOCKS
         */
//        registerPicketsModels(blockStateModelGenerator, PearBlocks.CALLERY_PICKETS);

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(PearItems.PEAR_TART, Models.GENERATED);
    }

    /*
        USE BASE MODELS TO GENERATE MODELS
     */
    public static void registerLantern(BlockStateModelGenerator blockStateModelGenerator, Block lantern){
        Identifier HANGING = LAMPEAR_HANGING_BASE.upload(lantern, "_hanging", TextureMap.all(lantern), blockStateModelGenerator.modelCollector);
        Identifier STANDING = LAMPEAR_BASE.upload(lantern, TextureMap.all(lantern), blockStateModelGenerator.modelCollector);

        blockStateModelGenerator.registerItemModel(lantern.asItem());
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(lantern)
                .coordinate(BlockStateModelGenerator.createBooleanModelMap(Properties.HANGING, HANGING, STANDING)));
    }

    public static void registerPearBlock(BlockStateModelGenerator blockStateModelGenerator, Block pearBlock) {
        Identifier outerID = Models.TEMPLATE_SINGLE_FACE.upload(pearBlock, TextureMap.texture(pearBlock), blockStateModelGenerator.modelCollector);
        Identifier insideID = Models.TEMPLATE_SINGLE_FACE.upload(pearBlock, "_inside", TextureMap.texture(getId(pearBlock, "_inside")), blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(MultipartBlockStateSupplier.create(pearBlock).with(When.create().set(Properties.NORTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, outerID)).with(When.create().set(Properties.EAST, true), BlockStateVariant.create().put(VariantSettings.MODEL, outerID).put(VariantSettings.Y, VariantSettings.Rotation.R90).put(VariantSettings.UVLOCK, true)).with(When.create().set(Properties.SOUTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, outerID).put(VariantSettings.Y, VariantSettings.Rotation.R180).put(VariantSettings.UVLOCK, true)).with(When.create().set(Properties.WEST, true), BlockStateVariant.create().put(VariantSettings.MODEL, outerID).put(VariantSettings.Y, VariantSettings.Rotation.R270).put(VariantSettings.UVLOCK, true)).with(When.create().set(Properties.UP, true), BlockStateVariant.create().put(VariantSettings.MODEL, outerID).put(VariantSettings.X, VariantSettings.Rotation.R270).put(VariantSettings.UVLOCK, true)).with(When.create().set(Properties.DOWN, true), BlockStateVariant.create().put(VariantSettings.MODEL, outerID).put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.UVLOCK, true)).with(When.create().set(Properties.NORTH, false), BlockStateVariant.create().put(VariantSettings.MODEL, insideID)).with(When.create().set(Properties.EAST, false), BlockStateVariant.create().put(VariantSettings.MODEL, insideID).put(VariantSettings.Y, VariantSettings.Rotation.R90).put(VariantSettings.UVLOCK, false)).with(When.create().set(Properties.SOUTH, false), BlockStateVariant.create().put(VariantSettings.MODEL, insideID).put(VariantSettings.Y, VariantSettings.Rotation.R180).put(VariantSettings.UVLOCK, false)).with(When.create().set(Properties.WEST, false), BlockStateVariant.create().put(VariantSettings.MODEL, insideID).put(VariantSettings.Y, VariantSettings.Rotation.R270).put(VariantSettings.UVLOCK, false)).with(When.create().set(Properties.UP, false), BlockStateVariant.create().put(VariantSettings.MODEL, insideID).put(VariantSettings.X, VariantSettings.Rotation.R270).put(VariantSettings.UVLOCK, false)).with(When.create().set(Properties.DOWN, false), BlockStateVariant.create().put(VariantSettings.MODEL, insideID).put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.UVLOCK, false)));
        blockStateModelGenerator.registerParentedItemModel(pearBlock, TexturedModel.CUBE_ALL.upload(pearBlock, "_inventory", blockStateModelGenerator.modelCollector));
    }

    public static Identifier getItemId(Block block) {
        Identifier identifier = Registries.BLOCK.getId(block);
        return identifier.withPrefixedPath("item/");
    }
    public static Identifier getId(Block block) {
        Identifier identifier = Registries.BLOCK.getId(block);
        return identifier.withPrefixedPath("block/");
    }
    public static Identifier getId(Block block, String suffix) {
        return getId(block).withSuffixedPath(suffix);
    }
    public static Identifier getId(String prefix, Block block) {
        Identifier identifier = Registries.BLOCK.getId(block);
        identifier = identifier.withPrefixedPath(prefix);
        return identifier.withPrefixedPath("block/");
    }
}
