package com.github.retrooper.packetevents.protocol.component.builtin.item;

import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import org.jspecify.annotations.NullMarked;

import java.util.Objects;

/**
 * @versions 1.21.11+
 */
@NullMarked
public class ItemUseEffects {

    private boolean canSprint;
    private boolean interactVibrations;
    private boolean speedMultiplier;

    public ItemUseEffects(boolean canSprint, boolean interactVibrations, boolean speedMultiplier) {
        this.canSprint = canSprint;
        this.interactVibrations = interactVibrations;
        this.speedMultiplier = speedMultiplier;
    }

    public static ItemUseEffects read(PacketWrapper<?> wrapper) {
        boolean canSprint = wrapper.readBoolean();
        boolean interactVibrations = wrapper.readBoolean();
        boolean speedMultiplier = wrapper.readBoolean();
        return new ItemUseEffects(canSprint, interactVibrations, speedMultiplier);
    }

    public static void write(PacketWrapper<?> wrapper, ItemUseEffects component) {
        wrapper.writeBoolean(component.canSprint);
        wrapper.writeBoolean(component.interactVibrations);
        wrapper.writeBoolean(component.speedMultiplier);
    }

    public boolean isCanSprint() {
        return this.canSprint;
    }

    public void setCanSprint(boolean canSprint) {
        this.canSprint = canSprint;
    }

    public boolean isInteractVibrations() {
        return this.interactVibrations;
    }

    public void setInteractVibrations(boolean interactVibrations) {
        this.interactVibrations = interactVibrations;
    }

    public boolean isSpeedMultiplier() {
        return this.speedMultiplier;
    }

    public void setSpeedMultiplier(boolean speedMultiplier) {
        this.speedMultiplier = speedMultiplier;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;
        ItemUseEffects that = (ItemUseEffects) obj;
        if (this.canSprint != that.canSprint) return false;
        if (this.interactVibrations != that.interactVibrations) return false;
        return this.speedMultiplier == that.speedMultiplier;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.canSprint, this.interactVibrations, this.speedMultiplier);
    }
}
