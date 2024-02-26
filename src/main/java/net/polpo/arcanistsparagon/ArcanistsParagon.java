package net.polpo.arcanistsparagon;

import net.fabricmc.api.ModInitializer;

import net.polpo.arcanistsparagon.item.ModItems;
import net.polpo.arcanistsparagon.itemgroups.ModItemGroups;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArcanistsParagon implements ModInitializer {
	public static final String MOD_ID = "arcanistsparagon";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


	@Override
	public void onInitialize() {

		ModItems.RegisterModItems();
		ModItemGroups.registerItemGroups();
	}
}