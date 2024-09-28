package net.polpo.arcanistsparagon.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class PlacerBlock extends Block {
    public PlacerBlock(Settings settings) {
        super(settings);
    }

    public static final Predicate<Entity> IS_BLOCKITEM = (entity) -> {
        if(entity.getType().equals(EntityType.ITEM)){
            ItemEntity itemEntity = (ItemEntity) entity;
            ItemStack stack = itemEntity.getStack();
            return stack.getItem() instanceof BlockItem;
        }
        return false;
    };

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if(!world.isClient()) {
            if(world.getBlockState(pos.down()).isAir()) {
                ServerWorld serverWorld = (ServerWorld) world;
                Vec3d center = new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
                List<ItemEntity> blockItemsEntities = serverWorld.getEntitiesByType(EntityType.ITEM, Box.of(center, 1.0f, 1.0f, 1.0f), IS_BLOCKITEM);
                ItemStack firstStack = blockItemsEntities.get(0).getStack();
                BlockItem toPlaceItem = (BlockItem) firstStack.getItem();
                BlockState toPlace = toPlaceItem.getBlock().getDefaultState();
                firstStack.decrement(1);
                serverWorld.setBlockState(pos.down(), toPlace);
            }
        }
    }
}
