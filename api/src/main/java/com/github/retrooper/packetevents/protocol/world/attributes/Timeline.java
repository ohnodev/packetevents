package com.github.retrooper.packetevents.protocol.world.attributes;

import com.github.retrooper.packetevents.protocol.mapper.CopyableEntity;
import com.github.retrooper.packetevents.protocol.mapper.DeepComparableEntity;
import com.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import org.jspecify.annotations.NullMarked;

import java.util.Map;

/**
 * @versions 1.21.11+
 */
@NullMarked
public interface Timeline extends MappedEntity, CopyableEntity<Timeline>, DeepComparableEntity {

    int getPeriodTicks();

    Map<EnvironmentAttribute<?>, TimelineTrack<?, ?>> getTracks();

    // TODO
}
