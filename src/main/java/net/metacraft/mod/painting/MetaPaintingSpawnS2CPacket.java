//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.metacraft.mod.painting;

import net.metacraft.mod.network.ClientNetworkCallbackImpl;
import net.metacraft.mod.network.Packet;
import net.minecraft.entity.decoration.painting.PaintingMotive;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

import java.util.UUID;

public class MetaPaintingSpawnS2CPacket extends EntitySpawnS2CPacket implements Packet {
    private final int id;
    private final UUID uuid;
    private final BlockPos pos;
    private final Direction facing;
    private final byte[] colors;
    private final int motiveId;

    public MetaPaintingSpawnS2CPacket(MetaPaintingEntity entity) {
        super(entity);
        this.id = entity.getId();
        this.uuid = entity.getUuid();
        this.pos = entity.getDecorationBlockPos();
        this.facing = entity.getHorizontalFacing();
        this.motiveId = Registry.PAINTING_MOTIVE.getRawId(entity.motive);
        this.colors = entity.getColors();
    }

    public MetaPaintingSpawnS2CPacket(PacketByteBuf buf) {
        super(buf);
        this.id = buf.readVarInt();
        this.uuid = buf.readUuid();
        this.pos = buf.readBlockPos();
        this.facing = Direction.fromHorizontal(buf.readUnsignedByte());
        this.motiveId = buf.readVarInt();
        this.colors = buf.readByteArray();
    }

    public void write(PacketByteBuf buf) {
        super.write(buf);
        buf.writeVarInt(this.id);
        buf.writeUuid(this.uuid);
        buf.writeBlockPos(this.pos);
        buf.writeByte(this.facing.getHorizontal());
        buf.writeVarInt(this.motiveId);
        buf.writeByteArray(this.colors);
    }


    public int getId() {
        return this.id;
    }

    public UUID getPaintingUuid() {
        return this.uuid;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public Direction getFacing() {
        return this.facing;
    }

    public PaintingMotive getMotive() {
        return Registry.PAINTING_MOTIVE.get(this.motiveId);
    }

    public byte[] getColors() {
        return colors;
    }

    @Override
    public void onPacket() {
        ClientNetworkCallbackImpl.INSTANCE.handleMetaPaintingPacket(this);
    }

    @Override
    public void toBuffer(PacketByteBuf buffer) {
        write(buffer);
    }
}
