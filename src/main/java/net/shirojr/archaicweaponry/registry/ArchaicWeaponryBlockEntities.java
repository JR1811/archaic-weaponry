package net.shirojr.archaicweaponry.registry;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.shirojr.archaicweaponry.ArchaicWeaponry;
import net.shirojr.archaicweaponry.block.blockentity.GuillotineBlockEntity;
import net.shirojr.archaicweaponry.util.LoggerUtil;

public class ArchaicWeaponryBlockEntities {
    public static final BlockEntityType<GuillotineBlockEntity> GUILLOTINE_BLOCK_ENTITY =
            register("guillotine", GuillotineBlockEntity::new, ArchaicWeaponryBlocks.GUILLOTINE);
    public static final BlockEntityType<GuillotineBlockEntity> BALLISTA_BLOCK_ENTITY =
            register("ballista", GuillotineBlockEntity::new, ArchaicWeaponryBlocks.BALLISTA);

    private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType.BlockEntityFactory<? extends T> entityFactory, Block... blocks) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(ArchaicWeaponry.MODID, name),
                BlockEntityType.Builder.<T>create(entityFactory, blocks)
                        .build(null));
    }

    public static void initialize() {
        LoggerUtil.devLogger("Registering %s BlockEntities".formatted(ArchaicWeaponry.MODID));
    }
}
