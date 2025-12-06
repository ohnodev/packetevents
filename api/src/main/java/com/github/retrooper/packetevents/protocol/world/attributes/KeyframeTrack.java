package com.github.retrooper.packetevents.protocol.world.attributes;

import com.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import com.github.retrooper.packetevents.protocol.util.NbtCodec;
import com.github.retrooper.packetevents.protocol.util.NbtCodecException;
import com.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
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

    public static <T> NbtMapCodec<KeyframeTrack<T>> mapCodec(NbtCodec<T> valueCodec) {
        NbtCodec<List<Keyframe<T>>> keyframesCodec = Keyframe.codec(valueCodec).applyList();
        return new NbtMapCodec<KeyframeTrack<T>>() {
            @Override
            public KeyframeTrack<T> decode(NBTCompound compound, PacketWrapper<?> wrapper) throws NbtCodecException {
                List<Keyframe<T>> keyframes = compound.getOrThrow("keyframes", keyframesCodec, wrapper);
                EasingType easingType = compound.getOr("ease", EasingType.CODEC, EasingType.LINEAR, wrapper);
                return new KeyframeTrack<>(keyframes, easingType);
            }

            @Override
            public void encode(NBTCompound compound, PacketWrapper<?> wrapper, KeyframeTrack<T> value) throws NbtCodecException {
                compound.set("keyframes", value.keyframes, keyframesCodec, wrapper);
                if (value.easingType != EasingType.LINEAR) {
                    compound.set("ease", value.easingType, EasingType.CODEC, wrapper);
                }
            }
        };
    }

    public List<Keyframe<T>> getKeyframes() {
        return this.keyframes;
    }

    public EasingType getEasingType() {
        return this.easingType;
    }
}
