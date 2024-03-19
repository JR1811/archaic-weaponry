package net.shirojr.archaicweaponry.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.shirojr.archaicweaponry.ArchaicWeaponry;
import net.shirojr.archaicweaponry.block.custom.BallistaBlock;
import net.shirojr.archaicweaponry.block.custom.GuillotineBlock;
import net.shirojr.archaicweaponry.util.LoggerUtil;

@SuppressWarnings("SameParameterValue")
public class ArchaicWeaponryBlocks {
    public static final GuillotineBlock GUILLOTINE = register("guillotine",
            new GuillotineBlock(FabricBlockSettings.copyOf(Blocks.SPRUCE_LOG)), true);

    public static final BallistaBlock BALLISTA = register("ballista",
            new BallistaBlock(FabricBlockSettings.copyOf(Blocks.SPRUCE_LOG)), true);

    private static <T extends Block> T register(String name, T block, boolean registerItem) {
        Identifier identifier = new Identifier(ArchaicWeaponry.MODID, name);
        if (registerItem) {
            Registry.register(Registries.ITEM, identifier, new BlockItem(block, new FabricItemSettings()));
        }
        return Registry.register(Registries.BLOCK, identifier, block);
    }

    public static void initialize() {
        LoggerUtil.devLogger("Registering %s Blocks".formatted(ArchaicWeaponry.MODID));
    }
}
