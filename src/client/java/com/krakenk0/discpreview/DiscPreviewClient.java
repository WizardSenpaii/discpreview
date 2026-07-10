package com.krakenk0.discpreview;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class DiscPreviewClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		DiscPreviewConfig.load();

		System.out.println("Disc Preview loaded!");

		ClientTickEvents.END_CLIENT_TICK.register(client -> HoverTracker.tick());

	}
}