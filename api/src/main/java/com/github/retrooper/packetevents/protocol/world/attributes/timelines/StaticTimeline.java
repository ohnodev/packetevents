package com.github.retrooper.packetevents.protocol.world.attributes.timelines;

import com.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import com.github.retrooper.packetevents.protocol.world.attributes.EnvironmentAttribute;
import com.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.Objects;

/**
 * @versions 1.21.11+
 */
@NullMarked
public class StaticTimeline extends AbstractMappedEntity implements Timeline {

    private final @Nullable Integer periodTicks;
    private final Map<EnvironmentAttribute<?>, TimelineTrack<?, ?>> tracks;

    public StaticTimeline(@Nullable Integer periodTicks, Map<EnvironmentAttribute<?>, TimelineTrack<?, ?>> tracks) {
        this(null, periodTicks, tracks);
    }

    @ApiStatus.Internal
    public StaticTimeline(
            @org.jetbrains.annotations.Nullable TypesBuilderData data,
            @Nullable Integer periodTicks, Map<EnvironmentAttribute<?>, TimelineTrack<?, ?>> tracks
    ) {
        super(data);
        this.periodTicks = periodTicks;
        this.tracks = tracks;
    }

    @Override
    public Timeline copy(@Nullable TypesBuilderData newData) {
        return new StaticTimeline(newData, this.periodTicks, this.tracks);
    }

    @Override
    public @Nullable Integer getPeriodTicks() {
        return this.periodTicks;
    }

    @Override
    public Map<EnvironmentAttribute<?>, TimelineTrack<?, ?>> getTracks() {
        return this.tracks;
    }

    @Override
    public boolean deepEquals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        StaticTimeline that = (StaticTimeline) obj;
        if (!Objects.equals(this.periodTicks, that.periodTicks)) return false;
        return this.tracks.equals(that.tracks);
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(super.hashCode(), this.periodTicks, this.tracks);
    }
}
