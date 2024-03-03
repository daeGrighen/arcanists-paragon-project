package net.polpo.arcanistsparagon.recipe;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.polpo.arcanistsparagon.ArcanistsParagon;

public class ModRecipes {
    public static void registerRecipes(){
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(ArcanistsParagon.MOD_ID, RitualTableRecipe.Serializer.ID),
                RitualTableRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(ArcanistsParagon.MOD_ID, RitualTableRecipe.Type.ID),
                RitualTableRecipe.Type.INSTANCE);
    }
}
