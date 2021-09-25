package net.metacraft.mod.network;

import net.metacraft.mod.painting.MetaPaintingSpawnS2CPacket;

public interface IClientNetworkCallback {
    void handleMetaPaintingPacket(MetaPaintingSpawnS2CPacket packet);
}
