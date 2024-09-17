package net.polpo.arcanistsparagon.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;
import net.polpo.arcanistsparagon.item.ModItems;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.ARCANE_CORE, 1)
                .pattern("EIE")
                .pattern("IAI")
                .pattern("EIE")
                .input('E', Items.ENDER_PEARL)
                .input('I', Items.IRON_INGOT)
                .input('A', ModItems.ASPHODITE_CHUNK)
                .criterion(hasItem(Items.ENDER_PEARL), conditionsFromItem(Items.ENDER_PEARL))
                .offerTo(exporter, new Identifier("arcane_core"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.RISINGBULB_CLOVE, 2)
                .input(ModItems.RISINGBULB)
                .criterion(FabricRecipeProvider.hasItem(ModItems.RISINGBULB_CLOVE), conditionsFromItem(ModItems.RISINGBULB_CLOVE))
                .criterion(FabricRecipeProvider.hasItem(ModItems.RISINGBULB), FabricRecipeProvider.conditionsFromItem(ModItems.RISINGBULB))
                .offerTo(exporter, new Identifier("risingbulb_clove"));
    }

}
