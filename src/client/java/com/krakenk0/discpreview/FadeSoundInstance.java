package com.krakenk0.discpreview;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;

public class FadeSoundInstance extends AbstractTickableSoundInstance {

    private static final int FADE_TIME = 15;

    private int fadeTicks = 0;
    private boolean fadingOut = false;


    public FadeSoundInstance(SoundEvent sound) {

        super(
                sound,
                SoundSource.RECORDS,
                RandomSource.create()
        );

        this.volume = 0.0F;
        this.pitch = 1.0F;

        this.looping = true;
        this.delay = 0;

        this.attenuation = SoundInstance.Attenuation.NONE;

        // Keep it attached to the player
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    @Override
    public boolean canStartSilent() {
        return true;
    }


    @Override
    public void tick() {

        System.out.println("TICKING SOUND " + this.volume);

        if (!fadingOut) {

            fadeTicks++;

            this.volume = Math.min(
                    1.0F,
                    (float) fadeTicks / FADE_TIME
            ) * (DiscPreviewConfig.volume / 100.0F);

        } else {

            fadeTicks--;

            this.volume = Math.max(
                    0.0F,
                    (float) fadeTicks / FADE_TIME
            ) * (DiscPreviewConfig.volume / 100.0F);

            if (fadeTicks <= 0) {
                super.stop();
            }
        }
    }


    public void fadeOut() {

        fadingOut = true;
        fadeTicks = FADE_TIME;

    }
}