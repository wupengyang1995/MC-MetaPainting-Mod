package net.metacraft.mod.network;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;

public interface Packet {

    void onPacket();

    void toBuffer(PacketByteBuf buffer);

    default PacketByteBuf toBuffer() {
        PacketByteBuf buffer = PacketByteBufs.create();
        toBuffer(buffer);
        return buffer;
    }
}