package net.polpo.arcanistsparagon.util;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricLootTableProvider;
import net.fabricmc.fabric.api.loot.v2.FabricLootTableBuilder;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;
import net.polpo.arcanistsparagon.item.ModItems;

public class ModLootTableModifiers {
    private static final Identifier SNIFFER_FIND_ID = new Identifier("minecraft", "gameplay/sniffer_digging");
    private static final Identifier ZOGLIN_ID = new Identifier("minecraft", "entities/zoglin");
    //private static final Identifier PIGLIN_BARTER_ID = new Identifier("minecraft", "gameplay/piglin_bartering");

    public static void modifyLootTables(){
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if(ZOGLIN_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.05f))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build())
                        .with(ItemEntry.builder(Items.BONE_BLOCK));

                tableBuilder.pool(poolBuilder);
            }

            if (SNIFFER_FIND_ID.equals(id)) {
                tableBuilder.modifyPools(pool -> {
                    pool.with(ItemEntry.builder(ModItems.BLAZING_COAL)
                            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
                            .conditionally(RandomChanceLootCondition.builder(1f)));
                });
            }

        });



    }
}
