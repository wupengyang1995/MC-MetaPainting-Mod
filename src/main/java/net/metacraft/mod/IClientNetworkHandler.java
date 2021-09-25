package net.metacraft.mod;

import net.metacraft.mod.network.MetaPaintingSpawnS2CPacket;

public interface IClientNetworkHandler {
    void handleMetaPaintingPacket(MetaPaintingSpawnS2CPacket packet);
}
