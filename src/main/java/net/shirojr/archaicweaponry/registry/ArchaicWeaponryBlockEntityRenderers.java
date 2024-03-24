package net.shirojr.archaicweaponry.registry;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.shirojr.archaicweaponry.ArchaicWeaponry;
import net.shirojr.archaicweaponry.block.blockentity.client.model.GuillotineBlockEntityModel;
import net.shirojr.archaicweaponry.block.blockentity.client.renderer.GuillotineBlockEntityRenderer;

public class ArchaicWeaponryBlockEntityRenderers {
    public static final EntityModelLayer GUILLOTINE_MODEL_LAYER = new EntityModelLayer(new Identifier(ArchaicWeaponry.MODID, "guillotine"), "main");
    public static void registerRenderers() {
        registerEntityLayers();
        BlockEntityRendererFactories.register(ArchaicWeaponryBlockEntities.GUILLOTINE_BLOCK_ENTITY, GuillotineBlockEntityRenderer::new);
    }

    public static void registerEntityLayers() {
        EntityModelLayerRegistry.registerModelLayer(GUILLOTINE_MODEL_LAYER, GuillotineBlockEntityModel::getTexturedModelData);
    }
}
