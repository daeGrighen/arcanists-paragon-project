package net.polpo.arcanistsparagon.datagen;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import net.polpo.arcanistsparagon.block.ModBlocks;
import net.polpo.arcanistsparagon.block.custom.RisingbulbCropBlock;
import net.polpo.arcanistsparagon.item.ModItems;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.ASPHODITE_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.ENTROPIC_QUARTZ_1);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.ENTROPIC_QUARTZ_2);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.ENTROPIC_QUARTZ_3);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.ENTROPIC_QUARTZ_4);

        blockStateModelGenerator.registerSimpleState(ModBlocks.RITUAL_TABLE);
        blockStateModelGenerator.registerSimpleState(ModBlocks.RITUAL_PEDESTAL);

        blockStateModelGenerator.registerFlowerPotPlant(ModBlocks.SCIONBLOOM_BLOSSOM, ModBlocks.POTTED_SCIONBLOOM_BLOSSOM, BlockStateModelGenerator.TintType.NOT_TINTED);
        blockStateModelGenerator.registerFlowerPotPlant(ModBlocks.WEANING_IMPATIENS, ModBlocks.POTTED_WEANING_IMPATIENS, BlockStateModelGenerator.TintType.NOT_TINTED);
        //blockStateModelGenerator.registerSimpleState(ModBlocks.RITUAL_CORE_BASE_BLOCK);
        blockStateModelGenerator.registerCrop(ModBlocks.RISINGBULB_CROP, RisingbulbCropBlock.AGE, 0, 1, 2, 3, 4, 5, 6, 7, 8);

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.BLAZING_COAL, Models.GENERATED);
        itemModelGenerator.register(ModItems.ARCANE_CORE, Models.GENERATED);
        itemModelGenerator.register(ModItems.ASPHODITE_CHUNK, Models.GENERATED);
        itemModelGenerator.register(ModItems.DIVINING_ROD, Models.GENERATED);
        itemModelGenerator.register(ModItems.INKY_CORE, Models.GENERATED);
        itemModelGenerator.register(ModItems.SNIFFER_TREAT, Models.GENERATED);
        itemModelGenerator.register(ModItems.RISINGBULB, Models.GENERATED);
        itemModelGenerator.register(ModItems.ENTROPIC_STAFF, Models.GENERATED);
        //itemModelGenerator.register(ModItems.SCIONBLOOM, Models.GENERATED);

    }

}
