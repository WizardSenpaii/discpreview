package com.krakenk0.discpreview;

import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvent;

public class SoundController {

    private static FadeSoundInstance currentSound;


    public static void play(SoundEvent sound) {

        stop();

        currentSound = new FadeSoundInstance(sound);

        System.out.println("STARTING FADE SOUND: " + sound);

        Minecraft.getInstance()
                .getSoundManager()
                .play(currentSound);

        System.out.println("SOUND PLAYING");
    }


    public static void stop() {

        if (currentSound != null) {

            currentSound.fadeOut();

        }
    }
}