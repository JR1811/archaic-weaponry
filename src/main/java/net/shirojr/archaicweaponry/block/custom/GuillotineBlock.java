package net.shirojr.archaicweaponry.block.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.shirojr.archaicweaponry.blockentity.custom.GuillotineBlockEntity;
import net.shirojr.archaicweaponry.util.ArchaicWeaponryProperties;
import net.shirojr.archaicweaponry.util.helper.MultiBlockUtil;
import org.jetbrains.annotations.Nullable;

public class GuillotineBlock extends BlockWithEntity {
    public static final EnumProperty<MultiBlockUtil.GuillotinePart> PART = ArchaicWeaponryProperties.GUILLOTINE_PART;

    public GuillotineBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getDefaultState().with(PART, MultiBlockUtil.GuillotinePart.HEAD));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        if (!state.get(ArchaicWeaponryProperties.GUILLOTINE_PART).equals(MultiBlockUtil.GuillotinePart.HEAD))
            return null;
        return new GuillotineBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return super.getTicker(world, state, type);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return super.canPlaceAt(state, world, pos);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
    }
}
