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

import com.github.retrooper.packetevents.protocol.color.AlphaColor;
import com.github.retrooper.packetevents.protocol.color.Color;
import com.github.retrooper.packetevents.protocol.particle.Particle;
import com.github.retrooper.packetevents.protocol.particle.type.ParticleTypes;
import com.github.retrooper.packetevents.protocol.util.NbtCodec;
import com.github.retrooper.packetevents.protocol.util.NbtCodecs;
import com.github.retrooper.packetevents.protocol.world.biome.BiomeEffects.ParticleSettings;
import com.github.retrooper.packetevents.util.mappings.VersionedRegistry;
import net.kyori.adventure.util.TriState;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.UnknownNullability;
import org.jspecify.annotations.NullMarked;

import java.util.Collections;
import java.util.List;

/**
 * @version 1.21.11+
 */
@NullMarked
public final class EnvironmentAttributes {

    private static final VersionedRegistry<EnvironmentAttribute<?>> REGISTRY =
            new VersionedRegistry<>("environment_attribute");
    public static final NbtCodec<EnvironmentAttribute<?>> CODEC = NbtCodecs.forRegistry(REGISTRY);

    private EnvironmentAttributes() {
    }

    public static VersionedRegistry<EnvironmentAttribute<?>> getRegistry() {
        return REGISTRY;
    }

    @ApiStatus.Internal
    public static <T> EnvironmentAttribute<T> defineUnsynced(String name) {
        return define(name, NbtCodecs.errorCodec(), null);
    }

    @ApiStatus.Internal
    public static <T> EnvironmentAttribute<T> define(String name, NbtCodec<T> codec, @UnknownNullability T defaultValue) {
        return REGISTRY.define(name, data ->
                new StaticEnvironmentAttribute<>(data, codec, defaultValue));
    }

