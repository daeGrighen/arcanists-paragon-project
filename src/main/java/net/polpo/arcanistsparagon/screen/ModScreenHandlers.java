package net.polpo.arcanistsparagon.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.polpo.arcanistsparagon.ArcanistsParagon;


public class ModScreenHandlers {
    public static final ScreenHandlerType<RitualTableScreenHandler> RITUAL_TABLE_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(ArcanistsParagon.MOD_ID, "ritual_table"),
                    new ExtendedScreenHandlerType<>(RitualTableScreenHandler::new));

    public static void registerScreenHandlers(){
        ArcanistsParagon.LOGGER.info("Registering Screen Handlers for " + ArcanistsParagon.MOD_ID);
    }
}
