package net.shirojr.archaicweaponry.block.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.shirojr.archaicweaponry.registry.ArchaicWeaponryBlockEntities;
import net.shirojr.archaicweaponry.util.LoggerUtil;
import org.jetbrains.annotations.Nullable;

public class GuillotineBlockEntity extends BlockEntity {
    private BlockPos originBlockPos;
    public GuillotineBlockEntity(BlockPos pos, BlockState state) {
        super(ArchaicWeaponryBlockEntities.GUILLOTINE_BLOCK_ENTITY, pos, state);
        LoggerUtil.devLogger("created BE at %s %s %s".formatted(pos.getX(), pos.getY(), pos.getZ()));
    }

    @Nullable
    public void setOriginBlockPos(BlockPos originBlockPos) {
        this.originBlockPos = originBlockPos;
        markDirty();
    }

    @Nullable
    public BlockPos getOriginBlockPos() {
        return this.originBlockPos;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        NbtCompound compound = nbt.getCompound("Origin");
        this.originBlockPos = new BlockPos(compound.getInt("x"), compound.getInt("y"), compound.getInt("z"));
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        NbtCompound compound = new NbtCompound();
        compound.putInt("x", this.originBlockPos.getX());
        compound.putInt("y", this.originBlockPos.getY());
        compound.putInt("z", this.originBlockPos.getZ());
        nbt.put("Origin", compound);
    }

    public static void tick(World world, BlockPos blockPos, BlockState state, GuillotineBlockEntity blockEntity) {

    }
}
