package com.krakenk0.discpreview;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import net.fabricmc.loader.api.FabricLoader;

public class DiscPreviewConfig {

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private static final Path CONFIG_PATH =
            FabricLoader.getInstance()
                    .getConfigDir()
                    .resolve("discpreview.json");

    public static boolean enabled = true;

    public static int volume = 100;


    public static void load() {
        if (!Files.exists(CONFIG_PATH)) {
            save();
            return;
        }

        try {
            String json = Files.readString(CONFIG_PATH);
            ConfigData data = GSON.fromJson(json, ConfigData.class);

            if (data != null) {
                enabled = data.enabled;

                volume = data.volume;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void save() {
        try {
            ConfigData data = new ConfigData();

            data.enabled = enabled;

            data.volume = volume;

            Files.writeString(
                    CONFIG_PATH,
                    GSON.toJson(data)
            );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static class ConfigData {

        boolean enabled = true;

        int volume = 100;
    }
}