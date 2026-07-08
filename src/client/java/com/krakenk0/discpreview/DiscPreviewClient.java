package com.krakenk0.discpreview;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class DiscPreviewClient implements ClientModInitializer {

	public static final String MOD_ID = "discpreview";

	@Override
	public void onInitializeClient() {

		System.out.println("Disc Preview loaded!");

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			HoverTracker.tick();
		});

	}
}