package net.shirojr.archaicweaponry.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.util.Identifier;
import net.shirojr.archaicweaponry.ArchaicWeaponry;
import net.shirojr.archaicweaponry.registry.ArchaicWeaponryBlocks;
import net.shirojr.archaicweaponry.util.ArchaicWeaponryProperties;

public class ArchaicWeaponryModelProvider extends FabricModelProvider {
    public ArchaicWeaponryModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.blockStateCollector
                .accept(VariantsBlockStateSupplier.create(ArchaicWeaponryBlocks.GUILLOTINE)
                        .coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates())
                        .coordinate(createGuillotinePartMap()));
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

    }

    private BlockStateVariantMap createGuillotinePartMap() {
        return BlockStateVariantMap.create(ArchaicWeaponryProperties.GUILLOTINE_PART).register(part ->
                switch (part) {
                    case FOOT -> BlockStateVariant.create().put(VariantSettings.MODEL,
                            new Identifier(ArchaicWeaponry.MODID, "block/guillotine_foot"));
                    case HEAD -> BlockStateVariant.create().put(VariantSettings.MODEL,
                            new Identifier(ArchaicWeaponry.MODID, "block/guillotine_head"));
                    case MID -> BlockStateVariant.create().put(VariantSettings.MODEL,
                            new Identifier(ArchaicWeaponry.MODID, "block/guillotine_mid"));
                    case TOP -> BlockStateVariant.create().put(VariantSettings.MODEL,
                            new Identifier(ArchaicWeaponry.MODID, "block/guillotine_top"));
                }
        );
    }
}
