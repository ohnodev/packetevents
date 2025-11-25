package com.github.retrooper.packetevents.protocol.component.builtin.item;

import com.github.retrooper.packetevents.protocol.mapper.MaybeMappedEntity;
import com.github.retrooper.packetevents.protocol.world.damagetype.DamageType;
import com.github.retrooper.packetevents.protocol.world.damagetype.DamageTypes;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import org.jspecify.annotations.NullMarked;

import java.util.Objects;

/**
 * @versions 1.21.11+
 */
@NullMarked
public class ItemDamageType {

    private MaybeMappedEntity<DamageType> damageType;

    public ItemDamageType(DamageType damageType) {
        this(new MaybeMappedEntity<>(damageType));
    }

    public ItemDamageType(MaybeMappedEntity<DamageType> damageType) {
        this.damageType = damageType;
    }

    public static ItemDamageType read(PacketWrapper<?> wrapper) {
        MaybeMappedEntity<DamageType> damageType = MaybeMappedEntity.read(wrapper,
                DamageTypes.getRegistry(), DamageType::read);
        return new ItemDamageType(damageType);
    }

    public static void write(PacketWrapper<?> wrapper, ItemDamageType component) {
        MaybeMappedEntity.write(wrapper, component.damageType, DamageType::write);
    }

    public MaybeMappedEntity<DamageType> getDamageType() {
        return this.damageType;
    }

    public void setDamageType(DamageType damageType) {
        this.setDamageType(new MaybeMappedEntity<>(damageType));
    }

    public void setDamageType(MaybeMappedEntity<DamageType> damageType) {
        this.damageType = damageType;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;
        ItemDamageType that = (ItemDamageType) obj;
        return this.damageType.equals(that.damageType);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.damageType);
    }
}
