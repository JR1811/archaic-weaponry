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
        if (!state.get(PART).isOrigin()) return BlockRenderType.MODEL;
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
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
}
