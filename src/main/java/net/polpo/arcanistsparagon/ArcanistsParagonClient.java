package net.polpo.arcanistsparagon;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.Vec3d;
import net.polpo.arcanistsparagon.block.ModBlocks;
import net.polpo.arcanistsparagon.block.entity.ModBlockEntities;
import net.polpo.arcanistsparagon.block.entity.renderer.PedestalBlockEntityRenderer;
import net.polpo.arcanistsparagon.block.entity.renderer.RitualCoreEntityRenderer;
import net.polpo.arcanistsparagon.entity.ModEntities;
import net.polpo.arcanistsparagon.screen.ModScreenHandlers;
import net.polpo.arcanistsparagon.screen.RitualTableScreen;
import net.polpo.arcanistsparagon.util.ArcanistsParagonParticleDecoder;

public class ArcanistsParagonClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        HandledScreens.register(ModScreenHandlers.RITUAL_TABLE_SCREEN_HANDLER, RitualTableScreen::new);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RISINGBULB_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SCIONBLOOM_BLOSSOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_SCIONBLOOM_BLOSSOM, RenderLayer.getCutout());

        BlockEntityRendererFactories.register(ModBlockEntities.RITUAL_PEDESTAL_BLOCK_ENTITY, PedestalBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.RITUAL_CORE_BLOCK_ENTITY, RitualCoreEntityRenderer::new);


        EntityRendererRegistry.register(ModEntities.INKY_CORE_PROJECTILE, FlyingItemEntityRenderer::new);

        ArcanistsParagonParticleDecoder decoder = new ArcanistsParagonParticleDecoder();



        ClientPlayNetworking.registerGlobalReceiver(ArcanistsParagon.PLAY_PARTICLE_PACKET_ID, (client, handler, buf, responseSender) -> {
            client.execute(() -> {
                Vec3d pos = buf.readVec3d();
                DefaultParticleType particle = decoder.decode(buf.readInt());
                if (particle == null){
                    ArcanistsParagon.LOGGER.info("null particle");
                    return;
                }
                client.world.addParticle(particle, pos.x, pos.y, pos.z, 0.0, 0.0, 0.0);
            });
        });
    }
}
