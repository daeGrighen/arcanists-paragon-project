package net.polpo.arcanistsparagon.block.entity.renderer;

import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.polpo.arcanistsparagon.block.entity.RitualCoreBlockEntity;
import net.polpo.arcanistsparagon.geckolib.RitualCoreGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class RitualCoreEntityRenderer extends GeoBlockRenderer<RitualCoreBlockEntity> {

    public RitualCoreEntityRenderer(BlockEntityRendererFactory.Context context) {
        super(new RitualCoreGeoModel());
    }
}
