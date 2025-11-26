/*
 * This file is part of packetevents - https://github.com/retrooper/packetevents
 * Copyright (C) 2025 retrooper and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.retrooper.packetevents.protocol.world.attributes;

import com.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import com.github.retrooper.packetevents.protocol.nbt.NBT;
import com.github.retrooper.packetevents.protocol.util.NbtCodec;
import com.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.UnknownNullability;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class StaticEnvironmentAttribute<T> extends AbstractMappedEntity implements EnvironmentAttribute<T> {

    private final NbtCodec<T> codec;
    private final @UnknownNullability T defaultValue;

    @ApiStatus.Internal
    public StaticEnvironmentAttribute(@Nullable TypesBuilderData data, NbtCodec<T> codec, @UnknownNullability T defaultValue) {
        super(data);
        this.codec = codec;
        this.defaultValue = defaultValue;
    }

    @Override
    public T getDefaultValue() {
        if (this.defaultValue == null) {
            throw new UnsupportedOperationException();
        }
        return this.defaultValue;
    }

    @Override
    public T decode(NBT nbt, PacketWrapper<?> wrapper) {
        return this.codec.decode(nbt, wrapper);
    }

    @Override
    public NBT encode(PacketWrapper<?> wrapper, T value) {
        return this.codec.encode(wrapper, value);
    }
}
