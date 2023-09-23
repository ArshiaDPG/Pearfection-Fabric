package net.digitalpear.pearfection.common.datagens;

import net.digitalpear.pearfection.Pearfection;
import net.digitalpear.pearfection.init.PearBlocks;
import net.digitalpear.pearfection.init.PearItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
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

    private static Model block(String parent, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(new Identifier(Pearfection.MOD_ID, "block/" + parent)), Optional.empty(), requiredTextureKeys);
    }
    private static Model block(String id,String parent, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(new Identifier(id, "block/" + parent)), Optional.empty(), requiredTextureKeys);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        registerLantern(blockStateModelGenerator, PearBlocks.LAMPEAR);
        registerLantern(blockStateModelGenerator, PearBlocks.COPPER_LAMPEAR);

        blockStateModelGenerator.registerLog(PearBlocks.CALLERY_STEM).stem(PearBlocks.CALLERY_STEM).wood(PearBlocks.CALLERY_WOOD);
        blockStateModelGenerator.registerLog(PearBlocks.STRIPPED_CALLERY_STEM).stem(PearBlocks.STRIPPED_CALLERY_STEM).wood(PearBlocks.STRIPPED_CALLERY_WOOD);
        blockStateModelGenerator.registerSimpleCubeAll(PearBlocks.CALLERY_PLANKS);
        createSlab(blockStateModelGenerator, PearBlocks.CALLERY_PLANKS, PearBlocks.CALLERY_SLAB);
        createStairs(blockStateModelGenerator, PearBlocks.CALLERY_PLANKS, PearBlocks.CALLERY_STAIRS);
        makeButton(blockStateModelGenerator, PearBlocks.CALLERY_PLANKS, PearBlocks.CALLERY_BUTTON);
        makePressurePlate(blockStateModelGenerator, PearBlocks.CALLERY_PLANKS, PearBlocks.CALLERY_PRESSURE_PLATE);
        fence(blockStateModelGenerator, PearBlocks.CALLERY_PLANKS, PearBlocks.CALLERY_FENCE);
        fenceGate(blockStateModelGenerator, PearBlocks.CALLERY_PLANKS, PearBlocks.CALLERY_FENCE_GATE);
        blockStateModelGenerator.registerDoor(PearBlocks.CALLERY_DOOR);
        blockStateModelGenerator.registerTrapdoor(PearBlocks.CALLERY_TRAPDOOR);
        blockStateModelGenerator.registerSimpleCubeAll(PearBlocks.CALLERY_LEAVES);
        blockStateModelGenerator.registerSimpleCubeAll(PearBlocks.FLOWERING_CALLERY_LEAVES);
        blockStateModelGenerator.registerHangingSign(PearBlocks.STRIPPED_CALLERY_STEM, PearBlocks.CALLERY_HANGING_SIGN, PearBlocks.CALLERY_WALL_HANGING_SIGN);

        makeParticles(blockStateModelGenerator, PearBlocks.CALLERY_PLANKS, PearBlocks.CALLERY_SIGN, PearBlocks.CALLERY_WALL_SIGN);


        blockStateModelGenerator.registerDoubleBlock(PearBlocks.CALLERY_VINE, BlockStateModelGenerator.TintType.NOT_TINTED);
        blockStateModelGenerator.registerFlowerPotPlant(PearBlocks.CALLERY_SPROUT, PearBlocks.POTTED_CALLERY_SPROUT, BlockStateModelGenerator.TintType.NOT_TINTED);
        blockStateModelGenerator.registerFlowerPotPlant(PearBlocks.CALLERY_TWIG, PearBlocks.POTTED_CALLERY_TWIG, BlockStateModelGenerator.TintType.NOT_TINTED);

        registerPearBlock(blockStateModelGenerator, PearBlocks.LAMPEAR_BLOCK);
    }

