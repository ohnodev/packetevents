package com.github.retrooper.packetevents.util.easing;

import org.jspecify.annotations.NullMarked;

// based on Mojang's CubicCurve
@NullMarked
public final class CubicCurve {

    private final float a;
    private final float b;
    private final float c;

    public CubicCurve(float a, float b, float c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public float calcSample(float t) {
        return ((this.a * t + this.b) * t + this.c) * t;
    }

    public float calcSampleGradient(float t) {
        return (3f * this.a * t + 2f * this.b) * t + this.c;
    }

    public float getA() {
        return this.a;
    }

    public float getB() {
        return this.b;
    }

    public float getC() {
        return this.c;
    }
}
