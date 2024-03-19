package net.shirojr.archaicweaponry.item;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.shirojr.archaicweaponry.ArchaicWeaponry;
import net.shirojr.archaicweaponry.util.LoggerUtil;

public class ArchaicWeaponryItems {


    private static <T extends Item> T registerItem(String name, T item) {
        Identifier identifier = new Identifier(ArchaicWeaponry.MODID, name);
        return Registry.register(Registries.ITEM, identifier, item);
    }

    public static void initialize() {
        LoggerUtil.devLogger("Registering %s Items".formatted(ArchaicWeaponry.MODID));
    }
}
