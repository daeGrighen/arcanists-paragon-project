package net.polpo.arcanistsparagon.geckolib;

import net.minecraft.util.Identifier;
import net.polpo.arcanistsparagon.block.custom.RitualCoreBlock;
import net.polpo.arcanistsparagon.block.entity.RitualCoreBlockEntity;
import software.bernie.geckolib.GeckoLib;
import software.bernie.geckolib.model.GeoModel;

public class RitualCoreGeoModel extends GeoModel<RitualCoreBlockEntity> {
    private final Identifier modelResource = new Identifier(GeckoLib.MOD_ID, "geo/ritual_core.geo.json");
    private final Identifier textureResource = new Identifier(GeckoLib.MOD_ID, "textures/block/geckolib/texture");
    private final Identifier animationResource = new Identifier(GeckoLib.MOD_ID, "animations/model.animation.json");

    @Override
    public Identifier getModelResource(RitualCoreBlockEntity animatable) {
        return modelResource;
    }

    @Override
    public Identifier getTextureResource(RitualCoreBlockEntity animatable) {
        return textureResource;
    }

    @Override
    public Identifier getAnimationResource(RitualCoreBlockEntity animatable) {
        return animationResource;
    }
}
