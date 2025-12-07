package com.github.retrooper.packetevents.protocol.world.attributes;

import com.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import com.github.retrooper.packetevents.protocol.util.NbtCodec;
import com.github.retrooper.packetevents.protocol.util.NbtCodecs;
import com.github.retrooper.packetevents.util.Either;
import org.jspecify.annotations.NullMarked;

/**
 * @versions 1.21.11+
 */
@NullMarked
public interface EasingType extends MappedEntity {

    NbtCodec<EasingType> CODEC = NbtCodecs.either(
            NbtCodecs.forRegistry(EasingTypes.getRegistry()),
            CubicBezierEasingType.CODEC
    ).apply(Either::unwrap, type -> type instanceof CubicBezierEasingType
            ? Either.createRight((CubicBezierEasingType) type) : Either.createLeft(type));

    float apply(float x);
}
