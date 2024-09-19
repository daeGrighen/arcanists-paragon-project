package net.polpo.arcanistsparagon.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.polpo.arcanistsparagon.ArcanistsParagon;
import net.polpo.arcanistsparagon.block.custom.*;

public class ModBlocks {

    public static final Block ASPHODITE_ORE = registerBlock("asphodite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(2,5),
                    FabricBlockSettings.copyOf(Blocks.END_STONE).strength(2f).slipperiness(1.5f).sounds(BlockSoundGroup.AMETHYST_BLOCK)));

    public static final Block RITUAL_TABLE = registerBlock("ritual_table",
            new RitualTableBlock(FabricBlockSettings.copyOf(Blocks.DIORITE).nonOpaque()));

    public static final Block RITUAL_PEDESTAL = registerBlock("ritual_pedestal",
            new RitualPedestalBlock(FabricBlockSettings.copyOf(Blocks.BLACKSTONE).nonOpaque()));

    public static final Block RITUAL_CORE = registerBlock("ritual_core",
            new RitualCoreBlock(FabricBlockSettings.copyOf(Blocks.BLACKSTONE).nonOpaque()));

    public static final Block RITUAL_CORE_BASE_BLOCK = registerBlock("ritual_core_base",
            new RitualCoreBaseBlock(FabricBlockSettings.copyOf(Blocks.BLACKSTONE).nonOpaque()));

    public static final Block ENTROPIC_QUARTZ_1 = registerBlock("entropic_quartz_1",
            new Block(FabricBlockSettings.copyOf(Blocks.QUARTZ_BLOCK)));
    public static final Block ENTROPIC_QUARTZ_2 = registerBlock("entropic_quartz_2",
            new Block(FabricBlockSettings.copyOf(Blocks.QUARTZ_BLOCK)));
    public static final Block ENTROPIC_QUARTZ_3 = registerBlock("entropic_quartz_3",
            new Block(FabricBlockSettings.copyOf(Blocks.QUARTZ_BLOCK)));
    public static final Block ENTROPIC_QUARTZ_4 = registerBlock("entropic_quartz_4",
            new Block(FabricBlockSettings.copyOf(Blocks.QUARTZ_BLOCK)));




    public static final Block RISINGBULB_CROP = Registry.register(Registries.BLOCK, new Identifier(ArcanistsParagon.MOD_ID,"risingbulb_crop"),
            new RisingbulbCropBlock(FabricBlockSettings.copyOf(Blocks.WHEAT)));

    public static final Block SCIONBLOOM_BLOSSOM = registerBlock("scionbloom_blossom",
            new ScionbloomBlossomBlock(StatusEffects.UNLUCK, 10,
                    FabricBlockSettings.copyOf(Blocks.ALLIUM).nonOpaque().notSolid()));

    public static final Block POTTED_SCIONBLOOM_BLOSSOM = Registry.register(Registries.BLOCK, new Identifier(ArcanistsParagon.MOD_ID, "potted_scionbloom_blossom"),
            new FlowerPotBlock(SCIONBLOOM_BLOSSOM, FabricBlockSettings.copyOf(Blocks.POTTED_ALLIUM).nonOpaque()));

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
