package net.metacraft.mod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.metacraft.mod.painting.MetaPaintingEntityRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class ExampleClientMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(PaintingModInitializer.ENTITY_TYPE_META_PAINTING, (context) -> new MetaPaintingEntityRenderer(context));
    }
}