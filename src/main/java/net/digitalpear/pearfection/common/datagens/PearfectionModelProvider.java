package net.digitalpear.pearfection.common.datagens;

import net.digitalpear.pearfection.Pearfection;
import net.digitalpear.pearfection.init.PearBlocks;
import net.digitalpear.pearfection.init.PearItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class PearfectionModelProvider extends FabricModelProvider {
    public PearfectionModelProvider(FabricDataOutput output) {
        super(output);
    }
    /*
        CODE FOR LAMPEAR MODEL GENERATION
     */
    public static final Model LAMPEAR_BASE = block("lampear_base", TextureKey.ALL);
    public static final Model LAMPEAR_HANGING_BASE = block("lampear_hanging_base", TextureKey.ALL);

    public static final Model PICKET_BASE = block(Identifier.of("bountifulfares", "template_pickets"), TextureKey.TEXTURE);

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
//        blockStateModelGenerator.registerLog(PearBlocks.CALLERY_STEM).stem(PearBlocks.CALLERY_STEM).wood(PearBlocks.CALLERY_WOOD);
//        blockStateModelGenerator.registerLog(PearBlocks.STRIPPED_CALLERY_STEM).stem(PearBlocks.STRIPPED_CALLERY_STEM).wood(PearBlocks.STRIPPED_CALLERY_WOOD);
//        makeStuff(blockStateModelGenerator, PearBlocks.CALLERY_PLANKS, PearBlocks.CALLERY_STAIRS, PearBlocks.CALLERY_SLAB, PearBlocks.CALLERY_FENCE, PearBlocks.CALLERY_FENCE_GATE, PearBlocks.CALLERY_BUTTON, PearBlocks.CALLERY_PRESSURE_PLATE);
//        blockStateModelGenerator.registerDoor(PearBlocks.CALLERY_DOOR);
//        blockStateModelGenerator.registerTrapdoor(PearBlocks.CALLERY_TRAPDOOR);
//        blockStateModelGenerator.registerSimpleCubeAll(PearBlocks.CALLERY_LEAVES);
        blockStateModelGenerator.registerSimpleCubeAll(PearBlocks.FLOWERING_CALLERY_LEAVES);
//        blockStateModelGenerator.registerHangingSign(PearBlocks.STRIPPED_CALLERY_STEM, PearBlocks.CALLERY_HANGING_SIGN, PearBlocks.CALLERY_WALL_HANGING_SIGN);
//        makeParticles(blockStateModelGenerator, PearBlocks.CALLERY_PLANKS, PearBlocks.CALLERY_SIGN, PearBlocks.CALLERY_WALL_SIGN);
        blockStateModelGenerator.registerDoubleBlock(PearBlocks.CALLERY_VINE, BlockStateModelGenerator.TintType.NOT_TINTED);
        blockStateModelGenerator.registerFlowerPotPlant(PearBlocks.CALLERY_SPROUT, PearBlocks.POTTED_CALLERY_SPROUT, BlockStateModelGenerator.TintType.NOT_TINTED);
        blockStateModelGenerator.registerFlowerPotPlant(PearBlocks.CALLERY_TWIG, PearBlocks.POTTED_CALLERY_TWIG, BlockStateModelGenerator.TintType.NOT_TINTED);

        registerPearBlock(blockStateModelGenerator, PearBlocks.LAMPEAR_BLOCK);


        /*
            MOD COMPAT BLOCKS
         */
//        registerPicketsModels(blockStateModelGenerator, PearBlocks.CALLERY_PICKETS);

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
//        itemModelGenerator.register(PearItems.CALLERY_BOAT, Models.GENERATED);
//        itemModelGenerator.register(PearItems.CALLERY_CHEST_BOAT, Models.GENERATED);
//        itemModelGenerator.register(PearItems.CALLERY_SIGN, Models.GENERATED);
        itemModelGenerator.register(PearItems.PEAR_TART, Models.GENERATED);

    }

    /*
        USE BASE MODELS TO GENERATE MODELS
     */
    public final void registerLantern(BlockStateModelGenerator blockStateModelGenerator, Block lantern){
        Identifier HANGING = LAMPEAR_HANGING_BASE.upload(lantern, "_hanging", TextureMap.all(lantern), blockStateModelGenerator.modelCollector);
        Identifier STANDING = LAMPEAR_BASE.upload(lantern, TextureMap.all(lantern), blockStateModelGenerator.modelCollector);

        blockStateModelGenerator.registerItemModel(lantern.asItem());
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(lantern)
                .coordinate(BlockStateModelGenerator.createBooleanModelMap(Properties.HANGING, HANGING, STANDING)));
    }

    public final void registerPearBlock(BlockStateModelGenerator blockStateModelGenerator, Block pearBlock) {
        Identifier outerID = Models.TEMPLATE_SINGLE_FACE.upload(pearBlock, TextureMap.texture(pearBlock), blockStateModelGenerator.modelCollector);
        Identifier insideID = Models.TEMPLATE_SINGLE_FACE.upload(pearBlock, "_inside", TextureMap.texture(getId(pearBlock, "_inside")), blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(MultipartBlockStateSupplier.create(pearBlock).with(When.create().set(Properties.NORTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, outerID)).with(When.create().set(Properties.EAST, true), BlockStateVariant.create().put(VariantSettings.MODEL, outerID).put(VariantSettings.Y, VariantSettings.Rotation.R90).put(VariantSettings.UVLOCK, true)).with(When.create().set(Properties.SOUTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, outerID).put(VariantSettings.Y, VariantSettings.Rotation.R180).put(VariantSettings.UVLOCK, true)).with(When.create().set(Properties.WEST, true), BlockStateVariant.create().put(VariantSettings.MODEL, outerID).put(VariantSettings.Y, VariantSettings.Rotation.R270).put(VariantSettings.UVLOCK, true)).with(When.create().set(Properties.UP, true), BlockStateVariant.create().put(VariantSettings.MODEL, outerID).put(VariantSettings.X, VariantSettings.Rotation.R270).put(VariantSettings.UVLOCK, true)).with(When.create().set(Properties.DOWN, true), BlockStateVariant.create().put(VariantSettings.MODEL, outerID).put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.UVLOCK, true)).with(When.create().set(Properties.NORTH, false), BlockStateVariant.create().put(VariantSettings.MODEL, insideID)).with(When.create().set(Properties.EAST, false), BlockStateVariant.create().put(VariantSettings.MODEL, insideID).put(VariantSettings.Y, VariantSettings.Rotation.R90).put(VariantSettings.UVLOCK, false)).with(When.create().set(Properties.SOUTH, false), BlockStateVariant.create().put(VariantSettings.MODEL, insideID).put(VariantSettings.Y, VariantSettings.Rotation.R180).put(VariantSettings.UVLOCK, false)).with(When.create().set(Properties.WEST, false), BlockStateVariant.create().put(VariantSettings.MODEL, insideID).put(VariantSettings.Y, VariantSettings.Rotation.R270).put(VariantSettings.UVLOCK, false)).with(When.create().set(Properties.UP, false), BlockStateVariant.create().put(VariantSettings.MODEL, insideID).put(VariantSettings.X, VariantSettings.Rotation.R270).put(VariantSettings.UVLOCK, false)).with(When.create().set(Properties.DOWN, false), BlockStateVariant.create().put(VariantSettings.MODEL, insideID).put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.UVLOCK, false)));
        blockStateModelGenerator.registerParentedItemModel(pearBlock, TexturedModel.CUBE_ALL.upload(pearBlock, "_inventory", blockStateModelGenerator.modelCollector));
    }

