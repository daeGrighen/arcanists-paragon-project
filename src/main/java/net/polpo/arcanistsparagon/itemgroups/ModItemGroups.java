package net.polpo.arcanistsparagon.itemgroups;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.polpo.arcanistsparagon.ArcanistsParagon;
import net.polpo.arcanistsparagon.item.ModItems;

public class ModItemGroups {
    public static final ItemGroup ARCANISTS_PARAGON_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(ArcanistsParagon.MOD_ID, "arcane_core"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.arcanist"))
                    .icon(() -> new ItemStack(ModItems.ARCANE_CORE)).entries((displayContext, entries) -> {
                        entries.add(ModItems.ARCANE_CORE);
                    }).build());

    public static void registerItemGroups(){
        ArcanistsParagon.LOGGER.info("Registering Item Groups for "+ ArcanistsParagon.MOD_ID);
    }
}
