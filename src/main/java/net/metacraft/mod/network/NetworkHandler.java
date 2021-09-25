package net.metacraft.mod.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.function.Function;


public final class NetworkHandler {
    public static <T extends Packet> S2CPacketType<T> serverToClient(Identifier id, Function<PacketByteBuf, T> factory) {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            ClientProxy.register(id, factory);
        }
        return () -> id;
    }

    private static final class ClientProxy {
        public static <T extends Packet> void register(Identifier id, Function<PacketByteBuf, T> factory) {
            ClientPlayNetworking.registerGlobalReceiver(id, (client, handler, buffer, responseSender) -> {
                T packet = factory.apply(buffer);
                client.execute(() -> packet.onPacket());
            });
        }
    }
}
