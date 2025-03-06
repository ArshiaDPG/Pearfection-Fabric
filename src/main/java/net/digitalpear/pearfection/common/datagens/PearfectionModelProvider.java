package net.digitalpear.pearfection.common.datagens;

import net.digitalpear.pearfection.Pearfection;
import net.digitalpear.pearfection.init.PearBlocks;
import net.digitalpear.pearfection.init.PearItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.client.data.*;
import net.minecraft.client.render.model.json.MultipartModelConditionBuilder;
import net.minecraft.client.render.model.json.WeightedUnbakedModel;
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
        WeightedUnbakedModel HANGING = BlockStateModelGenerator.createModel(LAMPEAR_HANGING_BASE.upload(lantern, "_hanging", TextureMap.all(lantern), blockStateModelGenerator.modelCollector));
        WeightedUnbakedModel STANDING = BlockStateModelGenerator.createModel(LAMPEAR_BASE.upload(lantern, TextureMap.all(lantern), blockStateModelGenerator.modelCollector));

        blockStateModelGenerator.registerItemModel(lantern.asItem());
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(lantern)
                .with(BlockStateModelGenerator.createBooleanModelMap(Properties.HANGING, HANGING, STANDING)));
    }

    public static void registerPearBlock(BlockStateModelGenerator blockStateModelGenerator, Block pearBlock) {
        WeightedUnbakedModel outerID = BlockStateModelGenerator.createModel(Models.TEMPLATE_SINGLE_FACE.upload(pearBlock, TextureMap.texture(pearBlock), blockStateModelGenerator.modelCollector));
        WeightedUnbakedModel insideID = BlockStateModelGenerator.createModel(Models.TEMPLATE_SINGLE_FACE.upload(pearBlock, "_inside", TextureMap.texture(getId(pearBlock, "_inside")), blockStateModelGenerator.modelCollector));
        blockStateModelGenerator.blockStateCollector.accept(MultipartBlockModelDefinitionCreator.create(pearBlock)
                .with(BlockStateModelGenerator.createMultipartConditionBuilder().put(Properties.NORTH, true), outerID)
                .with(BlockStateModelGenerator.createMultipartConditionBuilder().put(Properties.EAST, true), outerID.apply(BlockStateModelGenerator.ROTATE_Y_90).apply(BlockStateModelGenerator.UV_LOCK))
                .with(BlockStateModelGenerator.createMultipartConditionBuilder().put(Properties.SOUTH, true), outerID.apply(BlockStateModelGenerator.ROTATE_Y_180).apply(BlockStateModelGenerator.UV_LOCK))
                .with(BlockStateModelGenerator.createMultipartConditionBuilder().put(Properties.WEST, true), outerID.apply(BlockStateModelGenerator.ROTATE_Y_270).apply(BlockStateModelGenerator.UV_LOCK))
                .with(BlockStateModelGenerator.createMultipartConditionBuilder().put(Properties.UP, true), outerID.apply(BlockStateModelGenerator.ROTATE_X_270).apply(BlockStateModelGenerator.UV_LOCK))
                .with(BlockStateModelGenerator.createMultipartConditionBuilder().put(Properties.DOWN, true), outerID.apply(BlockStateModelGenerator.ROTATE_X_90).apply(BlockStateModelGenerator.UV_LOCK))
                .with(BlockStateModelGenerator.createMultipartConditionBuilder().put(Properties.NORTH, false), insideID).with(BlockStateModelGenerator.createMultipartConditionBuilder().put(Properties.EAST, false), insideID.apply(BlockStateModelGenerator.ROTATE_Y_90))
                .with(BlockStateModelGenerator.createMultipartConditionBuilder().put(Properties.SOUTH, false), insideID.apply(BlockStateModelGenerator.ROTATE_Y_180))
                .with(BlockStateModelGenerator.createMultipartConditionBuilder().put(Properties.WEST, false), insideID.apply(BlockStateModelGenerator.ROTATE_Y_270))
                .with(BlockStateModelGenerator.createMultipartConditionBuilder().put(Properties.UP, false), insideID.apply(BlockStateModelGenerator.ROTATE_X_270))
                .with(BlockStateModelGenerator.createMultipartConditionBuilder().put(Properties.DOWN, false), insideID.apply(BlockStateModelGenerator.ROTATE_X_90))
        );
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
