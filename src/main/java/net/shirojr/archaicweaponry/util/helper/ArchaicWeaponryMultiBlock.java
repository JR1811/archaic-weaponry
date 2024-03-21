package net.shirojr.archaicweaponry.util.helper;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Optional;

public interface ArchaicWeaponryMultiBlock {
    boolean isOrigin();

    Optional<BlockPos> getOrigin(World world, BlockPos blockPos);

    Optional<Direction> getDirection(World world, BlockPos blockPos);
}
