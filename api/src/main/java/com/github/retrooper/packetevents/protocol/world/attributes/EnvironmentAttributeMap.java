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

import com.github.retrooper.packetevents.protocol.nbt.NBT;
import com.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import com.github.retrooper.packetevents.protocol.util.NbtCodec;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @version 1.21.11++
 */
@NullMarked
public class EnvironmentAttributeMap {

    public static final NbtCodec<EnvironmentAttributeMap> CODEC = new NbtCodec<EnvironmentAttributeMap>() {
        @Override
        public EnvironmentAttributeMap decode(NBT nbt, PacketWrapper<?> wrapper) {
            NBTCompound compound = (NBTCompound) nbt;
            Map<EnvironmentAttribute<?>, Entry> entries = new HashMap<>();
            for (Map.Entry<String, NBT> entry : compound.getTags().entrySet()) {
                EnvironmentAttribute<?> attribute = EnvironmentAttributes.getRegistry().getByNameOrThrow(entry.getKey());
                entries.put(attribute, new Entry(entry.getValue()));
            }
            return new EnvironmentAttributeMap(entries);
        }

        @Override
        public NBT encode(PacketWrapper<?> wrapper, EnvironmentAttributeMap value) {
            NBTCompound compound = new NBTCompound();
            for (Map.Entry<EnvironmentAttribute<?>, Entry> entry : value.entries.entrySet()) {
                String key = entry.getKey().getName().toString();
                compound.setTag(key, entry.getValue().tag);
            }
            return compound;
        }
    };

    public static final EnvironmentAttributeMap EMPTY = new EnvironmentAttributeMap(Collections.emptyMap());

    private final Map<EnvironmentAttribute<?>, Entry> entries;

    private EnvironmentAttributeMap(Map<EnvironmentAttribute<?>, Entry> entries) {
        this.entries = entries;
    }

    public static EnvironmentAttributeMap create() {
        return new EnvironmentAttributeMap(new HashMap<>());
    }

    public EnvironmentAttributeMap copyImmutable() {
        Map<EnvironmentAttribute<?>, Entry> entries = new HashMap<>(this.entries);
        return new EnvironmentAttributeMap(Collections.unmodifiableMap(entries));
    }

    public EnvironmentAttributeMap copyMutable() {
        return new EnvironmentAttributeMap(new HashMap<>(this.entries));
    }

    public <T> @Nullable Entry get(EnvironmentAttribute<T> attribute) {
        return this.entries.get(attribute);
    }

    public boolean contains(EnvironmentAttribute<?> attribute) {
        return this.entries.containsKey(attribute);
    }

    public Set<EnvironmentAttribute<?>> keySet() {
        return this.entries.keySet();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof EnvironmentAttributeMap)) return false;
        return this.entries.equals(((EnvironmentAttributeMap) obj).entries);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.entries);
    }

    /**
     * There is currently no support for fully decoding environment attribute maps. TODO fix.
     */
    @ApiStatus.Experimental
    public static final class Entry {

        private final NBT tag;

        public Entry(NBT tag) {
            this.tag = tag;
        }
    }
}
