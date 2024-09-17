package net.polpo.arcanistsparagon.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class ScionbloomItem extends Item {
    public ScionbloomItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if(!user.getWorld().isClient()){
            if(entity.isBaby()){
                MobEntity mob = (MobEntity) entity;
                mob.setBaby(false);
                mob.playAmbientSound();
                user.getWorld().playSound(entity, entity.getBlockPos(), SoundEvents.BLOCK_SCULK_SPREAD, SoundCategory.PLAYERS, 1.0f, 2.0f);
                stack.decrement(1);
            }
        }

        return ActionResult.SUCCESS;
    }
}
