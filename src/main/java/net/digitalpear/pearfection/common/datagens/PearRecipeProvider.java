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

            ShapelessRecipeJsonBuilder.create(lookup, RecipeCategory.MISC, Items.STICK, 2)
                    .input(PearBlocks.CALLERY_TWIG)
                    .criterion(hasItem(PearBlocks.CALLERY_TWIG), conditionsFromItem(PearBlocks.CALLERY_TWIG)).offerTo(exporter, RegistryKey.of(RegistryKeys.RECIPE, Pearfection.id(Registries.ITEM.getId(Items.STICK).withSuffixedPath("_from_callery_twig").getPath())));

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
