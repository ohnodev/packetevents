package com.github.retrooper.packetevents.protocol.component.builtin.item;

import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import org.jspecify.annotations.NullMarked;

import java.util.Objects;

/**
 * @versions 1.21.11+
 */
@NullMarked
public class ItemMinimumAttackCharge {

    private float value;

    public ItemMinimumAttackCharge(float value) {
        this.value = value;
    }

    public static ItemMinimumAttackCharge read(PacketWrapper<?> wrapper) {
        return new ItemMinimumAttackCharge(wrapper.readFloat());
    }

    public static void write(PacketWrapper<?> wrapper, ItemMinimumAttackCharge component) {
        wrapper.writeFloat(component.value);
    }

    public float getValue() {
        return this.value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;
        ItemMinimumAttackCharge that = (ItemMinimumAttackCharge) obj;
        return Float.compare(that.value, this.value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.value);
    }
}
