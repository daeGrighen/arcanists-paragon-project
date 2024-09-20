package net.polpo.arcanistsparagon.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.polpo.arcanistsparagon.ArcanistsParagon;
import net.polpo.arcanistsparagon.block.ModBlocks;
import net.polpo.arcanistsparagon.util.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(ModTags.Blocks.DIVINER_ROD_FINDABLE_BLOCKS)
                .add(ModBlocks.ASPHODITE_ORE)
                .add(Blocks.ANCIENT_DEBRIS)
                .add(Blocks.NETHER_QUARTZ_ORE)
                .forceAddTag(BlockTags.COAL_ORES)
                .forceAddTag(BlockTags.EMERALD_ORES)
                .forceAddTag(BlockTags.REDSTONE_ORES)
                .forceAddTag(BlockTags.LAPIS_ORES)
                .forceAddTag(BlockTags.DIAMOND_ORES)
                .forceAddTag(BlockTags.IRON_ORES)
                .forceAddTag(BlockTags.GOLD_ORES)
                .forceAddTag(BlockTags.COPPER_ORES);

        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.ASPHODITE_ORE)
                .add(ModBlocks.RITUAL_PEDESTAL)
                .add(ModBlocks.RITUAL_TABLE);

        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier("fabric", "needs_tool_level_4")))
                .add(ModBlocks.ASPHODITE_ORE);

        getOrCreateTagBuilder(ModTags.Blocks.LOW_ENTROPY_BLOCKS)
                .add(Blocks.QUARTZ_BLOCK)
                .add(Blocks.SMOOTH_QUARTZ);

        getOrCreateTagBuilder(ModTags.Blocks.HIGH_ENTROPY_BLOCKS)
                .add(ModBlocks.ENTROPIC_QUARTZ_4);

        getOrCreateTagBuilder(ModTags.Blocks.ENTROPIC_QUARTZ_BLOCKS)
                .add(ModBlocks.ENTROPIC_QUARTZ_1)
                .add(ModBlocks.ENTROPIC_QUARTZ_2)
                .add(ModBlocks.ENTROPIC_QUARTZ_3)
                .add(ModBlocks.ENTROPIC_QUARTZ_4);

        getOrCreateTagBuilder(ModTags.Blocks.ENTROPY_STORAGE_BLOCKS)
                .forceAddTag(ModTags.Blocks.LOW_ENTROPY_BLOCKS)
                .forceAddTag(ModTags.Blocks.ENTROPIC_QUARTZ_BLOCKS);

    }
}
