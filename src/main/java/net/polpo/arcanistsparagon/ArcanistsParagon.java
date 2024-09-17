package net.polpo.arcanistsparagon;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.util.Identifier;
import net.polpo.arcanistsparagon.block.ModBlocks;
import net.polpo.arcanistsparagon.block.entity.ModBlockEntities;
import net.polpo.arcanistsparagon.item.ModFuelItems;
import net.polpo.arcanistsparagon.item.ModItems;
import net.polpo.arcanistsparagon.itemgroups.ModItemGroups;
import net.polpo.arcanistsparagon.recipe.ModRecipes;
import net.polpo.arcanistsparagon.screen.ModScreenHandlers;
import net.polpo.arcanistsparagon.util.ModLootTableModifiers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArcanistsParagon implements ModInitializer {
	public static final String MOD_ID = "arcanistsparagon";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Identifier PLAY_PARTICLE_PACKET_ID = Identifier.of(ArcanistsParagon.MOD_ID, "particle");
	public static final Identifier PLAY_PARTICLES_PACKET_ID = Identifier.of(ArcanistsParagon.MOD_ID, "particle");


	@Override
	public void onInitialize() {

		ModItems.RegisterModItems();
		ModItemGroups.registerItemGroups();
		ModBlocks.registerModBlocks();

		ModFuelItems.registerModFuels();

		ModBlockEntities.registerBlockEntities();

		ModScreenHandlers.registerScreenHandlers();

		ModRecipes.registerRecipes();

		ModLootTableModifiers.modifyLootTables();
	}
}