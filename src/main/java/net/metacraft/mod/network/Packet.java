package net.metacraft.mod.network;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;


public interface Packet<P extends PlayerEntity> {

    void handle(P sender);

    void toBuffer(PacketByteBuf buffer);

    default PacketByteBuf toBuffer() {
        PacketByteBuf buf = PacketByteBufs.create();
        toBuffer(buf);
        return buf;
    }
}