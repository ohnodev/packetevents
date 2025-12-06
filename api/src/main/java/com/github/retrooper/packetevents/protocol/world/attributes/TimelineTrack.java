package com.github.retrooper.packetevents.protocol.world.attributes;

import com.github.retrooper.packetevents.protocol.util.NbtCodec;
import com.github.retrooper.packetevents.protocol.util.NbtMapCodec;
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

//    public static <T>NbtCodec<>

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
