package net.polpo.arcanistsparagon.entity.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import net.polpo.arcanistsparagon.entity.ModEntities;
import net.polpo.arcanistsparagon.item.ModItems;

public class InkyCoreProjectileEntity extends ThrownItemEntity {
    public InkyCoreProjectileEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public InkyCoreProjectileEntity(LivingEntity livingEntity, World world) {
        super(ModEntities.INKY_CORE_PROJECTILE, livingEntity, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.INKY_CORE;
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }


    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        if (entity instanceof LivingEntity){
            LivingEntity entityLiving = (LivingEntity) entity;
            entityLiving.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 80));
        }
        this.discard();
        super.onEntityHit(entityHitResult);
    }

}
