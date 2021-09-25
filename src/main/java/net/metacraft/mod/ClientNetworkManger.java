package net.metacraft.mod;

import net.metacraft.mod.network.MetaPaintingSpawnS2CPacket;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

public enum ClientNetworkManger implements IClientNetworkHandler {

    /**
     * INSTANCE
     */
    INSTANCE;

    @Override
    public void handleMetaPaintingPacket(MetaPaintingSpawnS2CPacket packet) {
        EntityType<?> entityType = packet.getEntityTypeId();
        Entity entity = entityType.create(MinecraftClient.getInstance().world);
        if (entity != null) {
            entity.onSpawnPacket(packet);
            int i = packet.getId();
            MinecraftClient.getInstance().world.addEntity(i, entity);
        }
    }
}
