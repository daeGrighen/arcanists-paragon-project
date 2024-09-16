package net.polpo.arcanistsparagon.entity.custom;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.polpo.arcanistsparagon.ArcanistsParagon;
import net.polpo.arcanistsparagon.block.ModBlocks;
import net.polpo.arcanistsparagon.entity.ModEntities;
import net.polpo.arcanistsparagon.item.ModItems;
import net.polpo.arcanistsparagon.util.ArcanistsParagonParticleDecoder;
import net.polpo.arcanistsparagon.util.SpherePointGenerator;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.List;

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
    protected void onBlockHit(BlockHitResult blockHitResult) {
        if(!this.getWorld().isClient()){
            BlockPos pos = getBlockPos();
            double x = pos.getX();
            double y = pos.getY();
            double z = pos.getZ();
            int samples = 1000;

            List<Vec3d> points = SpherePointGenerator.generatePointsVec3ds(x, y, z, 3, samples);
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeInt(samples);
            buf.writeInt(ArcanistsParagonParticleDecoder.INK);

            for (Vec3d vec : points){
                buf.writeVec3d(vec);
            }


            ServerPlayerEntity thrower = null;
            if(this.getOwner() instanceof PlayerEntity){
                thrower = (ServerPlayerEntity) this.getOwner();
            }else{
                thrower = (ServerPlayerEntity) this.getWorld().getClosestPlayer(x, y, z, 500, false);
            }

            //index of particle is fucked
            send(thrower, buf);
        }




        this.discard();
        super.onBlockHit(blockHitResult);
    }

    private void send(ServerPlayerEntity user, PacketByteBuf buf){
        ServerPlayNetworking.send((ServerPlayerEntity) user, ArcanistsParagon.PLAY_PARTICLES_PACKET_ID, buf);
    }
}
