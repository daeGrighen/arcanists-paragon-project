package net.polpo.arcanistsparagon;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArcanistsParagon implements ModInitializer {
	public static final String MOD_ID = "arcanistsparagon";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	//https://www.youtube.com/watch?v=0Pr_iHlVKsI&list=PLKGarocXCE1EO43Dlf5JGh7Yk-kRAXUEJ&index=1
	//7:50

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
	}
}