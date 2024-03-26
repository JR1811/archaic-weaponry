package net.shirojr.archaicweaponry;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.shirojr.archaicweaponry.registry.ArchaicWeaponryBlockEntityRenderers;
import net.shirojr.archaicweaponry.registry.ArchaicWeaponryBlocks;

public class ArchaicWeaponryClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ArchaicWeaponryBlockEntityRenderers.registerRenderers();
        BlockRenderLayerMap.INSTANCE.putBlock(ArchaicWeaponryBlocks.GUILLOTINE, RenderLayer.getCutout());
    }
}
