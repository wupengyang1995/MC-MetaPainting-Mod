package net.metacraft.mod.Mixin;

import net.metacraft.mod.PaintingModInitializer;
import net.metacraft.mod.painting.MetaPaintingEntity;
import net.metacraft.mod.painting.MetaPaintingSpawnS2CPacket;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.MovingMinecartSoundInstance;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.network.packet.s2c.play.PaintingSpawnS2CPacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.network.ClientPlayNetworkHandler.class)
public class MixinClientPlayNetworkHandler{

    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    private ClientWorld world;

    /**
     * @author wupengyang
     * @reason 1
     */
    @Overwrite
    public void onEntitySpawn(EntitySpawnS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, (net.minecraft.client.network.ClientPlayNetworkHandler)(Object)this, client);
        EntityType<?> entityType = packet.getEntityTypeId();
        Entity entity = entityType.create(this.world);
        if (entity != null) {
            if (packet instanceof MetaPaintingSpawnS2CPacket) {
                System.out.println("MixinClientPlayNetworkHandler MCPaintingSpawnS2CPacket " + ((MetaPaintingSpawnS2CPacket) packet).getPos().toShortString());
                entity = new MetaPaintingEntity(PaintingModInitializer.ENTITY_TYPE_META_PAINTING, this.world);
                ((MetaPaintingEntity)entity).setAttachmentPos(((MetaPaintingSpawnS2CPacket) packet).getPos());
                ((MetaPaintingEntity)entity).setFacing(((MetaPaintingSpawnS2CPacket) packet).getFacing());
                ((MetaPaintingEntity)entity).setMotive(((MetaPaintingSpawnS2CPacket) packet).getMotive());
                entity.setUuid(packet.getUuid());
            }
            int i = packet.getId();
            // onSpawnPacket不能提前
            entity.onSpawnPacket(packet);
            this.world.addEntity(i, entity);
            if (entity instanceof AbstractMinecartEntity) {
                this.client.getSoundManager().play(new MovingMinecartSoundInstance((AbstractMinecartEntity)entity));
            }
        }
    }
    @Inject(at = @At("RETURN"), method = "onPaintingSpawn")
    public void onPaintingSpawn(PaintingSpawnS2CPacket packet, CallbackInfo ci) {
        System.out.println("MixinClientPlayNetworkHandler onPaintingSpawn " +  packet.getPos().toShortString());
    }
}
