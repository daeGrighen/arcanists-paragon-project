package net.polpo.arcanistsparagon.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
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

        blockStateModelGenerator.registerSimpleState(ModBlocks.RITUAL_TABLE);
        blockStateModelGenerator.registerSimpleState(ModBlocks.RITUAL_PEDESTAL);
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
        itemModelGenerator.register(ModItems.SCIONBLOOM, Models.GENERATED);

    }
}