//    public final void registerPicketsModels(BlockStateModelGenerator blockStateModelGenerator, Block picket){
//        Identifier modelID = PICKET_BASE.upload(picket, TextureMap.texture(picket), blockStateModelGenerator.modelCollector);
//        blockStateModelGenerator.blockStateCollector.accept(MultipartBlockStateSupplier.create(picket)
//                .with(When.create().set(PicketsBlock.NORTH, true),
//                        BlockStateVariant.create().put(VariantSettings.MODEL, modelID))
//                .with(When.create()
//                    .set(PicketsBlock.NORTH, false)
//                    .set(PicketsBlock.SOUTH, false)
//                    .set(PicketsBlock.EAST, false)
//                    .set(PicketsBlock.WEST, false),
//                        BlockStateVariant.create().put(VariantSettings.MODEL, modelID))
//
//
//                .with(When.create().set(PicketsBlock.EAST, true),
//                        BlockStateVariant.create().put(VariantSettings.MODEL, modelID).put(VariantSettings.Y, VariantSettings.Rotation.R90))
//                .with(When.create()
//                    .set(PicketsBlock.NORTH, false)
//                    .set(PicketsBlock.SOUTH, false)
//                    .set(PicketsBlock.EAST, false)
//                    .set(PicketsBlock.WEST, false),
//                        BlockStateVariant.create().put(VariantSettings.MODEL, modelID).put(VariantSettings.Y, VariantSettings.Rotation.R90))
//
//
//                .with(When.create().set(PicketsBlock.SOUTH, true),
//                        BlockStateVariant.create().put(VariantSettings.MODEL, modelID).put(VariantSettings.Y, VariantSettings.Rotation.R180))
//                .with(When.create()
//                    .set(PicketsBlock.NORTH, false)
//                    .set(PicketsBlock.SOUTH, false)
//                    .set(PicketsBlock.EAST, false)
//                    .set(PicketsBlock.WEST, false),
//                        BlockStateVariant.create().put(VariantSettings.MODEL, modelID).put(VariantSettings.Y, VariantSettings.Rotation.R180))
//
//
//                .with(When.create().set(PicketsBlock.WEST, true),
//                        BlockStateVariant.create().put(VariantSettings.MODEL, modelID).put(VariantSettings.Y, VariantSettings.Rotation.R270))
//                .with(When.create()
//                    .set(PicketsBlock.NORTH, false)
//                    .set(PicketsBlock.SOUTH, false)
//                    .set(PicketsBlock.EAST, false)
//                    .set(PicketsBlock.WEST, false),
//                        BlockStateVariant.create().put(VariantSettings.MODEL, modelID).put(VariantSettings.Y, VariantSettings.Rotation.R270))
//
//        );
//        Models.GENERATED.upload(ModelIds.getItemModelId(picket.asItem()), TextureMap.layer0(getItemId(picket)), blockStateModelGenerator.modelCollector);
//    }






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
