package net.polpo.arcanistsparagon.item;


import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.Item;
import net.polpo.arcanistsparagon.ArcanistsParagon;

public class ModFuelItems {

    public static void registerModFuels(){
        ArcanistsParagon.LOGGER.info("Registering Mod items for "+ ArcanistsParagon.MOD_ID);
        registerFuels(ModItems.BLAZING_COAL, 4000);
    }
    private static void registerFuels(Item item, int burnTicks){
        FuelRegistry.INSTANCE.add(item, burnTicks);
    }
}
