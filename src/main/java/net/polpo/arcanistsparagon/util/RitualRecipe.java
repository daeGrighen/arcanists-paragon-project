package net.polpo.arcanistsparagon.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.polpo.arcanistsparagon.ArcanistsParagon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RitualRecipe {
    private List<ItemStack> ingredients;
    private BlockState catalyst;
    private ItemStack result;

    public RitualRecipe(List<ItemStack> ingredients, BlockState catalyst, ItemStack result) {
        this.ingredients = ingredients;
        this.catalyst = catalyst;
        this.result = result;
    }

    // Getters and setters
    public List<ItemStack> getIngredients() {
        return ingredients;
    }

    private void setIngredients(List<ItemStack> ingredients) {
        this.ingredients = ingredients;
    }

    public BlockState getCatalyst() {
        return catalyst;
    }

    private void setCatalyst(BlockState catalyst) {
        this.catalyst = catalyst;
    }

    public ItemStack getResult() {
        return result;
    }

    private void setResult(ItemStack result) {
        this.result = result;
    }

    public boolean recipeMatches(List<ItemStack> providedItems, BlockState providedCatalyst){
        boolean isCatalystGood = (providedCatalyst == getCatalyst());
        boolean isRecipeGood = containsAll(getIngredients(), providedItems);
        ArcanistsParagon.LOGGER.info("recipe match:" + isRecipeGood + isCatalystGood);
        ArcanistsParagon.LOGGER.info("given: " + providedItems);
        ArcanistsParagon.LOGGER.info("recipe: " + getIngredients());
        return (isRecipeGood && isCatalystGood);
    }


    /*
    this checks if all elements of list2 are also in list1
     */
    private  boolean containsAll(List<ItemStack> list1, List<ItemStack> list2) {
        List<ItemStack> tempList = new ArrayList<>(list1);

        for (ItemStack item : list2) {
            boolean found = false;
            for (int i = 0; i < tempList.size(); i++) {
                if (ItemStack.areItemsEqual(tempList.get(i), item.copyWithCount(1))) {
                    tempList.remove(i);
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }

        return true;
    }

}
