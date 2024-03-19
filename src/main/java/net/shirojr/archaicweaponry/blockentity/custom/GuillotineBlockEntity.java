package net.shirojr.archaicweaponry.blockentity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.shirojr.archaicweaponry.blockentity.ArchaicWeaponryBlockEntities;

public class GuillotineBlockEntity extends BlockEntity {
    public GuillotineBlockEntity(BlockPos pos, BlockState state) {
        super(ArchaicWeaponryBlockEntities.GUILLOTINE_BLOCK_ENTITY, pos, state);
    }
}
