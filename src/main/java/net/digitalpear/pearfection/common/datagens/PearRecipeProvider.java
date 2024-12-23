package net.digitalpear.pearfection.common.datagens;

import net.digitalpear.pearfection.Pearfection;
import net.digitalpear.pearfection.init.PearBlocks;
import net.digitalpear.pearfection.init.PearItems;
import net.digitalpear.pearfection.init.tags.PearItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.data.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.*;

import java.util.concurrent.CompletableFuture;

public class PearRecipeProvider extends FabricRecipeProvider {


    public PearRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new PearRecipeGenerator(wrapperLookup, recipeExporter);
    }


    @Override
    public String getName() {
        return "recipe";
    }


    public class PearRecipeGenerator extends RecipeGenerator{
        RegistryEntryLookup<Item> lookup = registries.getOrThrow(Registries.ITEM.getKey());

        protected PearRecipeGenerator(RegistryWrapper.WrapperLookup registries, RecipeExporter exporter) {
            super(registries, exporter);
        }

        @Override
        public void generate() {
            PearBlocks.CALLERY.generateRecipes(this, lookup, exporter, PearItemTags.CALLERY_STEMS);

//            makeRecipes(exporter, PearBlocks.CALLERY_PLANKS, PearBlocks.CALLERY_STAIRS, PearBlocks.CALLERY_SLAB, PearBlocks.CALLERY_FENCE, PearBlocks.CALLERY_FENCE_GATE,
//                    PearBlocks.CALLERY_DOOR, PearBlocks.CALLERY_TRAPDOOR, PearBlocks.CALLERY_BUTTON, PearBlocks.CALLERY_PRESSURE_PLATE, PearItems.CALLERY_SIGN);

            ShapelessRecipeJsonBuilder.create(lookup, RecipeCategory.MISC, Items.STICK, 2)
                    .input(PearBlocks.CALLERY_TWIG)
                    .criterion(hasItem(PearBlocks.CALLERY_TWIG), conditionsFromItem(PearBlocks.CALLERY_TWIG)).offerTo(exporter, RegistryKey.of(RegistryKeys.RECIPE, Pearfection.id(Registries.ITEM.getId(Items.STICK).withSuffixedPath("_from_callery_twig").getPath())));


//            RecipeProvider.offerBoatRecipe(exporter, PearItems.CALLERY_BOAT, PearBlocks.CALLERY_PLANKS);
//            RecipeProvider.offerChestBoatRecipe(exporter, PearItems.CALLERY_CHEST_BOAT, PearItems.CALLERY_BOAT);
//            RecipeProvider.offerHangingSignRecipe(exporter, PearItems.CALLERY_HANGING_SIGN, PearBlocks.STRIPPED_CALLERY_STEM);
//            RecipeProvider.offerPlanksRecipe(exporter, PearBlocks.CALLERY_PLANKS, PearItemTags.CALLERY_STEMS, 4);
//            RecipeProvider.offerBarkBlockRecipe(exporter, PearBlocks.CALLERY_WOOD, PearBlocks.CALLERY_STEM);
//            RecipeProvider.offerBarkBlockRecipe(exporter, PearBlocks.STRIPPED_CALLERY_WOOD, PearBlocks.STRIPPED_CALLERY_STEM);


//        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, PearBlocks.CALLERY_PICKETS).pattern("PSP").input('P', PearBlocks.CALLERY_PLANKS).input('S', Items.STICK).criterion(hasItem(PearBlocks.CALLERY_PLANKS), conditionsFromItem(PearBlocks.CALLERY_PLANKS)).offerTo(exporter);

            ShapelessRecipeJsonBuilder.create(lookup, RecipeCategory.FOOD, PearItems.PEAR_TART, 2)
                    .input(PearBlocks.LAMPEAR)
                    .input(Items.SUGAR)
                    .input(Items.SNIFFER_EGG)
                    .input(Items.BREAD)
                    .criterion(hasItem(PearBlocks.LAMPEAR), conditionsFromItem(PearBlocks.LAMPEAR)).offerTo(exporter);


            ShapedRecipeJsonBuilder.create(lookup, RecipeCategory.FOOD, PearBlocks.COPPER_LAMPEAR)
                    .input('L', Items.COPPER_BLOCK)
                    .input('P', PearBlocks.LAMPEAR)
                    .input('H', Items.HONEYCOMB)
                    .pattern("LLL")
                    .pattern("LPL")
                    .pattern("LHL")
                    .criterion(hasItem(PearBlocks.LAMPEAR), conditionsFromItem(PearBlocks.LAMPEAR)).offerTo(exporter);
        }
    }
}
