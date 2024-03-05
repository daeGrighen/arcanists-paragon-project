package net.polpo.arcanistsparagon.geckolib;

import net.minecraft.util.Identifier;
import net.polpo.arcanistsparagon.block.custom.RitualCoreBlock;
import software.bernie.geckolib.GeckoLib;
import software.bernie.geckolib.model.GeoModel;

public class RitualCoreGeoModel extends GeoModel<RitualCoreBlock> {
    private final Identifier modelResource = new Identifier(GeckoLib.MOD_ID, "geo/ritual_core.json");
    private final Identifier textureResource = new Identifier(GeckoLib.MOD_ID, "textures/block/geckolib/texture");
    private final Identifier animationResource = new Identifier(GeckoLib.MOD_ID, "animations/model.animation.json");
    @Override
    public Identifier getModelResource(RitualCoreBlock animatable) {
        return modelResource;
    }

    @Override
    public Identifier getTextureResource(RitualCoreBlock animatable) {
        return textureResource;
    }

    @Override
    public Identifier getAnimationResource(RitualCoreBlock animatable) {
        return animationResource;
    }
}
