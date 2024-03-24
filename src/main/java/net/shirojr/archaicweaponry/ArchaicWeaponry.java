package net.shirojr.archaicweaponry;

import net.fabricmc.api.ModInitializer;
import net.shirojr.archaicweaponry.registry.ArchaicWeaponryBlocks;
import net.shirojr.archaicweaponry.registry.ArchaicWeaponryBlockEntities;
import net.shirojr.archaicweaponry.registry.ArchaicWeaponryItemGroup;
import net.shirojr.archaicweaponry.registry.ArchaicWeaponryItems;
import net.shirojr.archaicweaponry.sound.ArchaicWeaponrySounds;
import net.shirojr.archaicweaponry.util.LoggerUtil;

public class ArchaicWeaponry implements ModInitializer {
    public static final String MODID = "archaic-weaponry";

    @Override
    public void onInitialize() {
        ArchaicWeaponryItems.initialize();
        ArchaicWeaponryItemGroup.initialize();
        ArchaicWeaponryBlocks.initialize();
        ArchaicWeaponryBlockEntities.initialize();
        ArchaicWeaponrySounds.initialize();

        LoggerUtil.LOGGER.info("Chop Chop!");
    }
}