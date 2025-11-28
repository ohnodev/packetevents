package com.github.retrooper.packetevents.protocol.world.attributes;

import org.jspecify.annotations.NullMarked;

import java.util.List;

/**
 * @versions 1.21.11+
 */
@NullMarked
public class KeyframeTrack<T> {

    private final List<Keyframe<T>> keyframes;
    private final EasingType easingType;

    public KeyframeTrack(List<Keyframe<T>> keyframes, EasingType easingType) {
        this.keyframes = keyframes;
        this.easingType = easingType;
    }

    // TODO

    public List<Keyframe<T>> getKeyframes() {
        return this.keyframes;
    }

    public EasingType getEasingType() {
        return this.easingType;
    }
}
