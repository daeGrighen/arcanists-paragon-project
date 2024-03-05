package net.polpo.arcanistsparagon.geckolib;

import net.minecraft.util.Identifier;
import net.polpo.arcanistsparagon.ArcanistsParagon;
import net.polpo.arcanistsparagon.block.custom.RitualCoreBlock;
import net.polpo.arcanistsparagon.block.entity.RitualCoreBlockEntity;
import software.bernie.geckolib.GeckoLib;
import software.bernie.geckolib.model.GeoModel;

public class RitualCoreGeoModel extends GeoModel<RitualCoreBlockEntity> {
    private final Identifier modelResource = new Identifier(ArcanistsParagon.MOD_ID, "geo/ritual_core.geo.json");
    private final Identifier textureResource = new Identifier(ArcanistsParagon.MOD_ID, "textures/block/ritual_core.png");
    private final Identifier animationResource = new Identifier(ArcanistsParagon.MOD_ID, "animations/model.animation.json");

    @Override
    public Identifier getModelResource(RitualCoreBlockEntity animatable) {
        return this.modelResource;
    }

    @Override
    public Identifier getTextureResource(RitualCoreBlockEntity animatable) {
        return this.textureResource;
    }

    @Override
    public Identifier getAnimationResource(RitualCoreBlockEntity animatable) {
        return this.animationResource;
    }
}
