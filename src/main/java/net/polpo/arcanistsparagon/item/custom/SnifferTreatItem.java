package net.polpo.arcanistsparagon.item.custom;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.passive.SnifferEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Iterator;
import java.util.List;

public class SnifferTreatItem extends Item {
    private static final TrackedData<SnifferEntity.State> STATE = null;
    public SnifferTreatItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if(!user.getWorld().isClient()) {
            if (entity instanceof SnifferEntity) {
                SnifferEntity sniffer = (SnifferEntity) entity;
                sniffer.addVelocity(0.0, 0.1, 0.0);
                ServerWorld serverWorld = (ServerWorld) user.getEntityWorld();
                Vec3d blockPos = sniffer.getPos();

                LootTable lootTable = serverWorld.getServer().getLootManager().getLootTable(LootTables.SNIFFER_DIGGING_GAMEPLAY);
                LootContextParameterSet lootContextParameterSet = (new LootContextParameterSet.Builder(serverWorld)).add(LootContextParameters.ORIGIN, blockPos).add(LootContextParameters.THIS_ENTITY, sniffer).build(LootContextTypes.GIFT);
                List<ItemStack> list = lootTable.generateLoot(lootContextParameterSet);

                Iterator var6 = list.iterator();

                while (var6.hasNext()) {
                    ItemStack itemStack = (ItemStack) var6.next();
                    ItemEntity itemEntity = new ItemEntity(serverWorld, (double) blockPos.getX(), (double) blockPos.getY(), (double) blockPos.getZ(), itemStack);
                    itemEntity.setToDefaultPickupDelay();
                    serverWorld.spawnEntity(itemEntity);
                }

                sniffer.playSound(SoundEvents.ENTITY_SNIFFER_DROP_SEED, 1.0F, 1.0F);
                user.playSound(SoundEvents.ENTITY_SNIFFER_DROP_SEED, 1.0f, 1.2f);
                stack.decrement(1);
            }
        }
        return ActionResult.SUCCESS;
    }
}
