package com.github.retrooper.packetevents.protocol.entity.nautilus;

import com.github.retrooper.packetevents.protocol.entity.nautilus.ZombieNautilusVariant.ModelType;
import com.github.retrooper.packetevents.resources.ResourceLocation;
import com.github.retrooper.packetevents.util.mappings.VersionedRegistry;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * @versions 1.21.11+
 */
@NullMarked
public final class ZombieNautilusVariants {

    private static final VersionedRegistry<ZombieNautilusVariant> REGISTRY = new VersionedRegistry<>("zombie_nautilus_variant");

    private ZombieNautilusVariants() {
    }

    @ApiStatus.Internal
    public static ZombieNautilusVariant define(String name, ModelType modelType, String texture) {
        ResourceLocation assetId = new ResourceLocation("entity/nautilus/" + texture);
        return REGISTRY.define(name, data ->
                new StaticZombieNautilusVariant(data, modelType, assetId));
    }

    public static VersionedRegistry<ZombieNautilusVariant> getRegistry() {
        return REGISTRY;
    }

    public static final ZombieNautilusVariant NORMAL = define("normal", ModelType.NORMAL, "zombie_nautilus");
    public static final ZombieNautilusVariant WARM = define("warm", ModelType.WARM, "zombie_nautilus_coral");

    static {
        REGISTRY.unloadMappings();
    }
}
