package net.shirojr.archaicweaponry.util.helper;

import net.minecraft.block.BlockState;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.shirojr.archaicweaponry.block.ArchaicWeaponryBlocks;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MultiBlockUtil {
    public static final List<Direction> HORIZONTAL_DIRECTIONS = Arrays.stream(Direction.values())
            .filter(direction -> direction.getAxis().isHorizontal()).toList();

    private MultiBlockUtil() {
    }

    public enum MultiBlockType implements StringIdentifiable {
        GUILLOTINE("guillotine"), BALLISTA("ballista");

        private final String id;

        MultiBlockType(String identifier) {
            this.id = identifier;
        }

        public static Optional<MultiBlockType> getType(World world, BlockPos blockPos) {
            BlockState blockState = world.getBlockState(blockPos);
            if (blockState.isOf(ArchaicWeaponryBlocks.GUILLOTINE)) return Optional.of(GUILLOTINE);
            else if (blockState.isOf(ArchaicWeaponryBlocks.BALLISTA)) return Optional.of(BALLISTA);
            return Optional.empty();
        }

        @Override
        public String asString() {
            return this.id;
        }
    }

    public enum GuillotinePart implements ArchaicWeaponryMultiBlock, StringIdentifiable {
        FOOT("foot"), HEAD("head"), MID("mid"), TOP("top");
        private final String id;

        GuillotinePart(String name) {
            this.id = name;
        }

        @Override
        public boolean isOrigin() {
            return this.equals(HEAD);
        }

        @Override
        public Optional<BlockPos> getOrigin(World world, BlockPos blockPos) {
            return switch (this) {
                case TOP -> Optional.of(blockPos.down(2));
                case MID -> Optional.of(blockPos.down());
                case HEAD -> Optional.of(blockPos);
                default -> {
                    for (Direction direction : HORIZONTAL_DIRECTIONS) {
                        if (world.getBlockState(blockPos.offset(direction)).isOf(ArchaicWeaponryBlocks.GUILLOTINE)) {
                            yield Optional.of(blockPos.offset(direction));
                        }
                    }
                    yield Optional.empty();
                }
            };
        }

        @Override
        public Optional<Direction> getDirection(World world, BlockPos blockPos) {
            if (!world.getBlockState(blockPos).isOf(ArchaicWeaponryBlocks.GUILLOTINE)) return Optional.empty();
            if (getOrigin(world, blockPos).isEmpty()) return Optional.empty();
            BlockPos originBlockPos = getOrigin(world, blockPos).get();
            for (Direction direction : HORIZONTAL_DIRECTIONS) {
                if (world.getBlockState(originBlockPos.offset(direction)).isOf(ArchaicWeaponryBlocks.GUILLOTINE)) {
                    return Optional.of(direction);
                }
            }
            return Optional.empty();
        }

        @Override
        public String asString() {
            return this.id;
        }

        public static Optional<GuillotinePart> fromString(String identifier) {
            for (GuillotinePart part : GuillotinePart.values()) {
                if (identifier.equals(part.asString())) {
                    return Optional.of(part);
                }
            }
            return Optional.empty();
        }
    }
}
