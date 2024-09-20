package net.polpo.arcanistsparagon.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.polpo.arcanistsparagon.block.ModBlocks;

import java.util.ArrayList;
import java.util.List;

// Define a simple Pair class to hold key-value pairs


public class ModEntropizingHelper {
    public class Pair<K, V> {
        public final K key;
        public final V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
    public List<Pair<BlockState, BlockState>> blocksAddEntropyList;

    public ModEntropizingHelper() {
        blocksAddEntropyList = new ArrayList<>();
        blocksAddEntropyList.add(new Pair<>(Blocks.QUARTZ_BLOCK.getDefaultState(), ModBlocks.ENTROPIC_QUARTZ_1.getDefaultState()));
        blocksAddEntropyList.add(new Pair<>(ModBlocks.ENTROPIC_QUARTZ_1.getDefaultState(), ModBlocks.ENTROPIC_QUARTZ_2.getDefaultState()));
        blocksAddEntropyList.add(new Pair<>(ModBlocks.ENTROPIC_QUARTZ_2.getDefaultState(), ModBlocks.ENTROPIC_QUARTZ_3.getDefaultState()));
        blocksAddEntropyList.add(new Pair<>(ModBlocks.ENTROPIC_QUARTZ_3.getDefaultState(), ModBlocks.ENTROPIC_QUARTZ_4.getDefaultState()));
    }

    public BlockState entropy_add(BlockState state) {
        for (Pair<BlockState, BlockState> pair : blocksAddEntropyList) {
            if (pair.key.equals(state)) {
                return pair.value;
            }
        }
        return null;
    }

    public BlockState entropy_remove(BlockState state) {
        for (Pair<BlockState, BlockState> pair : blocksAddEntropyList) {
            if (pair.value.equals(state)) {
                return pair.key;
            }
        }
        return null;
    }
}
