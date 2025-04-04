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

package com.github.retrooper.packetevents.protocol.entity.tropicalfish;

import com.github.retrooper.packetevents.util.mappings.VersionedRegistry;
import org.jetbrains.annotations.ApiStatus;

public final class TropicalFishPatterns {

    private static final VersionedRegistry<TropicalFishPattern> REGISTRY =
            new VersionedRegistry<>("tropical_fish_pattern");

    private TropicalFishPatterns() {
    }

    @ApiStatus.Internal
    public static TropicalFishPattern define(String name) {
        return REGISTRY.define(name, StaticTropicalFishPattern::new);
    }

    public static VersionedRegistry<TropicalFishPattern> getRegistry() {
        return REGISTRY;
    }

    public static final TropicalFishPattern KOB = define("kob");
    public static final TropicalFishPattern SUNSTREAK = define("sunstreak");
    public static final TropicalFishPattern SNOOPER = define("snooper");
    public static final TropicalFishPattern DASHER = define("dasher");
    public static final TropicalFishPattern BRINELY = define("brinely");
    public static final TropicalFishPattern SPOTTY = define("spotty");
    public static final TropicalFishPattern FLOPPER = define("flopper");
    public static final TropicalFishPattern STRIPEY = define("stripey");
    public static final TropicalFishPattern GLITTER = define("glitter");
    public static final TropicalFishPattern BLOCKFISH = define("blockfish");
    public static final TropicalFishPattern BETTY = define("betty");
    public static final TropicalFishPattern CLAYFISH = define("clayfish");

    static {
        REGISTRY.unloadMappings();
    }
}
