/*
 * This file is part of packetevents - https://github.com/retrooper/packetevents
 * Copyright (C) 2026 retrooper and contributors
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

package com.github.retrooper.packetevents.protocol.world.clock;
// Created by booky10 in packetevents (21:30 20.02.2026)

import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import org.jspecify.annotations.NullMarked;

import java.util.Objects;

@NullMarked
public final class ClockState {

    private final long totalTicks;
    private final boolean paused;

    public ClockState(long totalTicks, boolean paused) {
        this.totalTicks = totalTicks;
        this.paused = paused;
    }

    public static ClockState read(PacketWrapper<?> wrapper) {
        if (wrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_26_1)) {
            return new ClockState(wrapper.readVarLong(), wrapper.readBoolean());
        }
        long totalTicks = wrapper.readLong();
        boolean tickTime = wrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_2)
                ? wrapper.readBoolean() : totalTicks >= 0L;
        return new ClockState(totalTicks, !tickTime);
    }

    public static void write(PacketWrapper<?> wrapper, ClockState state) {
        if (wrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_26_1)) {
            wrapper.writeVarLong(state.totalTicks);
            wrapper.writeBoolean(state.paused);
        } else {
            wrapper.writeLong(state.totalTicks);
            if (wrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
                wrapper.writeBoolean(!state.paused);
            }
        }
    }

    public long getTotalTicks() {
        return this.totalTicks;
    }

    public ClockState withTotalTicks(long totalTicks) {
        return new ClockState(totalTicks, this.paused);
    }

    public boolean isPaused() {
        return this.paused;
    }

    public ClockState withPaused(boolean paused) {
        return new ClockState(this.totalTicks, paused);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ClockState)) return false;
        ClockState that = (ClockState) obj;
        if (this.totalTicks != that.totalTicks) return false;
        return this.paused == that.paused;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.totalTicks, this.paused);
    }
}