//    private void registerBookshelf(BlockStateModelGenerator blockStateModelGenerator) {
//        TextureMap textureMap = TextureMap.sideEnd(getId(PearBlocks.CALLERY_BOOKSHELF), getId(PearBlocks.CALLERY_PLANKS));
//        Identifier identifier = Models.CUBE_COLUMN.upload(PearBlocks.CALLERY_BOOKSHELF, textureMap, blockStateModelGenerator.modelCollector);
//        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(PearBlocks.CALLERY_BOOKSHELF, identifier));
//    }

    public static void makeStuff(BlockStateModelGenerator blockStateModelGenerator, Block planks, Block stairs, Block slab, Block fence, Block fenceGate, Block button, Block pressurePlate){
        blockStateModelGenerator.registerSimpleCubeAll(planks);
        createStairs(blockStateModelGenerator, planks, stairs);
        createSlab(blockStateModelGenerator, planks, slab);
        makeButton(blockStateModelGenerator, planks, button);
        makePressurePlate(blockStateModelGenerator, planks, pressurePlate);
        fence(blockStateModelGenerator, planks, fence);
        fenceGate(blockStateModelGenerator, planks, fenceGate);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(PearItems.CALLERY_BOAT, Models.GENERATED);
        itemModelGenerator.register(PearItems.CALLERY_CHEST_BOAT, Models.GENERATED);
        itemModelGenerator.register(PearItems.CALLERY_SIGN, Models.GENERATED);
        itemModelGenerator.register(PearItems.PEAR_TART, Models.GENERATED);

    }

    public void makeParticles(BlockStateModelGenerator blockStateModelGenerator, Block particle, Block sign, Block wallSign){
        Identifier identifier = Models.PARTICLE.upload(sign, TextureMap.particle(new Identifier(Pearfection.MOD_ID, "block/" + Registries.BLOCK.getId(particle).getPath())), blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(sign, identifier));
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(wallSign, identifier));
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



    public static void createStairs(BlockStateModelGenerator blockStateModelGenerator, Block textureBase, Block stairs){
        Identifier STAIRS = Models.STAIRS.upload(stairs, TextureMap.all(textureBase), blockStateModelGenerator.modelCollector);
        Identifier INNER_STAIRS = Models.INNER_STAIRS.upload(stairs, TextureMap.all(textureBase), blockStateModelGenerator.modelCollector);
        Identifier OUTER_STAIRS = Models.OUTER_STAIRS.upload(stairs, TextureMap.all(textureBase), blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createStairsBlockState(stairs,
                INNER_STAIRS, STAIRS, OUTER_STAIRS));
    }
    public static void createSlab(BlockStateModelGenerator blockStateModelGenerator, Block textureBase, Block slab){
        Identifier SLAB = Models.SLAB.upload(slab, TextureMap.all(textureBase), blockStateModelGenerator.modelCollector);
        Identifier SLAB_TOP = Models.SLAB_TOP.upload(slab, TextureMap.all(textureBase), blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSlabBlockState(slab,
                SLAB, SLAB_TOP, getId(textureBase)));
    }
    public static void makeButton(BlockStateModelGenerator blockStateModelGenerator, Block textureBase, Block button){
        Identifier BUTTON = Models.BUTTON.upload(button, TextureMap.all(textureBase), blockStateModelGenerator.modelCollector);
        Identifier BUTTON_PRESSED = Models.BUTTON_PRESSED.upload(button, TextureMap.all(textureBase), blockStateModelGenerator.modelCollector);
        Identifier BUTTON_INVENTORY = Models.BUTTON_INVENTORY.upload(button, TextureMap.all(textureBase), blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createButtonBlockState(button,
                BUTTON, BUTTON_PRESSED));
        blockStateModelGenerator.registerParentedItemModel(button.asItem(), BUTTON_INVENTORY);
    }

    public static void makePressurePlate(BlockStateModelGenerator blockStateModelGenerator, Block textureBase, Block plate){
        Identifier PRESSURE_PLATE_DOWN = Models.PRESSURE_PLATE_DOWN.upload(plate, TextureMap.all(textureBase), blockStateModelGenerator.modelCollector);
        Identifier PRESSURE_PLATE_UP = Models.PRESSURE_PLATE_UP.upload(plate, TextureMap.all(textureBase), blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createPressurePlateBlockState(plate,
                PRESSURE_PLATE_UP, PRESSURE_PLATE_DOWN));
    }
    public static void fence(BlockStateModelGenerator blockStateModelGenerator, Block textureBase, Block fenceBlock) {
        Identifier identifier = Models.FENCE_POST.upload(fenceBlock, TextureMap.all(textureBase), blockStateModelGenerator.modelCollector);
        Identifier identifier2 = Models.FENCE_SIDE.upload(fenceBlock, TextureMap.all(textureBase), blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createFenceBlockState(fenceBlock, identifier, identifier2));
        Identifier identifier3 = Models.FENCE_INVENTORY.upload(fenceBlock, TextureMap.all(textureBase), blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.registerParentedItemModel(fenceBlock, identifier3);
    }
    public static void fenceGate(BlockStateModelGenerator blockStateModelGenerator, Block textureBase, Block fenceGateBlock) {
        Identifier identifier = Models.TEMPLATE_FENCE_GATE_OPEN.upload(fenceGateBlock, TextureMap.all(textureBase), blockStateModelGenerator.modelCollector);
        Identifier identifier2 = Models.TEMPLATE_FENCE_GATE.upload(fenceGateBlock, TextureMap.all(textureBase), blockStateModelGenerator.modelCollector);
        Identifier identifier3 = Models.TEMPLATE_FENCE_GATE_WALL_OPEN.upload(fenceGateBlock, TextureMap.all(textureBase), blockStateModelGenerator.modelCollector);
        Identifier identifier4 = Models.TEMPLATE_FENCE_GATE_WALL.upload(fenceGateBlock, TextureMap.all(textureBase), blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createFenceGateBlockState(fenceGateBlock, identifier, identifier2, identifier3, identifier4, true));
    }
    public final void registerPearBlock(BlockStateModelGenerator blockStateModelGenerator, Block pearBlock) {
        Identifier outerID = Models.TEMPLATE_SINGLE_FACE.upload(pearBlock, TextureMap.texture(pearBlock), blockStateModelGenerator.modelCollector);
        Identifier insideID = Models.TEMPLATE_SINGLE_FACE.upload(pearBlock, "_inside", TextureMap.texture(getId(pearBlock, "_inside")), blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(MultipartBlockStateSupplier.create(pearBlock).with(When.create().set(Properties.NORTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, outerID)).with(When.create().set(Properties.EAST, true), BlockStateVariant.create().put(VariantSettings.MODEL, outerID).put(VariantSettings.Y, VariantSettings.Rotation.R90).put(VariantSettings.UVLOCK, true)).with(When.create().set(Properties.SOUTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, outerID).put(VariantSettings.Y, VariantSettings.Rotation.R180).put(VariantSettings.UVLOCK, true)).with(When.create().set(Properties.WEST, true), BlockStateVariant.create().put(VariantSettings.MODEL, outerID).put(VariantSettings.Y, VariantSettings.Rotation.R270).put(VariantSettings.UVLOCK, true)).with(When.create().set(Properties.UP, true), BlockStateVariant.create().put(VariantSettings.MODEL, outerID).put(VariantSettings.X, VariantSettings.Rotation.R270).put(VariantSettings.UVLOCK, true)).with(When.create().set(Properties.DOWN, true), BlockStateVariant.create().put(VariantSettings.MODEL, outerID).put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.UVLOCK, true)).with(When.create().set(Properties.NORTH, false), BlockStateVariant.create().put(VariantSettings.MODEL, insideID)).with(When.create().set(Properties.EAST, false), BlockStateVariant.create().put(VariantSettings.MODEL, insideID).put(VariantSettings.Y, VariantSettings.Rotation.R90).put(VariantSettings.UVLOCK, false)).with(When.create().set(Properties.SOUTH, false), BlockStateVariant.create().put(VariantSettings.MODEL, insideID).put(VariantSettings.Y, VariantSettings.Rotation.R180).put(VariantSettings.UVLOCK, false)).with(When.create().set(Properties.WEST, false), BlockStateVariant.create().put(VariantSettings.MODEL, insideID).put(VariantSettings.Y, VariantSettings.Rotation.R270).put(VariantSettings.UVLOCK, false)).with(When.create().set(Properties.UP, false), BlockStateVariant.create().put(VariantSettings.MODEL, insideID).put(VariantSettings.X, VariantSettings.Rotation.R270).put(VariantSettings.UVLOCK, false)).with(When.create().set(Properties.DOWN, false), BlockStateVariant.create().put(VariantSettings.MODEL, insideID).put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.UVLOCK, false)));
        blockStateModelGenerator.registerParentedItemModel(pearBlock, TexturedModel.CUBE_ALL.upload(pearBlock, "_inventory", blockStateModelGenerator.modelCollector));
    }







    public static Identifier getId(Block block) {
        Identifier identifier = Registries.BLOCK.getId(block);
        return identifier.withPrefixedPath("block/");
    }
    public static Identifier getId(Block block, String suffix) {
        Identifier identifier = Registries.BLOCK.getId(block);
        identifier = identifier.withSuffixedPath(suffix);
        return identifier.withPrefixedPath("block/");
    }
    public static Identifier getId(String prefix, Block block) {
        Identifier identifier = Registries.BLOCK.getId(block);
        identifier = identifier.withPrefixedPath(prefix);
        return identifier.withPrefixedPath("block/");
    }

}
