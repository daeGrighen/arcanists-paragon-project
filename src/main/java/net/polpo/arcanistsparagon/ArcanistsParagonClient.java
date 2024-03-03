package net.polpo.arcanistsparagon;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.polpo.arcanistsparagon.block.entity.ModBlockEntities;
import net.polpo.arcanistsparagon.block.entity.renderer.PedestalBlockEntityRenderer;
import net.polpo.arcanistsparagon.screen.ModScreenHandlers;
import net.polpo.arcanistsparagon.screen.RitualTableScreen;

public class ArcanistsParagonClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        HandledScreens.register(ModScreenHandlers.RITUAL_TABLE_SCREEN_HANDLER, RitualTableScreen::new);

        BlockEntityRendererFactories.register(ModBlockEntities.RITUAL_PEDESTAL_BLOCK_ENTITY, PedestalBlockEntityRenderer::new);
    }
}
