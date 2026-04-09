package io.github.retrooper.packetevents;

import com.github.retrooper.packetevents.manager.registry.ItemRegistry;
import com.github.retrooper.packetevents.manager.registry.RegistryManager;
import com.github.retrooper.packetevents.protocol.item.type.ItemType;
import com.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import com.github.retrooper.packetevents.protocol.player.ClientVersion;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import io.github.retrooper.packetevents.factory.fabric.FabricPacketEventsAPI;
import io.github.retrooper.packetevents.factory.fabric.FabricPlayerManager;
import io.github.retrooper.packetevents.loader.ChainLoadData;
import io.github.retrooper.packetevents.loader.ChainLoadEntryPoint;
import io.github.retrooper.packetevents.manager.AbstractFabricPlayerManager;
import io.github.retrooper.packetevents.manager.registry.FabricRegistryManager;
import io.github.retrooper.packetevents.util.LazyHolder;
import org.jetbrains.annotations.Nullable;

public class FabricOfficialChainLoadEntrypoint implements ChainLoadEntryPoint {

    protected LazyHolder<AbstractFabricPlayerManager> playerManagerHolder =
            LazyHolder.simple(() -> new FabricPlayerManager());
    protected LazyHolder<RegistryManager> registryManagerHolder =
            LazyHolder.simple(() -> new FabricRegistryManager(new ItemRegistry() {
                @Override
                public @Nullable ItemType getByName(String name) {
                    return ItemTypes.getRegistry().getByName(ClientVersion.V_26_2, name);
                }

                @Override
                public @Nullable ItemType getById(int id) {
                    return ItemTypes.getRegistry().getById(ClientVersion.V_26_2, id);
                }
            }));

    @Override
    public void initialize(ChainLoadData chainLoadData) {
        chainLoadData.setPlayerManagerIfNull(playerManagerHolder);
        chainLoadData.setRegistryManagerIfNull(registryManagerHolder);
    }

    @Override
    public ServerVersion getNativeVersion() {
        return ServerVersion.V_26_2;
    }
}
