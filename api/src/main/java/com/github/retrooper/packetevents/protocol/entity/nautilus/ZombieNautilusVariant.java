package com.github.retrooper.packetevents.protocol.entity.nautilus;

import com.github.retrooper.packetevents.protocol.entity.cow.CowVariant;
import com.github.retrooper.packetevents.protocol.entity.cow.CowVariants;
import com.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import com.github.retrooper.packetevents.resources.ResourceLocation;
import com.github.retrooper.packetevents.util.adventure.AdventureIndexUtil;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import net.kyori.adventure.util.Index;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface ZombieNautilusVariant extends MappedEntity {

    ModelType getModelType();

    ResourceLocation getAssetId();

    static ZombieNautilusVariant read(PacketWrapper<?> wrapper) {
        return wrapper.readMappedEntity(ZombieNautilusVariants.getRegistry());
    }

    static void write(PacketWrapper<?> wrapper, ZombieNautilusVariant variant) {
        wrapper.writeMappedEntity(variant);
    }

    enum ModelType {

        NORMAL("normal"),
        WARM("warm"),
        ;

        private static final Index<String, ModelType> NAME_INDEX = Index.create(
                ModelType.class, ModelType::getName);

        private final String name;

        ModelType(String name) {
            this.name = name;
        }

        public static ModelType getByName(String name) {
            return AdventureIndexUtil.indexValueOrThrow(NAME_INDEX, name);
        }

        public String getName() {
            return this.name;
        }
    }
}
