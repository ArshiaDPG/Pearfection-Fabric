package net.digitalpear.pearfection.common.datagens;

import net.digitalpear.pearfection.Pearfection;
import net.digitalpear.pearfection.init.PearBlocks;
import net.digitalpear.pearfection.init.tags.PearItemTags;
import net.digitalpear.pearfection.init.PearItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class PearRecipeGen extends FabricRecipeProvider {
    public PearRecipeGen(FabricDataOutput output) {
        super(output);
    }


    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {

        makeRecipes(exporter, PearBlocks.CALLERY_PLANKS, PearBlocks.CALLERY_STAIRS, PearBlocks.CALLERY_SLAB, PearBlocks.CALLERY_FENCE, PearBlocks.CALLERY_FENCE_GATE,
                PearBlocks.CALLERY_DOOR, PearBlocks.CALLERY_TRAPDOOR, PearBlocks.CALLERY_BUTTON, PearBlocks.CALLERY_PRESSURE_PLATE, PearItems.CALLERY_SIGN);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, Items.STICK, 2)
                        .input(PearBlocks.CALLERY_TWIG)
                .criterion(hasItem(PearBlocks.CALLERY_TWIG), conditionsFromItem(PearBlocks.CALLERY_TWIG)).offerTo(exporter, Registries.ITEM.getId(Items.STICK).withSuffixedPath("_from_callery_twig"));


        RecipeProvider.offerBoatRecipe(exporter, PearItems.CALLERY_BOAT, PearBlocks.CALLERY_PLANKS);
        RecipeProvider.offerChestBoatRecipe(exporter, PearItems.CALLERY_CHEST_BOAT, PearBlocks.CALLERY_PLANKS);
        RecipeProvider.offerHangingSignRecipe(exporter, PearItems.CALLERY_HANGING_SIGN, PearBlocks.STRIPPED_CALLERY_STEM);
        RecipeProvider.offerPlanksRecipe2(exporter, PearBlocks.CALLERY_PLANKS, PearItemTags.CALLERY_STEMS, 4);
        woodFromHollow(exporter, RecipeCategory.BUILDING_BLOCKS, PearBlocks.CALLERY_WOOD, PearBlocks.HOLLOWED_CALLERY_STEM);
        wood(exporter, RecipeCategory.BUILDING_BLOCKS, PearBlocks.CALLERY_WOOD, PearBlocks.CALLERY_STEM);
        wood(exporter, RecipeCategory.BUILDING_BLOCKS, PearBlocks.STRIPPED_CALLERY_WOOD, PearBlocks.STRIPPED_CALLERY_STEM);



        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, PearItems.PEAR_TART)
                .input(PearBlocks.LAMPEAR)
                .input(Items.SUGAR)
                .input(Items.EGG)
                .input(Items.BREAD)
                .criterion(hasItem(PearBlocks.LAMPEAR), conditionsFromItem(PearBlocks.LAMPEAR)).offerTo(exporter);


        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, PearBlocks.COPPER_LAMPEAR)
                .input('L', Items.COPPER_BLOCK)
                .input('P', PearBlocks.LAMPEAR)
                .input('H', Items.HONEYCOMB)
                .pattern("LLL")
                .pattern("LPL")
                .pattern("LHL")
                .criterion(hasItem(PearBlocks.LAMPEAR), conditionsFromItem(PearBlocks.LAMPEAR)).offerTo(exporter);
    }

    public static void makeRecipes(Consumer<RecipeJsonProvider> exporter, Block planks, Block stairs, Block slab, Block fence, Block fenceGate, Block door, Block trapdoor, Block button, Block pressurePlate, ItemConvertible sign){
        createStairsRecipe(exporter, stairs, planks);
        createSlabRecipe(exporter, slab, planks);
        createTrapdoorRecipe(exporter, trapdoor, planks);
        createSignRecipe(exporter, sign, planks);
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, door, 3).input('#', planks).pattern("##").pattern("##").pattern("##").criterion(hasItem(planks), conditionsFromItem(planks)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, fence, 3).input('W', planks).input('#', Items.STICK).pattern("W#W").pattern("W#W").criterion(hasItem(planks), conditionsFromItem(planks)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, fenceGate).input('#', Items.STICK).input('W', planks).pattern("#W#").pattern("#W#").criterion(hasItem(planks), conditionsFromItem(planks)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, pressurePlate).input('#', planks).pattern("##").criterion(hasItem(planks), conditionsFromItem(planks)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, sign, 3).group("sign").input('#', planks).input('X', Items.STICK).pattern("###").pattern("###").pattern(" X ");
        ShapelessRecipeJsonBuilder.create(RecipeCategory.REDSTONE, button, 1).input(planks).criterion(hasItem(planks), conditionsFromItem(planks)).offerTo(exporter);
    }

    public static void createSlabRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible input) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 6).input('#', input).pattern("###").criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
    }


    public static void createStairsRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible input) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 4).input('#', input).pattern("#  ").pattern("## ").pattern("###").criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
    }

    public static void createTrapdoorRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible input) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, output, 2).input('#', input).pattern("###").pattern("###").criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
    }

    public static void createSignRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible input) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, output, 3).group("sign").input('#', input).input('X', Items.STICK).pattern("###").pattern("###").pattern(" X ").criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
    }

    public static void wood(Consumer<RecipeJsonProvider> exporter, RecipeCategory category, ItemConvertible output, ItemConvertible input) {
        ShapedRecipeJsonBuilder.create(category, output, 4).input('#', input).pattern("##").pattern("##").criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
    }
    public static void woodFromHollow(Consumer<RecipeJsonProvider> exporter, RecipeCategory category, ItemConvertible output, ItemConvertible input) {
        ShapedRecipeJsonBuilder.create(category, output, 2).input('#', input).pattern("##").pattern("##").group("bark").criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter, Registries.ITEM.getId(output.asItem()).withSuffixedPath("_from_hollowed"));
    }
}
