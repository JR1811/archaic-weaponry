package net.shirojr.archaicweaponry;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.shirojr.archaicweaponry.datagen.provider.ArchaicWeaponryModelProvider;

public class ArchaicWeaponryDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(ArchaicWeaponryModelProvider::new);
    }
}
