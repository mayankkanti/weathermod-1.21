package net.bytexo.weathermod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeatherMod implements ClientModInitializer {
	public static final String MOD_ID = "weatherstatus";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override

	public void onInitializeClient() {
		HudRenderer.init();
		LOGGER.info("Weather Status Mod Initialized!");
	}
}