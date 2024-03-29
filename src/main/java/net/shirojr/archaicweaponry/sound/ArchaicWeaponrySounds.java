package net.shirojr.archaicweaponry.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.shirojr.archaicweaponry.ArchaicWeaponry;
import net.shirojr.archaicweaponry.util.LoggerUtil;

public class ArchaicWeaponrySounds {
    public static SoundEvent GUILLOTINE_EMPTY = of("guillotine_empty");
    public static SoundEvent GUILLOTINE_FLESH = of("guillotine_flesh");

    static SoundEvent of(String id) {
        Identifier identifier = new Identifier(ArchaicWeaponry.MODID, id);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }

    public static void initialize() {
        LoggerUtil.devLogger("Registering %s Sounds".formatted(ArchaicWeaponry.MODID));
    }
}
