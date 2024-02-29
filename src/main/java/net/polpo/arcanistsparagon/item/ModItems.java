package net.polpo.arcanistsparagon.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.polpo.arcanistsparagon.ArcanistsParagon;

public class ModItems {

    public static final Item ARCANE_CORE = registerItem("arcane_core", new Item(new FabricItemSettings()));
    public static final Item ASPHODITE_CHUNK = registerItem("asphodite_chunk", new Item(new FabricItemSettings()));


    private static void AddItemsToItemGroup(FabricItemGroupEntries entries){
    }
    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, new Identifier(ArcanistsParagon.MOD_ID, name), item);
    }
    public static void RegisterModItems() {
        ArcanistsParagon.LOGGER.info("Registering Mod items for "+ ArcanistsParagon.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::AddItemsToItemGroup);
    }
}
