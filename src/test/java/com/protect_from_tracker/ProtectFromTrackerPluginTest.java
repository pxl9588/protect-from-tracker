package com.protect_from_tracker;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class ProtectFromTrackerPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(ProtectFromTrackerPlugin.class);
		RuneLite.main(args);
	}
}