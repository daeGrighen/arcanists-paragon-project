package net.polpo.arcanistsparagon.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.data.DataWriter;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.CopyNbtLootFunction;
import net.minecraft.loot.function.CopyStateFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.nbt.ContextLootNbtProvider;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import net.polpo.arcanistsparagon.block.ModBlocks;
import net.polpo.arcanistsparagon.block.custom.EntropyCellBlock;
import net.polpo.arcanistsparagon.block.custom.RisingbulbCropBlock;
import net.polpo.arcanistsparagon.block.custom.ScionbloomBlossomBlock;
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

        addDrop(ModBlocks.WEANING_IMPATIENS);
        addDrop(ModBlocks.SCIONBLOOM_BLOSSOM);
        addPottedPlantDrops(ModBlocks.POTTED_SCIONBLOOM_BLOSSOM);
        addPottedPlantDrops(ModBlocks.POTTED_WEANING_IMPATIENS);


        addDrop(ModBlocks.ENTROPY_CELL, dropWithBlockStateEntropyCell(ModBlocks.ENTROPY_CELL));

        /*BlockStatePropertyLootCondition.Builder scionbloomBuilder = BlockStatePropertyLootCondition.builder(ModBlocks.SCIONBLOOM_BLOSSOM).properties(StatePredicate.Builder.create()
                .exactMatch(ScionbloomBlossomBlock.CHARGES, 0));
        addDrop(ModBlocks.SCIONBLOOM_BLOSSOM, bStateDrops(ModBlocks.SCIONBLOOM_BLOSSOM, scionbloomBuilder));*/

        BlockStatePropertyLootCondition.Builder risingbulbBuilder = BlockStatePropertyLootCondition.builder(ModBlocks.RISINGBULB_CROP).properties(StatePredicate.Builder.create()
                .exactMatch(RisingbulbCropBlock.AGE, 8));
        addDrop(ModBlocks.RISINGBULB_CROP, cropDrops(ModBlocks.RISINGBULB_CROP, ModItems.RISINGBULB, ModItems.RISINGBULB_CLOVE, risingbulbBuilder));
    }

    public LootTable.Builder multipleDropOreBlocks(Block drop, Item item, float min, float max) {
        return BlockLootTableGenerator.dropsWithSilkTouch(drop, (LootPoolEntry.Builder)this.applyExplosionDecay(drop, ((LeafEntry.Builder) ItemEntry.builder(item).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(min, max)))).apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))));
    }

    public LootTable.Builder bStateDrops(Block block, LootCondition.Builder condition) {
        return (LootTable.Builder)this.applyExplosionDecay(block, LootTable.builder().pool(LootPool.builder().with(ItemEntry.builder(block).conditionally(condition))));
    }

    public static LootTable.Builder dropWithBlockStateEntropyCell(Block drop) {
        return LootTable.builder().pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).with(ItemEntry.builder(drop).apply(CopyStateFunction.builder(drop).addProperty(EntropyCellBlock.CHARGES))));
    }

}
