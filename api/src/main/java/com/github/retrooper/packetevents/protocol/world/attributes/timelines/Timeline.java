package com.github.retrooper.packetevents.protocol.world.attributes.timelines;

import com.github.retrooper.packetevents.protocol.mapper.CopyableEntity;
import com.github.retrooper.packetevents.protocol.mapper.DeepComparableEntity;
import com.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import com.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import com.github.retrooper.packetevents.protocol.nbt.NBTInt;
import com.github.retrooper.packetevents.protocol.util.NbtCodec;
import com.github.retrooper.packetevents.protocol.util.NbtCodecException;
import com.github.retrooper.packetevents.protocol.util.NbtCodecs;
import com.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import com.github.retrooper.packetevents.protocol.world.attributes.EnvironmentAttribute;
import com.github.retrooper.packetevents.protocol.world.attributes.EnvironmentAttributes;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.Map;

/**
 * @versions 1.21.11+
 */
@NullMarked
public interface Timeline extends MappedEntity, CopyableEntity<Timeline>, DeepComparableEntity {

    NbtCodec<Map<EnvironmentAttribute<?>, TimelineTrack<?, ?>>> TRACK_CODEC = NbtMapCodec.<EnvironmentAttribute<?>, TimelineTrack<?, ?>>codecOfMap(
            NbtCodecs.forRegistry(EnvironmentAttributes.getRegistry()), TimelineTrack::codec).codec();
    NbtCodec<Timeline> CODEC = new NbtMapCodec<Timeline>() {
        @Override
        public Timeline decode(NBTCompound compound, PacketWrapper<?> wrapper) throws NbtCodecException {
            Integer periodTicks = compound.getOrNull("period_ticks", NbtCodecs.INT, wrapper);
            Map<EnvironmentAttribute<?>, TimelineTrack<?, ?>> tracks = compound.getOr("tracks", TRACK_CODEC, Collections.emptyMap(), wrapper);
            return new StaticTimeline(periodTicks, tracks);
        }

        @Override
        public void encode(NBTCompound compound, PacketWrapper<?> wrapper, Timeline value) throws NbtCodecException {
            Integer periodTicks = value.getPeriodTicks();
            if (periodTicks != null) {
                compound.setTag("period_ticks", new NBTInt(periodTicks));
            }
            Map<EnvironmentAttribute<?>, TimelineTrack<?, ?>> tracks = value.getTracks();
            if (!tracks.isEmpty()) {
                compound.set("tracks", tracks, TRACK_CODEC, wrapper);
            }
        }
    }.codec();

    @Nullable Integer getPeriodTicks();

    Map<EnvironmentAttribute<?>, TimelineTrack<?, ?>> getTracks();
}
