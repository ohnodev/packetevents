package io.github.retrooper.packetevents.factory.fabric;

import io.github.retrooper.packetevents.manager.AbstractFabricPlayerManager;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FabricPlayerManager extends AbstractFabricPlayerManager {

    public FabricPlayerManager() {
        super(FabricPacketEventsAPI.getServerAPI());
    }

    @Override
    public int getPing(@NotNull Object player) {
        return ((ServerPlayer) player).connection.latency();
    }

    @Override
    public Object getChannel(@NotNull Object player) {
        return ((ServerPlayer) player).connection.connection.channel;
    }

    @Override
    public boolean isServerPlayer(Object player) {
        return player instanceof ServerPlayer;
    }

    @Override
    public void disconnectPlayer(Object serverPlayer, String message) {
        ((ServerPlayer) serverPlayer).connection.disconnect(Component.literal(message));
    }

    @Override
    public void kickOnException(Object player, String message) {
        ServerPlayer serverPlayer = (ServerPlayer) player;
        Runnable kickTask = () -> disconnectPlayer(serverPlayer, message);
        if (!executeOnMainThread(serverPlayer, kickTask)) {
            kickTask.run();
        }
    }

    private boolean executeOnMainThread(ServerPlayer serverPlayer, Runnable task) {
        // Keep this mapping-agnostic: official snapshots expose either getServer() or server().
        Object server = invokeNoArg(serverPlayer, "getServer");
        if (server == null) {
            server = invokeNoArg(serverPlayer, "server");
        }
        if (server == null) {
            return false;
        }
        try {
            Method execute = server.getClass().getMethod("execute", Runnable.class);
            execute.invoke(server, task);
            return true;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {
            return false;
        }
    }

    private Object invokeNoArg(Object target, String methodName) {
        try {
            Method method = target.getClass().getMethod(methodName);
            return method.invoke(target);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {
            return null;
        }
    }
}
