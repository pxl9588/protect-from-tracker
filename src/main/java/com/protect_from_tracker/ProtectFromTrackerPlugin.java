package com.protect_from_tracker;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.HitsplatID;
import net.runelite.api.Player;
import net.runelite.api.Prayer;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import java.util.List;

@Slf4j
@PluginDescriptor(
	name = "Protect From Tracker"
)
public class ProtectFromTrackerPlugin extends Plugin
{
	private static final List<Prayer> PROTECT_FROM_PRAYERS = List.of(Prayer.PROTECT_FROM_MAGIC, Prayer.PROTECT_FROM_MISSILES, Prayer.PROTECT_FROM_MELEE);
	@Inject
	private Client client;

	@Inject
	private ProtectFromTrackerConfig config;

	@Override
	protected void startUp() throws Exception
	{
		log.info("Protect From Tracker started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Protect From Tracker stopped!");
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Example says " + config.greeting(), null);
		}
	}

	@Provides
	ProtectFromTrackerConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ProtectFromTrackerConfig.class);
	}

	private boolean protectFromPrayerActive()
	{
		for (Prayer pray : Prayer.values())//Check if any prayers are active
		{
			if (client.isPrayerActive(pray))
			{
				return true;
			}
		}

		return false;
	}

	@Subscribe
	public void onGameTick(GameTick tick)
	{
		for (Prayer prayer : PROTECT_FROM_PRAYERS) {
			if(client.isPrayerActive(prayer)) {
				log.info("Prayer " + prayer + " is active.");
			}
		}
	}
}
