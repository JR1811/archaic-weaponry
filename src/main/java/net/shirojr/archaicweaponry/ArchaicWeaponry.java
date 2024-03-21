package net.shirojr.archaicweaponry;

import net.fabricmc.api.ModInitializer;
import net.shirojr.archaicweaponry.block.ArchaicWeaponryBlocks;
import net.shirojr.archaicweaponry.blockentity.ArchaicWeaponryBlockEntities;
import net.shirojr.archaicweaponry.item.ArchaicWeaponryItemGroup;
import net.shirojr.archaicweaponry.item.ArchaicWeaponryItems;
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