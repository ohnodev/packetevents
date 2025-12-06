package com.github.retrooper.packetevents.protocol.world.attributes;

import com.github.retrooper.packetevents.protocol.util.NbtCodec;
import org.jspecify.annotations.NullMarked;

/**
 * @versions 1.21.11+
 */
@NullMarked
public interface AttributeModifier<T, A> {

    NbtCodec<A> argumentCodec(EnvironmentAttribute<T> attribute);
}
