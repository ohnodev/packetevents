package com.github.retrooper.packetevents.protocol.entity.nautilus;

import com.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import com.github.retrooper.packetevents.resources.ResourceLocation;
import com.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class StaticZombieNautilusVariant extends AbstractMappedEntity implements ZombieNautilusVariant {

    private final ModelType modelType;
    private final ResourceLocation assetId;

    public StaticZombieNautilusVariant(ModelType modelType, ResourceLocation assetId) {
        this(null, modelType, assetId);
    }

    @ApiStatus.Internal
    public StaticZombieNautilusVariant(@Nullable TypesBuilderData data, ModelType modelType, ResourceLocation assetId) {
        super(data);
        this.modelType = modelType;
        this.assetId = assetId;
    }

    @Override
    public ModelType getModelType() {
        return this.modelType;
    }

    @Override
    public ResourceLocation getAssetId() {
        return this.assetId;
    }
}
