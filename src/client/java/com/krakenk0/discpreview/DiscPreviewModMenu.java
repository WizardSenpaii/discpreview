package com.krakenk0.discpreview;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class DiscPreviewModMenu implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return this::createConfigScreen;
    }

    private Screen createConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.literal("Disc Preview Config"));

        ConfigCategory general = builder.getOrCreateCategory(
                Component.literal("General")
        );

        general.addEntry(
                builder.entryBuilder()
                        .startBooleanToggle(
                                Component.literal("Enabled"),
                                DiscPreviewConfig.enabled
                        )
                        .setDefaultValue(true)
                        .setSaveConsumer(value -> {
                            DiscPreviewConfig.enabled = value;
                            DiscPreviewConfig.save();
                        })
                        .build()
        );
        general.addEntry(
                builder.entryBuilder()
                        .startIntSlider(
                                Component.literal("Volume"),
                                DiscPreviewConfig.volume,
                                0,
                                100
                        )
                        .setDefaultValue(100)
                        .setSaveConsumer(value -> {
                            DiscPreviewConfig.volume = value;
                            DiscPreviewConfig.save();
                        })
                        .build()
        );
        return builder.build();
    }
}