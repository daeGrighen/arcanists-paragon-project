package net.polpo.arcanistsparagon.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.polpo.arcanistsparagon.ArcanistsParagon;

public class RitualTableScreen extends HandledScreen<RitualTableScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(ArcanistsParagon.MOD_ID, "textures/gui/ritual_table_gui.png");
    public RitualTableScreen(RitualTableScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        titleY = 1000;
        playerInventoryTitleY = 1000;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) /2;
        int y = (height - backgroundHeight) /2;

        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
         //https://www.youtube.com/watch?v=Y4dK9ETdZCQ&list=PLKGarocXCE1EO43Dlf5JGh7Yk-kRAXUEJ&index=31 25:37

    }
}
