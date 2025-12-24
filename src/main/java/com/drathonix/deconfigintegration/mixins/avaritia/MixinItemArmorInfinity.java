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

/**
 * Adds DECI support for infinity armor.
 * 
 * @since 1.0.2
 * @author Jack Andersen
 */
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
                fields.add(
                    new ItemConfigField(References.DOUBLE_ID, slot, "VerticalFlightSpeedMulti")
                        .readFromItem(stack, 0.5D)
                        .setMinMaxAndIncromente(1D, 1.5D, 0.01D));
                fields.add(
                    new ItemConfigField(References.DOUBLE_ID, slot, "StepHeight").readFromItem(stack, 1D)
                        .setMinMaxAndIncromente(0.501D, 1.01D, 0.1D));
                fields.add(
                    new ItemConfigField(References.DOUBLE_ID, slot, "FlightSpeedBoost").readFromItem(stack, 1.1D)
                        .setMinMaxAndIncromente(1D, 1.1D, 0.01D));
                fields.add(
                    new ItemConfigField(References.DOUBLE_ID, slot, "HorizontalSpeedBoost").readFromItem(stack, 0.15D)
                        .setMinMaxAndIncromente(0D, 0.15D, 0.01D));
                return fields;
            }
        }
    }

    @Override
    public boolean hasProfiles() {
        return false;
    }
}
