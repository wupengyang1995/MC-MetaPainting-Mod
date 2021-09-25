package net.metacraft.mod;

import net.metacraft.mod.painting.MetaPaintingEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;

public final class MetaEntityType {
    private static String ENTITY_TYPE_ID = "entity_meta_painting";

    public static EntityType<MetaPaintingEntity> ENTITY_TYPE_META_PAINTING = null;

    public static void init() {
        ENTITY_TYPE_META_PAINTING = Registry.register(
                Registry.ENTITY_TYPE,
                ENTITY_TYPE_ID,
                EntityType.Builder.create(MetaPaintingEntity::new, SpawnGroup.MISC).setDimensions(0.5F, 0.5F).maxTrackingRange(10).trackingTickInterval(2147483647).build(ENTITY_TYPE_ID));

    }
}
