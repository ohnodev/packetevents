package com.github.retrooper.packetevents.protocol.component.builtin.item;

import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import org.jspecify.annotations.NullMarked;

import java.util.Objects;

/**
 * @versions 1.21.11+
 */
@NullMarked
public class ItemSwingAnimation {

    private static final Type[] TYPES = Type.values();

    private Type type;
    private int duration;

    public ItemSwingAnimation(Type type, int duration) {
        this.type = type;
        this.duration = duration;
    }

    public static ItemSwingAnimation read(PacketWrapper<?> wrapper) {
        Type type = wrapper.readEnum(TYPES, Type.NONE);
        int duration = wrapper.readVarInt();
        return new ItemSwingAnimation(type, duration);
    }

    public static void write(PacketWrapper<?> wrapper, ItemSwingAnimation component) {
        wrapper.writeEnum(component.type);
        wrapper.writeVarInt(component.duration);
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;
        ItemSwingAnimation that = (ItemSwingAnimation) obj;
        if (this.duration != that.duration) return false;
        return this.type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.type, this.duration);
    }

    public enum Type {
        NONE,
        WHACK,
        STAB,
    }
}
