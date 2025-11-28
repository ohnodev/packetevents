package com.github.retrooper.packetevents.protocol.world.attributes;

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

    // TODO

    public AttributeModifier<T, A> getModifier() {
        return this.modifier;
    }

    public KeyframeTrack<A> getArgumentTrack() {
        return this.argumentTrack;
    }
}
