package com.github.retrooper.packetevents.protocol.entity.nautilus;

import com.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import com.github.retrooper.packetevents.protocol.nbt.NBT;
import com.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import com.github.retrooper.packetevents.protocol.nbt.NBTString;
import com.github.retrooper.packetevents.protocol.player.ClientVersion;
import com.github.retrooper.packetevents.resources.ResourceLocation;
import com.github.retrooper.packetevents.util.adventure.AdventureIndexUtil;
import com.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import net.kyori.adventure.util.Index;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

/**
 * @versions 1.21.11+
 */
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

    static ZombieNautilusVariant decode(NBT nbt, ClientVersion version, @Nullable TypesBuilderData data) {
        NBTCompound compound = (NBTCompound) nbt;
        String modelTypeString = compound.getStringTagValueOrNull("model");
        ModelType modelType = modelTypeString != null ? ModelType.getByName(modelTypeString) : ModelType.NORMAL;
        ResourceLocation assetId = new ResourceLocation(compound.getStringTagValueOrThrow("asset_id"));
        return new StaticZombieNautilusVariant(data, modelType, assetId);
    }

    static NBT encode(ZombieNautilusVariant variant, ClientVersion version) {
        NBTCompound compound = new NBTCompound();
        compound.setTag("model", new NBTString(variant.getModelType().getName()));
        compound.setTag("asset_id", new NBTString(variant.getAssetId().toString()));
        return compound;
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
