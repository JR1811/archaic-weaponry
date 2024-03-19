package net.shirojr.archaicweaponry.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.shirojr.archaicweaponry.ArchaicWeaponry;
import net.shirojr.archaicweaponry.util.LoggerUtil;

import java.util.List;

@SuppressWarnings("SameParameterValue")
public class ArchaicWeaponryItemGroup {
    public static RegistryKey<ItemGroup> ARCHAIC_WEAPONRY_ITEMGROUP = registerItemGroup("archaic_weaponry",
            Text.translatable("itemgroup.archaic_weaponry"), new ItemStack(Items.WOODEN_SWORD));


    private static RegistryKey<ItemGroup> registerItemGroup(String name, Text displayName, ItemStack displayStack) {
        ItemGroup group = FabricItemGroup.builder().icon(() -> displayStack).displayName(displayName).build();
        Identifier groupIdentifier = new Identifier(ArchaicWeaponry.MODID, name);
        Registry.register(Registries.ITEM_GROUP, groupIdentifier, group);
        return RegistryKey.of(RegistryKeys.ITEM_GROUP, groupIdentifier);
    }

    private static void initializeItemGroups() {
        ItemGroupEvents.modifyEntriesEvent(ARCHAIC_WEAPONRY_ITEMGROUP).register(archaicWeaponryEntries -> {
            List<ItemStack> boatismList = Registries.ITEM.stream()
                    /*.filter(item -> {
                                List<Item> ungroupedItems = List.of();  // <-- put items in here to ungroup
                                if (!Registries.ITEM.getId(item).getNamespace().equals(ArchaicWeaponry.MODID)) return false;
                                return !ungroupedItems.contains(item);
                            }
                    )*/
                    .map(Item::getDefaultStack).toList();
            archaicWeaponryEntries.addAll(boatismList);
        });
    }

    public static void initialize() {
        LoggerUtil.devLogger("Registering " + ArchaicWeaponry.MODID + " ItemGroups");
        initializeItemGroups();
    }
}
