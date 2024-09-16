package net.polpo.arcanistsparagon.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.polpo.arcanistsparagon.ArcanistsParagon;
import net.polpo.arcanistsparagon.entity.custom.InkyCoreProjectileEntity;

public class ModEntities {
    public static final EntityType<InkyCoreProjectileEntity> INKY_CORE_PROJECTILE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(ArcanistsParagon.MOD_ID, "inky_core_projectile"),
            FabricEntityTypeBuilder.<InkyCoreProjectileEntity>create(SpawnGroup.MISC, InkyCoreProjectileEntity :: new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f)).build());
}
