package net.metacraft.mod.network;

import net.metacraft.mod.utils.Constants;
import net.minecraft.util.Identifier;

public final class NetworkManager {
    public static S2CPacketType<MetaPaintingSpawnS2CPacket> SERVER_MetaPainting = null;

    public static void init() {
        SERVER_MetaPainting = SimpleNetworking.serverToClient(new Identifier(Constants.MOD_ID, "meta_painting"), MetaPaintingSpawnS2CPacket::new);
    }
}
