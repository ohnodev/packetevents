/*
 * This file is part of packetevents - https://github.com/retrooper/packetevents
 * Copyright (C) 2024 retrooper and contributors
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

package com.github.retrooper.packetevents.protocol.world.dimension;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.protocol.mapper.CopyableEntity;
import com.github.retrooper.packetevents.protocol.mapper.DeepComparableEntity;
import com.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import com.github.retrooper.packetevents.protocol.mapper.MappedEntityRefSet;
import com.github.retrooper.packetevents.protocol.mapper.MappedEntitySet;
import com.github.retrooper.packetevents.protocol.nbt.NBT;
import com.github.retrooper.packetevents.protocol.nbt.NBTByte;
import com.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import com.github.retrooper.packetevents.protocol.nbt.NBTDouble;
import com.github.retrooper.packetevents.protocol.nbt.NBTFloat;
import com.github.retrooper.packetevents.protocol.nbt.NBTInt;
import com.github.retrooper.packetevents.protocol.nbt.NBTLong;
import com.github.retrooper.packetevents.protocol.nbt.NBTNumber;
import com.github.retrooper.packetevents.protocol.nbt.NBTString;
import com.github.retrooper.packetevents.protocol.player.ClientVersion;
import com.github.retrooper.packetevents.protocol.util.CodecNameable;
import com.github.retrooper.packetevents.protocol.util.NbtCodec;
import com.github.retrooper.packetevents.protocol.util.NbtCodecException;
import com.github.retrooper.packetevents.protocol.util.NbtCodecs;
import com.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import com.github.retrooper.packetevents.protocol.world.attributes.EnvironmentAttributeMap;
import com.github.retrooper.packetevents.protocol.world.attributes.EnvironmentAttributes;
import com.github.retrooper.packetevents.protocol.world.attributes.timelines.Timeline;
import com.github.retrooper.packetevents.resources.ResourceLocation;
import com.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

import java.util.OptionalLong;

@NullMarked
public interface DimensionType extends MappedEntity, CopyableEntity<DimensionType>, DeepComparableEntity {

    NbtCodec<DimensionType> CODEC = new NbtMapCodec<DimensionType>() {
        @Override
        public DimensionType decode(NBTCompound compound, PacketWrapper<?> wrapper) throws NbtCodecException {
            boolean hasFixedTime;
            Skybox skybox;
            CardinalLight cardinalLight;
            EnvironmentAttributeMap attributes;
            MappedEntityRefSet<Timeline> timelines;

            ServerVersion version = wrapper.getServerVersion();
            if (version.isNewerThanOrEquals(ServerVersion.V_1_21_11)) {
                hasFixedTime = compound.getBooleanOr("has_fixed_time", false);
                skybox = compound.getOr("skybox", Skybox.CODEC, Skybox.OVERWORLD, wrapper);
                cardinalLight = compound.getOr("cardinal_light", CardinalLight.CODEC, CardinalLight.DEFAULT, wrapper);
                attributes = compound.getOr("attributes", EnvironmentAttributeMap.CODEC, EnvironmentAttributeMap.EMPTY, wrapper);
                timelines = compound.getOr("timelines", MappedEntitySet::decodeRefSet, MappedEntitySet.createEmpty(), wrapper);
            } else {
                hasFixedTime = false;
                skybox = Skybox.NONE;
                cardinalLight = CardinalLight.DEFAULT;
                attributes = EnvironmentAttributeMap.create();
                timelines = MappedEntitySet.createEmpty();
            }

            double coordinateScale;
            int minY = 0;
            int height = 256;
            NBT monsterSpawnLightLevel = null;
            int monsterSpawnBlockLightLimit = 0;
            if (version.isNewerThanOrEquals(ServerVersion.V_1_16_2)) {
                coordinateScale = compound.getNumberTagOrThrow("coordinate_scale").getAsDouble();
                if (version.isNewerThanOrEquals(ServerVersion.V_1_17)) {
                    minY = compound.getNumberTagOrThrow("min_y").getAsInt();
                    height = compound.getNumberTagOrThrow("height").getAsInt();
                    if (version.isNewerThanOrEquals(ServerVersion.V_1_19)) {
                        // TODO proper int provider decoding/encoding
                        monsterSpawnLightLevel = compound.getTagOrThrow("monster_spawn_light_level");
                        monsterSpawnBlockLightLimit = compound.getNumberTagOrThrow("monster_spawn_block_light_limit").getAsInt();
                    }
                }
            } else {
                coordinateScale = compound.getBoolean("shrunk") ? 8d : 1d;
            }

            ResourceLocation effectsLocation = null;
            OptionalLong fixedTime = OptionalLong.empty();
            if (version.isOlderThan(ServerVersion.V_1_21_11)) {
                Number fixedTimeNum = compound.getNumberTagValueOrNull("fixed_time");
                if (fixedTimeNum != null) {
                    hasFixedTime = true;
                    fixedTime = OptionalLong.of(fixedTimeNum.longValue());
                }
                boolean ultrawarm = compound.getBoolean("ultrawarm");
                boolean natural = compound.getBoolean("natural");
                boolean bedWorking = compound.getBoolean("bed_works");
                boolean respawnAnchorWorking = compound.getBoolean("respawn_anchor_works");
                boolean piglinSafe = compound.getBoolean("piglin_safe");
                boolean hasRaids = compound.getBoolean("has_raids");

                if (version.isNewerThanOrEquals(ServerVersion.V_1_16_2)) {
                    effectsLocation = compound.getOrThrow("effects", ResourceLocation.CODEC, wrapper);
                }
                if (version.isNewerThanOrEquals(ServerVersion.V_1_21_6)) {
                    NBTNumber cloudHeightTag = compound.getNumberTagOrNull("cloud_height");
                    if (cloudHeightTag != null) {
                        attributes.set(EnvironmentAttributes.VISUAL_CLOUD_HEIGHT, cloudHeightTag.getAsFloat());
                    }
                }
            }

            boolean hasSkylight = compound.getBooleanOrThrow("has_skylight");
            boolean hasCeiling = compound.getBooleanOrThrow("has_ceiling");
            int logicalHeight = compound.getNumberTagValueOrThrow("logical_height").intValue();
            // TODO proper tag key decoding/encoding
            String infiniburn = compound.getStringTagValueOrThrow("infiniburn");
            float ambientLight = compound.getNumberTagValueOrThrow("ambient_light").floatValue();

            // new
        }

        @Override
        public void encode(NBTCompound compound, PacketWrapper<?> wrapper, DimensionType value) throws NbtCodecException {

        }
    }.codec;

    /**
     * @versions 1.21.11+
     */
    boolean hasFixedTime();

    /**
     * @versions -1.21.10
     */
    @ApiStatus.Obsolete
    OptionalLong getFixedTime();

    boolean hasSkyLight();

    boolean hasCeiling();

    /**
     * @versions -1.21.10
     */
    @ApiStatus.Obsolete
    boolean isUltraWarm();

    /**
     * @versions -1.21.10
     */
    @ApiStatus.Obsolete
    boolean isNatural();

    double getCoordinateScale();

    default boolean isShrunk() {
        return this.getCoordinateScale() > 1d;
    }

    /**
     * @versions -1.21.10
     */
    @ApiStatus.Obsolete
    boolean isBedWorking();

    /**
     * @versions -1.21.10
     */
    @ApiStatus.Obsolete
    boolean isRespawnAnchorWorking();

    /**
     * @versions 1.17+
     */
    default int getMinY() {
        return this.getMinY(PacketEvents.getAPI().getServerManager().getVersion().toClientVersion());
    }

    /**
     * @versions 1.17+
     */
    int getMinY(ClientVersion version);

    /**
     * @versions 1.17+
     */
    default int getHeight() {
        return this.getHeight(PacketEvents.getAPI().getServerManager().getVersion().toClientVersion());
    }

    /**
     * @versions 1.17+
     */
    int getHeight(ClientVersion version);

    default int getLogicalHeight() {
        return this.getLogicalHeight(PacketEvents.getAPI().getServerManager().getVersion().toClientVersion());
    }

    int getLogicalHeight(ClientVersion version);

    String getInfiniburnTag();

    /**
     * @versions 1.16.2-1.21.10
     */
    @ApiStatus.Obsolete
    ResourceLocation getEffectsLocation();

    float getAmbientLight();

    /**
     * @versions 1.21.6-1.21.10
     */
    @ApiStatus.Obsolete
    @Nullable Integer getCloudHeight();

    // monster settings

    /**
     * @versions -1.21.10
     */
    @ApiStatus.Obsolete
    boolean isPiglinSafe();

    /**
     * @versions -1.21.10
     */
    @ApiStatus.Obsolete
    boolean hasRaids();

    /**
     * @versions 1.19+
     */
    @ApiStatus.Experimental
    NBT getMonsterSpawnLightLevel();

    /**
     * @versions 1.19+
     */
    int getMonsterSpawnBlockLightLimit();

    // conversion utilities

    default DimensionTypeRef asRef(ClientVersion version) {
        return new DimensionTypeRef.DirectRef(this, version);
    }

    // nbt decoding/encoding

    static DimensionType decode(NBT nbt, ClientVersion version, @Nullable TypesBuilderData data) {
        NBTCompound compound = (NBTCompound) nbt;
        OptionalLong fixedTime = !compound.getTags().containsKey("fixed_time") ? OptionalLong.empty() :
                OptionalLong.of(compound.getNumberTagOrThrow("fixed_time").getAsLong());
        boolean hasSkylight = compound.getBoolean("has_skylight");
        boolean hasCeiling = compound.getBoolean("has_ceiling");
        boolean ultrawarm = compound.getBoolean("ultrawarm");
        boolean natural = compound.getBoolean("natural");
        boolean bedWorking = compound.getBoolean("bed_works");
        boolean respawnAnchorWorking = compound.getBoolean("respawn_anchor_works");
        int logicalHeight = compound.getNumberTagOrThrow("logical_height").getAsInt();
        String infiniburnTag = compound.getStringTagValueOrThrow("infiniburn");
        float ambientLight = compound.getNumberTagOrThrow("ambient_light").getAsFloat();
        boolean piglinSafe = compound.getBoolean("piglin_safe");
        boolean hasRaids = compound.getBoolean("has_raids");

        double coordinateScale;
        int minY = 0;
        int height = 256;
        ResourceLocation effectsLocation = null;
        Integer cloudHeight = null;
        NBT monsterSpawnLightLevel = null;
        int monsterSpawnBlockLightLimit = 0;
        if (version.isNewerThanOrEquals(ClientVersion.V_1_16_2)) {
            coordinateScale = compound.getNumberTagOrThrow("coordinate_scale").getAsDouble();
            effectsLocation = new ResourceLocation(compound.getStringTagValueOrThrow("effects"));
            if (version.isNewerThanOrEquals(ClientVersion.V_1_17)) {
                minY = compound.getNumberTagOrThrow("min_y").getAsInt();
                height = compound.getNumberTagOrThrow("height").getAsInt();
                if (version.isNewerThanOrEquals(ClientVersion.V_1_19)) {
                    monsterSpawnLightLevel = compound.getTagOrThrow("monster_spawn_light_level");
                    monsterSpawnBlockLightLimit = compound.getNumberTagOrThrow("monster_spawn_block_light_limit").getAsInt();
                    if (version.isNewerThanOrEquals(ClientVersion.V_1_21_6)) {
                        NBTNumber cloudHeightTag = compound.getNumberTagOrNull("cloud_height");
                        cloudHeight = cloudHeightTag != null ? cloudHeightTag.getAsInt() : null;
                    }
                }
            }
        } else {
            coordinateScale = compound.getBoolean("shrunk") ? 8d : 1d;
        }

        return new StaticDimensionType(data, fixedTime, hasSkylight, hasCeiling, ultrawarm, natural, coordinateScale,
                bedWorking, respawnAnchorWorking, minY, height, logicalHeight, infiniburnTag, effectsLocation,
                ambientLight, cloudHeight, piglinSafe, hasRaids, monsterSpawnLightLevel, monsterSpawnBlockLightLimit);
    }

    static NBT encode(DimensionType dimensionType, ClientVersion version) {
        NBTCompound compound = new NBTCompound();
        dimensionType.getFixedTime().ifPresent(fixedTime ->
                compound.setTag("fixed_time", new NBTLong(fixedTime)));
        compound.setTag("has_skylight", new NBTByte(dimensionType.hasSkyLight()));
        compound.setTag("has_ceiling", new NBTByte(dimensionType.hasCeiling()));
        compound.setTag("ultrawarm", new NBTByte(dimensionType.isUltraWarm()));
        compound.setTag("natural", new NBTByte(dimensionType.isNatural()));
        compound.setTag("bed_works", new NBTByte(dimensionType.isBedWorking()));
        compound.setTag("respawn_anchor_works", new NBTByte(dimensionType.isRespawnAnchorWorking()));
        compound.setTag("logical_height", new NBTInt(dimensionType.getLogicalHeight(version)));
        compound.setTag("infiniburn", new NBTString(dimensionType.getInfiniburnTag()));
        compound.setTag("ambient_light", new NBTFloat(dimensionType.getAmbientLight()));
        compound.setTag("piglin_safe", new NBTByte(dimensionType.isPiglinSafe()));
        compound.setTag("has_raids", new NBTByte(dimensionType.hasRaids()));

        if (version.isNewerThanOrEquals(ClientVersion.V_1_16_2)) {
            compound.setTag("coordinate_scale", new NBTDouble(dimensionType.getCoordinateScale()));
            compound.setTag("effects", new NBTString(dimensionType.getEffectsLocation().toString()));
            if (version.isNewerThanOrEquals(ClientVersion.V_1_17)) {
                compound.setTag("min_y", new NBTInt(dimensionType.getMinY(version)));
                compound.setTag("height", new NBTInt(dimensionType.getHeight(version)));
                if (version.isNewerThanOrEquals(ClientVersion.V_1_19)) {
                    compound.setTag("monster_spawn_light_level", dimensionType.getMonsterSpawnLightLevel());
                    compound.setTag("monster_spawn_block_light_limit", new NBTInt(dimensionType.getMonsterSpawnBlockLightLimit()));
                    if (version.isNewerThanOrEquals(ClientVersion.V_1_21_6)) {
                        if (dimensionType.getCloudHeight() != null) {
                            compound.setTag("cloud_height", new NBTInt(dimensionType.getCloudHeight()));
                        }
                    }
                }
            }
        } else {
            compound.setTag("shrunk", new NBTByte(dimensionType.isShrunk()));
        }
        return compound;
    }


    /**
     * @versions 1.21.11+
     */
    enum Skybox implements CodecNameable {

        NONE("none"),
        OVERWORLD("overworld"),
        END("end"),
        ;

        public static final NbtCodec<Skybox> CODEC = NbtCodecs.forEnum(values());

        private final String name;

        Skybox(String name) {
            this.name = name;
        }

        @Override
        public String getCodecName() {
            return this.name;
        }
    }

    enum CardinalLight implements CodecNameable {

        DEFAULT("default"),
        NETHER("nether"),
        ;

        public static final NbtCodec<CardinalLight> CODEC = NbtCodecs.forEnum(values());

        private final String name;

        CardinalLight(String name) {
            this.name = name;
        }

        @Override
        public String getCodecName() {
            return this.name;
        }
    }
}
