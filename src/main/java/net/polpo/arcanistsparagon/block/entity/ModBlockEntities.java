package net.polpo.arcanistsparagon.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.polpo.arcanistsparagon.ArcanistsParagon;
import net.polpo.arcanistsparagon.block.ModBlocks;

public class ModBlockEntities {
    public static final BlockEntityType<RitualTableBlockEntity> RITUAL_TABLE_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(ArcanistsParagon.MOD_ID, "ritual_table_be"),
                    FabricBlockEntityTypeBuilder.create(RitualTableBlockEntity::new, ModBlocks.RITUAL_TABLE).build());

    public static final BlockEntityType<RitualPedestalBlockEntity> RITUAL_PEDESTAL_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(ArcanistsParagon.MOD_ID, "ritual_pedestal_be"),
                    FabricBlockEntityTypeBuilder.create(RitualPedestalBlockEntity::new, ModBlocks.RITUAL_PEDESTAL).build());


    public static void registerBlockEntities(){
        ArcanistsParagon.LOGGER.info("Registering BlockEntities for "+ ArcanistsParagon.MOD_ID);
    }
}
