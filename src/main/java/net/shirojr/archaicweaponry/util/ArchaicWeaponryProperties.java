package net.shirojr.archaicweaponry.util;

import net.minecraft.state.property.EnumProperty;
import net.shirojr.archaicweaponry.util.helper.MultiBlockUtil;

public class ArchaicWeaponryProperties {
    public static final EnumProperty<MultiBlockUtil.GuillotinePart> GUILLOTINE_PART;

    static {
        GUILLOTINE_PART = EnumProperty.of("guillotine_part", MultiBlockUtil.GuillotinePart.class);
    }
}