    public static EnvironmentAttribute<Color> VISUAL_FOG_COLOR = define("visual/fog_color", NbtCodecs.RGB_COLOR, Color.BLACK);
    public static EnvironmentAttribute<Float> VISUAL_FOG_START_DISTANCE = define("visual/fog_start_distance", NbtCodecs.FLOAT, 0f);
    public static EnvironmentAttribute<Float> VISUAL_FOG_END_DISTANCE = define("visual/fog_end_distance", NbtCodecs.FLOAT, 1024f);
    public static EnvironmentAttribute<Float> VISUAL_SKY_FOG_END_DISTANCE = define("visual/sky_fog_end_distance", NbtCodecs.FLOAT, 512f);
    public static EnvironmentAttribute<Float> VISUAL_CLOUD_FOG_END_DISTANCE = define("visual/cloud_fog_end_distance", NbtCodecs.FLOAT, 2048f);
    public static EnvironmentAttribute<Color> VISUAL_WATER_FOG_COLOR = define("visual/water_fog_color", NbtCodecs.RGB_COLOR, new Color(0xFAFACD));
    public static EnvironmentAttribute<Float> VISUAL_WATER_FOG_START_DISTANCE = define("visual/water_fog_start_distance", NbtCodecs.FLOAT, -8f);
    public static EnvironmentAttribute<Float> VISUAL_WATER_FOG_END_DISTANCE = define("visual/water_fog_end_distance", NbtCodecs.FLOAT, 96f);
    public static EnvironmentAttribute<Color> VISUAL_SKY_COLOR = define("visual/sky_color", NbtCodecs.RGB_COLOR, Color.BLACK);
    public static EnvironmentAttribute<AlphaColor> VISUAL_SUNRISE_SUNSET_COLOR = define("visual/sunrise_sunset_color", NbtCodecs.ARGB_COLOR, AlphaColor.TRANSPARENT);
    public static EnvironmentAttribute<AlphaColor> VISUAL_CLOUD_COLOR = define("visual/cloud_color", NbtCodecs.ARGB_COLOR, AlphaColor.TRANSPARENT);
    public static EnvironmentAttribute<Float> VISUAL_CLOUD_HEIGHT = define("visual/cloud_height", NbtCodecs.FLOAT, 192.33f);
    public static EnvironmentAttribute<Float> VISUAL_SUN_ANGLE = define("visual/sun_angle", NbtCodecs.FLOAT, 0f);
    public static EnvironmentAttribute<Float> VISUAL_MOON_ANGLE = define("visual/moon_angle", NbtCodecs.FLOAT, 0f);
    public static EnvironmentAttribute<Float> VISUAL_STAR_ANGLE = define("visual/star_angle", NbtCodecs.FLOAT, 0f);
    public static EnvironmentAttribute<MoonPhase> VISUAL_MOON_PHASE = define("visual/moon_phase", MoonPhase.CODEC, MoonPhase.FULL_MOON);
    public static EnvironmentAttribute<Float> VISUAL_STAR_BRIGHTNESS = define("visual/star_brightness", NbtCodecs.FLOAT, 0f);
    public static EnvironmentAttribute<Color> VISUAL_SKY_LIGHT_COLOR = define("visual/sky_light_color", NbtCodecs.RGB_COLOR, Color.WHITE);
    public static EnvironmentAttribute<Float> VISUAL_SKY_LIGHT_FACTOR = define("visual/sky_light_factor", NbtCodecs.FLOAT, 1f);
    public static EnvironmentAttribute<Particle<?>> VISUAL_DEFAULT_DRIPSTONE_PARTICLE = define("visual/default_dripstone_particle", Particle.CODEC, new Particle<>(ParticleTypes.DRIPPING_DRIPSTONE_WATER));
    public static EnvironmentAttribute<List<ParticleSettings>> VISUAL_AMBIENT_PARTICLES = define("visual/ambient_particles", ParticleSettings.CODEC.applyList(), Collections.emptyList());
    public static EnvironmentAttribute<BackgroundMusic> AUDIO_BACKGROUND_MUSIC = define("audio/background_music", BackgroundMusic.CODEC, BackgroundMusic.EMPTY);
    public static EnvironmentAttribute<Float> AUDIO_MUSIC_VOLUME = define("audio/music_volume", NbtCodecs.FLOAT, 1f);
    public static EnvironmentAttribute<AmbientSounds> AUDIO_AMBIENT_SOUNDS = define("audio/ambient_sounds", AmbientSounds.CODEC, AmbientSounds.EMPTY);
    public static EnvironmentAttribute<Boolean> AUDIO_FIREFLY_BUSH_SOUNDS = define("audio/firefly_bush_sounds", NbtCodecs.BOOLEAN, false);
    public static EnvironmentAttribute<Float> GAMEPLAY_SKY_LIGHT_LEVEL = define("gameplay/sky_light_level", NbtCodecs.FLOAT, 15f);
    /**
     * Not synced via network, not usable via packetevents.
     */
    @ApiStatus.Obsolete
    public static EnvironmentAttribute<Boolean> GAMEPLAY_CAN_START_RAID = defineUnsynced("gameplay/can_start_raid");
    public static EnvironmentAttribute<Boolean> GAMEPLAY_WATER_EVAPORATES = define("gameplay/water_evaporates", NbtCodecs.BOOLEAN, false);
    /**
     * Not synced via network, not usable via packetevents.
     */
    @ApiStatus.Obsolete
    public static EnvironmentAttribute<?> GAMEPLAY_BED_RULE = defineUnsynced("gameplay/bed_rule");
    /**
     * Not synced via network, not usable via packetevents.
     */
    @ApiStatus.Obsolete
    public static EnvironmentAttribute<Boolean> GAMEPLAY_RESPAWN_ANCHOR_WORKS = defineUnsynced("gameplay/respawn_anchor_works");
    /**
     * Not synced via network, not usable via packetevents.
     */
    @ApiStatus.Obsolete
    public static EnvironmentAttribute<Boolean> GAMEPLAY_NETHER_PORTAL_SPAWNS_PIGLIN = defineUnsynced("gameplay/nether_portal_spawns_piglin");
    public static EnvironmentAttribute<Boolean> GAMEPLAY_FAST_LAVA = define("gameplay/fast_lava", NbtCodecs.BOOLEAN, false);
    /**
     * Not synced via network, not usable via packetevents.
     */
    @ApiStatus.Obsolete
    public static EnvironmentAttribute<Boolean> GAMEPLAY_INCREASED_FIRE_BURNOUT = defineUnsynced("gameplay/increased_fire_burnout");
    /**
     * Not synced via network, not usable via packetevents.
     */
    @ApiStatus.Obsolete
    public static EnvironmentAttribute<TriState> GAMEPLAY_EYEBLOSSOM_OPEN = defineUnsynced("gameplay/eyeblossom_open");
    /**
     * Not synced via network, not usable via packetevents.
     */
    @ApiStatus.Obsolete
    public static EnvironmentAttribute<Float> GAMEPLAY_TURTLE_EGG_HATCH_CHANCE = defineUnsynced("gameplay/turtle_egg_hatch_chance");
    public static EnvironmentAttribute<Boolean> GAMEPLAY_PIGLINS_ZOMBIFY = define("gameplay/piglins_zombify", NbtCodecs.BOOLEAN, true);
    /**
     * Not synced via network, not usable via packetevents.
     */
    @ApiStatus.Obsolete
    public static EnvironmentAttribute<Boolean> GAMEPLAY_SNOW_GOLEM_MELTS = defineUnsynced("gameplay/snow_golem_melts");
    public static EnvironmentAttribute<Boolean> GAMEPLAY_CREAKING_ACTIVE = define("gameplay/creaking_active", NbtCodecs.BOOLEAN, false);
    /**
     * Not synced via network, not usable via packetevents.
     */
    @ApiStatus.Obsolete
    public static EnvironmentAttribute<Float> GAMEPLAY_SURFACE_SLIME_SPAWN_CHANCE = defineUnsynced("gameplay/surface_slime_spawn_chance");
    /**
     * Not synced via network, not usable via packetevents.
     */
    @ApiStatus.Obsolete
    public static EnvironmentAttribute<Float> GAMEPLAY_CAT_WAKING_UP_GIFT_CHANCE = defineUnsynced("gameplay/cat_waking_up_gift_chance");
    /**
     * Not synced via network, not usable via packetevents.
     */
    @ApiStatus.Obsolete
    public static EnvironmentAttribute<Boolean> GAMEPLAY_BEES_STAY_IN_HIVE = defineUnsynced("gameplay/bees_stay_in_hive");
    /**
     * Not synced via network, not usable via packetevents.
     */
    @ApiStatus.Obsolete
    public static EnvironmentAttribute<Boolean> GAMEPLAY_MONSTERS_BURN = defineUnsynced("gameplay/monsters_burn");
    /**
     * Not synced via network, not usable via packetevents.
     */
    @ApiStatus.Obsolete
    public static EnvironmentAttribute<Boolean> GAMEPLAY_CAN_PILLAGER_PATROL_SPAWN = defineUnsynced("gameplay/can_pillager_patrol_spawn");
    /**
     * Not synced via network, not usable via packetevents.
     */
    @ApiStatus.Obsolete
    public static EnvironmentAttribute<?> GAMEPLAY_VILLAGER_ACTIVITY = defineUnsynced("gameplay/villager_activity");
    /**
     * Not synced via network, not usable via packetevents.
     */
    @ApiStatus.Obsolete
    public static EnvironmentAttribute<?> GAMEPLAY_BABY_VILLAGER_ACTIVITY = defineUnsynced("gameplay/baby_villager_activity");

    static {
        REGISTRY.unloadMappings();
    }
}
