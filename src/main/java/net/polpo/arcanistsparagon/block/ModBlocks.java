package net.polpo.arcanistsparagon.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.polpo.arcanistsparagon.ArcanistsParagon;

public class ModBlocks {

    public static final Block ASPHODITE_ORE = registerBlock("asphodite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(2,5),
                    FabricBlockSettings.copyOf(Blocks.END_STONE).strength(2f).slipperiness(2f).sounds(BlockSoundGroup.AMETHYST_BLOCK)));

    private static Block registerBlock(String name, Block block){
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(ArcanistsParagon.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block){

        return Registry.register(Registries.ITEM, new Identifier(ArcanistsParagon.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }


    public static void registerModBlocks(){
        ArcanistsParagon.LOGGER.info("Registering ModBlocks from "+ ArcanistsParagon.MOD_ID);
    }
}
