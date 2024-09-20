package net.polpo.arcanistsparagon.util;

import com.ibm.icu.impl.InvalidFormatException;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.polpo.arcanistsparagon.ArcanistsParagon;

public class ModTags {

    public static class Blocks{
        public static final TagKey<Block> DIVINER_ROD_FINDABLE_BLOCKS =
                createTag("diviner_rod_findable_blocks");

        public static final TagKey<Block> LOW_ENTROPY_BLOCKS = createTag("low_entropy_blocks");
        public static final TagKey<Block> HIGH_ENTROPY_BLOCKS = createTag("high_entropy_blocks");
        public static final TagKey<Block> ENTROPY_STORAGE_BLOCKS = createTag("entropy_storage_blocks");
        public static final TagKey<Block> ENTROPIC_QUARTZ_BLOCKS = createTag("entropic_quartz_blocks");


        private static TagKey<Block> createTag(String name){
            return TagKey.of(RegistryKeys.BLOCK, new Identifier(ArcanistsParagon.MOD_ID, name));
        }

    }

    public static class Items{
        private static TagKey<Item> createTag(String name){
            return TagKey.of(RegistryKeys.ITEM, new Identifier(ArcanistsParagon.MOD_ID, name));
        }

    }
}
