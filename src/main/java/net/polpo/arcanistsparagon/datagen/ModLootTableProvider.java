package net.polpo.arcanistsparagon.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.data.DataWriter;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.util.Identifier;
import net.polpo.arcanistsparagon.block.ModBlocks;
import net.polpo.arcanistsparagon.block.custom.RisingbulbCropBlock;
import net.polpo.arcanistsparagon.item.ModItems;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class ModLootTableProvider extends FabricBlockLootTableProvider {

    public ModLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.ASPHODITE_ORE, multipleDropOreBlocks(ModBlocks.ASPHODITE_ORE, ModItems.ASPHODITE_CHUNK, 1.0f, 3.0f));
        addDrop(ModBlocks.RITUAL_PEDESTAL);
        addDrop(ModBlocks.RITUAL_TABLE);
        addDrop(ModBlocks.RITUAL_CORE);

        BlockStatePropertyLootCondition.Builder risingbulbBuilder = BlockStatePropertyLootCondition.builder(ModBlocks.RISINGBULB_CROP).properties(StatePredicate.Builder.create()
                .exactMatch(RisingbulbCropBlock.AGE, 8));
        addDrop(ModBlocks.RISINGBULB_CROP, cropDrops(ModBlocks.RISINGBULB_CROP, ModItems.RISINGBULB, ModItems.RISINGBULB_CLOVE, risingbulbBuilder));
    }

    public LootTable.Builder multipleDropOreBlocks(Block drop, Item item, float min, float max) {
        return BlockLootTableGenerator.dropsWithSilkTouch(drop, (LootPoolEntry.Builder)this.applyExplosionDecay(drop, ((LeafEntry.Builder) ItemEntry.builder(item).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(min, max)))).apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))));
    }
}
