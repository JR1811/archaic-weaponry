package net.shirojr.archaicweaponry.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.shirojr.archaicweaponry.registry.ArchaicWeaponryBlocks;
import net.shirojr.archaicweaponry.registry.ArchaicWeaponryBlockEntities;
import net.shirojr.archaicweaponry.block.blockentity.GuillotineBlockEntity;
import net.shirojr.archaicweaponry.util.ArchaicWeaponryProperties;
import net.shirojr.archaicweaponry.util.helper.MultiBlockUtil;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GuillotineBlock extends BlockWithEntity {
    public static final EnumProperty<MultiBlockUtil.GuillotinePart> PART = ArchaicWeaponryProperties.GUILLOTINE_PART;
    public static final DirectionProperty DIRECTION = Properties.HORIZONTAL_FACING;

    public GuillotineBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getDefaultState().with(PART, MultiBlockUtil.GuillotinePart.HEAD).with(DIRECTION, Direction.NORTH));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PART, DIRECTION);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GuillotineBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ArchaicWeaponryBlockEntities.GUILLOTINE_BLOCK_ENTITY, GuillotineBlockEntity::tick);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.empty();
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        for (Direction direction : MultiBlockUtil.HORIZONTAL_DIRECTIONS) {
            BlockPos testPosition = pos.offset(direction);
            if (!world.getBlockState(testPosition).isReplaceable()) return false;
            testPosition = testPosition.offset(Direction.DOWN);
            if (!world.getBlockState(testPosition).isSideSolidFullSquare(world, testPosition, Direction.UP))
                return false;
        }
        if (!world.getBlockState(pos.offset(Direction.UP, 1)).isReplaceable()) return false;
        return world.getBlockState(pos.offset(Direction.UP, 2)).isReplaceable();
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if (world.isClient()) return;
        Direction direction = placer == null ? Direction.NORTH : placer.getHorizontalFacing();
        world.setBlockState(pos, state.with(DIRECTION, direction));
        placeAdditionalBlock(world, pos.offset(direction), pos, direction, state, MultiBlockUtil.GuillotinePart.FOOT);
        placeAdditionalBlock(world, pos.offset(Direction.UP, 1), pos, direction, state, MultiBlockUtil.GuillotinePart.MID);
        placeAdditionalBlock(world, pos.offset(Direction.UP, 2), pos, direction, state, MultiBlockUtil.GuillotinePart.TOP);
        if (world.getBlockEntity(pos) instanceof GuillotineBlockEntity guillotineBlockEntity) {
            guillotineBlockEntity.setOriginBlockPos(pos);
        }
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
        if (world.isClient()) return;
        if (!(world.getBlockEntity(pos) instanceof GuillotineBlockEntity guillotineBlockEntity)) return;
        if (guillotineBlockEntity.getOriginBlockPos() == null) return;
        BlockPos originPos = guillotineBlockEntity.getOriginBlockPos();
        List<BlockPos> blockPosList = new ArrayList<>();
        blockPosList.add(originPos);
        blockPosList.add(originPos.offset(state.get(DIRECTION)));
        blockPosList.add(originPos.offset(Direction.UP, 1));
        blockPosList.add(originPos.offset(Direction.UP, 2));
        for (BlockPos blockPos : blockPosList) {
            if (blockPos.equals(pos)) continue;
            removeBlock(world, blockPos);
        }
    }

    private void placeAdditionalBlock(World world, BlockPos pos, BlockPos originPos, Direction direction, BlockState state, MultiBlockUtil.GuillotinePart part) {
        world.setBlockState(pos, ArchaicWeaponryBlocks.GUILLOTINE.getDefaultState().with(PART, part).with(DIRECTION, direction));
        world.updateNeighbors(pos, Blocks.AIR);
        state.updateNeighbors(world, pos, Block.NOTIFY_ALL);
        if (!(world.getBlockEntity(pos) instanceof GuillotineBlockEntity guillotineBlockEntity)) return;
        guillotineBlockEntity.setOriginBlockPos(originPos);
    }

    private void removeBlock(World world, BlockPos pos) {
        world.setBlockState(pos, Blocks.AIR.getDefaultState(), NOTIFY_ALL);
        world.updateNeighbors(pos, Blocks.AIR);
        world.getBlockState(pos).updateNeighbors(world, pos, Block.NOTIFY_ALL);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape shape = VoxelShapes.empty();
        switch (state.get(PART)) {
            case FOOT -> {
                switch (state.get(DIRECTION)) {
                    case NORTH, SOUTH -> shape = VoxelShapes.union(shape,
                            VoxelShapes.cuboid(0.0625, 0.25, 0, 0.9375, 0.5, 1));
                    case EAST, WEST -> shape = VoxelShapes.union(shape,
                            VoxelShapes.cuboid(0, 0.25, 0.0625, 1, 0.5, 0.9375));
                }
            }
            case HEAD -> {
                switch (state.get(DIRECTION)) {
                    case NORTH -> {
                        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0.25, 0, 0.9375, 0.5, 1));
                        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.046875, 0, 0.859375, 0.203125, 1.0625, 1.015625));
                        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.796875, 0, 0.859375, 0.953125, 1.0625, 1.015625));
                        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.203125, 0.5, 0.890625, 0.796875, 1.0625, 0.921875));
                    }
                    case EAST -> {
                        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0.25, 0.0625, 1, 0.5, 0.9375));
                        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(-0.015625, 0, 0.046875, 0.140625, 1.0625, 0.203125));
                        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(-0.015625, 0, 0.796875, 0.140625, 1.0625, 0.953125));
                        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.078125, 0.5, 0.203125, 0.109375, 1.0625, 0.796875));
                    }
                    case SOUTH -> {
                        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0.25, 0, 0.9375, 0.5, 1));
                        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.796875, 0, -0.015625, 0.953125, 1.0625, 0.140625));
                        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.046875, 0, -0.015625, 0.203125, 1.0625, 0.140625));
                        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.203125, 0.5, 0.078125, 0.796875, 1.0625, 0.109375));
                    }
                    case WEST -> {
                        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0.25, 0.0625, 1, 0.5, 0.9375));
                        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.859375, 0, 0.796875, 1.015625, 1.0625, 0.953125));
                        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.859375, 0, 0.046875, 1.015625, 1.0625, 0.203125));
                        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.890625, 0.5, 0.203125, 0.921875, 1.0625, 0.796875));
                    }
                }
            }
            case MID -> {
                switch (state.get(DIRECTION)) {
                    case NORTH -> {
                        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.046875, 0.0625, 0.859375, 0.203125, 1, 1.015625));
                        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.796875, 0.0625, 0.859375, 0.953125, 1, 1.015625));
                    }
                    case EAST -> {
                        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(-0.015625, 0.0625, 0.046875, 0.140625, 1, 0.203125));
                        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(-0.015625, 0.0625, 0.796875, 0.140625, 1, 0.953125));
                    }
                    case SOUTH -> {
                        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.796875, 0.0625, -0.015625, 0.953125, 1, 0.140625));
                        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.046875, 0.0625, -0.015625, 0.203125, 1, 0.140625));
                    }
                    case WEST -> {
                        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.859375, 0.0625, 0.796875, 1.015625, 1, 0.953125));
                        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.859375, 0.0625, 0.046875, 1.015625, 1, 0.203125));
                    }
                }
            }
            case TOP -> {
                switch (state.get(DIRECTION)) {
                    case NORTH -> {
                        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.046875, 0, 0.859375, 0.203125, 0.8125, 1.015625));
                        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.796875, 0, 0.859375, 0.953125, 0.8125, 1.015625));
                        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0.78125, 0.840625, 1, 0.9375, 1.0281250000000002));
                    }
                    case EAST -> {
                        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(-0.015625, 0, 0.046875, 0.140625, 0.8125, 0.203125));
                        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(-0.015625, 0, 0.796875, 0.140625, 0.8125, 0.953125));
                        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(-0.028125000000000178, 0.78125, 0, 0.15937500000000004, 0.9375, 1));
                    }
                    case SOUTH -> {
                        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.796875, 0, -0.015625, 0.953125, 0.8125, 0.140625));
                        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.046875, 0, -0.015625, 0.203125, 0.8125, 0.140625));
                        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0.78125, -0.028125000000000178, 1, 0.9375, 0.15937500000000004));
                    }
                    case WEST -> {
                        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.859375, 0, 0.796875, 1.015625, 0.8125, 0.953125));
                        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.859375, 0, 0.046875, 1.015625, 0.8125, 0.203125));
                        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.840625, 0.78125, 0, 1.0281250000000002, 0.9375, 1));
                    }
                }
            }
        }
        return shape;
    }
}
