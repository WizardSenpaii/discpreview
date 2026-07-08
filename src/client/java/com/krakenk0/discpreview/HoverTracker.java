package com.krakenk0.discpreview;

import com.krakenk0.discpreview.mixin.AbstractContainerScreenMixin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.JukeboxPlayable;

public class HoverTracker {

    private static ItemStack lastHovered = ItemStack.EMPTY;
    private static ItemStack currentDisc = ItemStack.EMPTY;

    private static int hoverTicks = 0;
    private static ItemStack pendingDisc = ItemStack.EMPTY;

    public static void tick() {

        Minecraft client = Minecraft.getInstance();

        if (!(client.screen instanceof AbstractContainerScreen<?> screen)) {

            pendingDisc = ItemStack.EMPTY;
            hoverTicks = 0;

            if (!currentDisc.isEmpty()) {
                currentDisc = ItemStack.EMPTY;
                SoundController.stop();
            }

            lastHovered = ItemStack.EMPTY;
            return;
        }

        Slot slot =

                ((AbstractContainerScreenMixin) screen).getHoveredSlot();

        if (slot == null) {

            pendingDisc = ItemStack.EMPTY;
            hoverTicks = 0;

            if (!currentDisc.isEmpty()) {
                currentDisc = ItemStack.EMPTY;
                SoundController.stop();
            }

            return;
        }

        ItemStack stack = slot.getItem();

        if (stack.isEmpty()) {

            pendingDisc = ItemStack.EMPTY;
            hoverTicks = 0;

            if (!currentDisc.isEmpty()) {
                currentDisc = ItemStack.EMPTY;
                SoundController.stop();
            }

            return;
        }

        if (stack.has(DataComponents.JUKEBOX_PLAYABLE)) {

            if (!ItemStack.isSameItemSameComponents(stack, pendingDisc)) {

                pendingDisc = stack.copy();
                hoverTicks = 0;

            } else {

                hoverTicks++;

            }


            // 6 ticks = 0.3 seconds
            if (hoverTicks >= 12 &&
                    !ItemStack.isSameItemSameComponents(stack, currentDisc)) {


                currentDisc = stack.copy();

                JukeboxPlayable playable =
                        stack.get(DataComponents.JUKEBOX_PLAYABLE);


                if (playable != null && client.level != null) {

                    playable.song()
                            .unwrap(client.level.registryAccess())
                            .ifPresent(holder -> {

                                SoundController.play(
                                        holder.value()
                                                .soundEvent()
                                                .value()
                                );

                            });
                }
            }


        } else {

            pendingDisc = ItemStack.EMPTY;
            hoverTicks = 0;


            if (!currentDisc.isEmpty()) {

                currentDisc = ItemStack.EMPTY;
                SoundController.stop();

            }
        }
    }
}