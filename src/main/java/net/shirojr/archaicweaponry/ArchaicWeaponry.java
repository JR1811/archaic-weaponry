package net.shirojr.archaicweaponry;

import net.fabricmc.api.ModInitializer;

import net.shirojr.archaicweaponry.item.ArchaicWeaponryItemGroup;
import net.shirojr.archaicweaponry.item.ArchaicWeaponryItems;
import net.shirojr.archaicweaponry.sound.ArchaicWeaponrySounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArchaicWeaponry implements ModInitializer {
	public static final String MODID = "archaic-weaponry";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize() {
		ArchaicWeaponryItems.initialize();
		ArchaicWeaponryItemGroup.initialize();
		ArchaicWeaponrySounds.initialize();
		LOGGER.info("Hello Fabric world!");
	}
}