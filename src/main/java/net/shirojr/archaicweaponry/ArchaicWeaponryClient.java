package net.shirojr.archaicweaponry;

import net.fabricmc.api.ClientModInitializer;
import net.shirojr.archaicweaponry.registry.ArchaicWeaponryBlockEntityRenderers;

public class ArchaicWeaponryClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ArchaicWeaponryBlockEntityRenderers.registerRenderers();
    }
}
