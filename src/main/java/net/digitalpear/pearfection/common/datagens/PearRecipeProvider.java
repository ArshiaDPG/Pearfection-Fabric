package net.digitalpear.pearfection.common.datagens;

import net.digitalpear.pearfection.init.PearBlocks;
import net.digitalpear.pearfection.init.PearItems;
import net.digitalpear.pearfection.init.tags.PearItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;

import java.util.function.Consumer;

public class PearRecipeProvider extends FabricRecipeProvider {
    public PearRecipeProvider(FabricDataOutput output) {
        super(output);
    }



    @Override
    public void generate(RecipeExporter exporter) {

        makeRecipes(exporter, PearBlocks.CALLERY_PLANKS, PearBlocks.CALLERY_STAIRS, PearBlocks.CALLERY_SLAB, PearBlocks.CALLERY_FENCE, PearBlocks.CALLERY_FENCE_GATE,
                PearBlocks.CALLERY_DOOR, PearBlocks.CALLERY_TRAPDOOR, PearBlocks.CALLERY_BUTTON, PearBlocks.CALLERY_PRESSURE_PLATE, PearItems.CALLERY_SIGN);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, Items.STICK, 2)
                        .input(PearBlocks.CALLERY_TWIG)
                .criterion(hasItem(PearBlocks.CALLERY_TWIG), conditionsFromItem(PearBlocks.CALLERY_TWIG)).offerTo(exporter, Registries.ITEM.getId(Items.STICK).withSuffixedPath("_from_callery_twig"));


        RecipeProvider.offerBoatRecipe(exporter, PearItems.CALLERY_BOAT, PearBlocks.CALLERY_PLANKS);
        RecipeProvider.offerChestBoatRecipe(exporter, PearItems.CALLERY_CHEST_BOAT, PearBlocks.CALLERY_PLANKS);
        RecipeProvider.offerHangingSignRecipe(exporter, PearItems.CALLERY_HANGING_SIGN, PearBlocks.STRIPPED_CALLERY_STEM);
        RecipeProvider.offerPlanksRecipe(exporter, PearBlocks.CALLERY_PLANKS, PearItemTags.CALLERY_STEMS, 4);
        RecipeProvider.offerBarkBlockRecipe(exporter, PearBlocks.CALLERY_WOOD, PearBlocks.CALLERY_STEM);
        RecipeProvider.offerBarkBlockRecipe(exporter, PearBlocks.STRIPPED_CALLERY_WOOD, PearBlocks.STRIPPED_CALLERY_STEM);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, PearItems.PEAR_TART, 2)
                .input(PearBlocks.LAMPEAR)
                .input(Items.SUGAR)
                .input(Items.SNIFFER_EGG)
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

    public static void makeRecipes(RecipeExporter exporter, Block planks, Block stairs, Block slab, Block fence, Block fenceGate, Block door, Block trapdoor, Block button, Block pressurePlate, ItemConvertible sign){

        RecipeProvider.createStairsRecipe(stairs, Ingredient.ofItems(planks)).criterion(hasItem(planks), conditionsFromItem(planks)).offerTo(exporter);
        RecipeProvider.createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, slab, Ingredient.ofItems(planks)).criterion(hasItem(planks), conditionsFromItem(planks)).offerTo(exporter);
        RecipeProvider.createTrapdoorRecipe(trapdoor, Ingredient.ofItems(planks)).criterion(hasItem(planks), conditionsFromItem(planks)).offerTo(exporter);
        RecipeProvider.createDoorRecipe(door, Ingredient.ofItems(planks)).criterion(hasItem(planks), conditionsFromItem(planks)).offerTo(exporter);
        RecipeProvider.createFenceRecipe(fence, Ingredient.ofItems(planks)).criterion(hasItem(planks), conditionsFromItem(planks)).offerTo(exporter);
        RecipeProvider.createFenceGateRecipe(fenceGate, Ingredient.ofItems(planks)).criterion(hasItem(planks), conditionsFromItem(planks)).offerTo(exporter);
        RecipeProvider.createSignRecipe(sign, Ingredient.ofItems(planks)).criterion(hasItem(planks), conditionsFromItem(planks)).offerTo(exporter);
        RecipeProvider.createPressurePlateRecipe(RecipeCategory.REDSTONE, pressurePlate, Ingredient.ofItems(planks)).criterion(hasItem(planks), conditionsFromItem(planks)).offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.REDSTONE, button, 1).input(planks).criterion(hasItem(planks), conditionsFromItem(planks)).offerTo(exporter);
    }
}
