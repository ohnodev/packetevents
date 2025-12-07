package com.github.retrooper.packetevents.protocol.world.attributes.timelines;

import com.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import com.github.retrooper.packetevents.protocol.util.NbtCodec;
import com.github.retrooper.packetevents.protocol.util.NbtCodecException;
import com.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import com.github.retrooper.packetevents.protocol.world.attributes.EnvironmentAttribute;
import com.github.retrooper.packetevents.protocol.world.attributes.modifiers.AttributeModifier;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import org.jspecify.annotations.NullMarked;

/**
 * @versions 1.21.11+
 */
@NullMarked
public class TimelineTrack<T, A> {

    private final AttributeModifier<T, A> modifier;
    private final KeyframeTrack<A> argumentTrack;

    public TimelineTrack(AttributeModifier<T, A> modifier, KeyframeTrack<A> argumentTrack) {
        this.modifier = modifier;
        this.argumentTrack = argumentTrack;
    }

    public static <T> NbtCodec<TimelineTrack<T, ?>> codec(EnvironmentAttribute<T> attribute) {
        NbtCodec<AttributeModifier<T, ?>> modifierCodec = attribute.getType().getModifierCodec();
        return new NbtMapCodec<TimelineTrack<T, ?>>() {
            @Override
            public TimelineTrack<T, ?> decode(NBTCompound compound, PacketWrapper<?> wrapper) throws NbtCodecException {
                AttributeModifier<T, ?> modifier = compound.getOr("modifier", modifierCodec, AttributeModifier.override(), wrapper);
                return TimelineTrack.codec(attribute, modifier).decode(compound, wrapper);
            }

            @Override
            public void encode(NBTCompound compound, PacketWrapper<?> wrapper, TimelineTrack<T, ?> value) throws NbtCodecException {
                this.encode0(compound, wrapper, value);
            }

            private <A> void encode0(NBTCompound compound, PacketWrapper<?> wrapper, TimelineTrack<T, A> value) throws NbtCodecException {
                if (value.modifier != AttributeModifier.override()) {
                    compound.set("modifier", value.modifier, modifierCodec, wrapper);
                }
                TimelineTrack.codec(attribute, value.modifier).encode(compound, wrapper, value);
            }
        }.codec();
    }

    public static <T, A> NbtMapCodec<TimelineTrack<T, A>> codec(EnvironmentAttribute<T> attribute, AttributeModifier<T, A> modifier) {
        return KeyframeTrack.mapCodec(modifier.argumentCodec(attribute))
                .apply(track -> new TimelineTrack<>(modifier, track), TimelineTrack::getArgumentTrack);
    }

    public AttributeModifier<T, A> getModifier() {
        return this.modifier;
    }

    public KeyframeTrack<A> getArgumentTrack() {
        return this.argumentTrack;
    }
}
