package com.drathonix.deconfigintegration.mixins.avaritia;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;

import com.brandon3055.brandonscore.common.lib.References;
import com.brandon3055.draconicevolution.common.utills.IConfigurableItem;
import com.brandon3055.draconicevolution.common.utills.ItemConfigField;
import com.drathonix.deconfigintegration.bridge.DEConfigurableExt;
import com.drathonix.deconfigintegration.bridge.EnumerableItemConfigField;
import com.drathonix.deconfigintegration.integration.ItemConfigFields;

import fox.spiteful.avaritia.items.ItemArmorInfinity;

@Mixin(ItemArmorInfinity.class)
public class MixinItemArmorInfinity implements DEConfigurableExt, IConfigurableItem {

    @Override
    public List<ItemConfigField> getFields(ItemStack stack, int slot) {
        List<ItemConfigField> fields = new ArrayList<>();
        ItemArmor armor = (ItemArmor) stack.getItem();
        switch (armor.armorType) {
            // Helm
            case 0 -> {
                ItemConfigFields.addGogglesOfRevealingWithCheck(fields, stack, slot);
                ItemConfigFields.addNightVision(fields, stack, slot);
                return fields;
            }
            // Chest
            case 1 -> {
                fields.add(
                    new EnumerableItemConfigField(References.INT_ID, slot, "flight", "flight")
                        .representAs(References.BOOLEAN_ID)
                        .readFromItem(stack, 1)
                        .setMinMaxAndIncromente(0, 1, 1));
                return fields;
            }
            // Legs
            case 2 -> {
                fields.add(
                    new ItemConfigField(References.DOUBLE_ID, slot, "ArmorJumpMult").readFromItem(stack, 0.4D)
                        .setMinMaxAndIncromente(0D, 0.4D, 0.01D));
                return fields;
            }
            // Boots
            default -> {
                return fields;
            }
        }
    }

    @Override
    public boolean hasProfiles() {
        return false;
    }
}
