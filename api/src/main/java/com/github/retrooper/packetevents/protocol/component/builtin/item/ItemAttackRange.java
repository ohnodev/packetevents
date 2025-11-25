package com.github.retrooper.packetevents.protocol.component.builtin.item;

import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import org.jspecify.annotations.NullMarked;

import java.util.Objects;

/**
 * @versions 1.21.11+
 */
@NullMarked
public class ItemAttackRange {

    private float minRange;
    private float maxRange;
    private float hitboxMargin;
    private float mobFactor;

    public ItemAttackRange(float minRange, float maxRange, float hitboxMargin, float mobFactor) {
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.hitboxMargin = hitboxMargin;
        this.mobFactor = mobFactor;
    }

    public static ItemAttackRange read(PacketWrapper<?> wrapper) {
        float minRange = wrapper.readFloat();
        float maxRange = wrapper.readFloat();
        float hitboxMargin = wrapper.readFloat();
        float mobFactor = wrapper.readFloat();
        return new ItemAttackRange(minRange, maxRange, hitboxMargin, mobFactor);
    }

    public static void write(PacketWrapper<?> wrapper, ItemAttackRange component) {
        wrapper.writeFloat(component.minRange);
        wrapper.writeFloat(component.maxRange);
        wrapper.writeFloat(component.hitboxMargin);
        wrapper.writeFloat(component.mobFactor);
    }

    public float getMinRange() {
        return this.minRange;
    }

    public void setMinRange(float minRange) {
        this.minRange = minRange;
    }

    public float getMaxRange() {
        return this.maxRange;
    }

    public void setMaxRange(float maxRange) {
        this.maxRange = maxRange;
    }

    public float getHitboxMargin() {
        return this.hitboxMargin;
    }

    public void setHitboxMargin(float hitboxMargin) {
        this.hitboxMargin = hitboxMargin;
    }

    public float getMobFactor() {
        return this.mobFactor;
    }

    public void setMobFactor(float mobFactor) {
        this.mobFactor = mobFactor;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;
        ItemAttackRange that = (ItemAttackRange) obj;
        if (Float.compare(that.minRange, this.minRange) != 0) return false;
        if (Float.compare(that.maxRange, this.maxRange) != 0) return false;
        if (Float.compare(that.hitboxMargin, this.hitboxMargin) != 0) return false;
        return Float.compare(that.mobFactor, this.mobFactor) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.minRange, this.maxRange, this.hitboxMargin, this.mobFactor);
    }
}
