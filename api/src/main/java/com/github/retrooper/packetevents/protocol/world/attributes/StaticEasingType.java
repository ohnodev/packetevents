package com.github.retrooper.packetevents.protocol.world.attributes;
// Created by booky10 in packetevents (8:41 PM 07.12.2025)

import com.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import com.github.retrooper.packetevents.util.FloatUnaryOperator;
import com.github.retrooper.packetevents.util.easing.CubicBezierControls;
import com.github.retrooper.packetevents.util.easing.CubicCurve;
import com.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * @versions 1.21.1+
 */
@NullMarked
public class StaticEasingType extends AbstractMappedEntity implements EasingType {

    private final FloatUnaryOperator operator;

    @ApiStatus.Internal
    public StaticEasingType(@Nullable TypesBuilderData data, FloatUnaryOperator operator) {
        super(data);
        this.operator = operator;
    }

    @Override
    public float apply(float x) {
        return this.operator.apply(x);
    }
}
