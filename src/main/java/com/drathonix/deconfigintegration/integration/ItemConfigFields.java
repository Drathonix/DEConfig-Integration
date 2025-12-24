package com.drathonix.deconfigintegration.integration;

import java.util.List;

import net.minecraft.item.ItemStack;

import com.brandon3055.brandonscore.common.lib.References;
import com.brandon3055.draconicevolution.common.utills.ItemConfigField;
import com.drathonix.deconfigintegration.DEConfigIntegration;
import com.drathonix.deconfigintegration.bridge.TranslatableItemConfigField;

public class ItemConfigFields {

    public static void addGogglesOfRevealingWithCheck(List<ItemConfigField> fields, ItemStack stack, int slot) {
        if (DEConfigIntegration.thaumcraft) {
            addGogglesOfRevealing(fields, stack, slot);
        }
    }

    public static void addGogglesOfRevealing(List<ItemConfigField> fields, ItemStack stack, int slot) {
        if (DEConfigIntegration.thaumcraft) {
            fields
                .add(new ItemConfigField(References.BOOLEAN_ID, slot, "GogglesOfRevealing").readFromItem(stack, true));
        }
    }

    public static void addNightVision(List<ItemConfigField> fields, ItemStack stack, int slot) {
        fields.add(
            new TranslatableItemConfigField(References.BOOLEAN_ID, slot, "Nightvision", "Nightvision")
                .readFromItem(stack, false));
    }

    public static boolean enableGogglesOfRevealing(ItemStack stack) {
        if (stack.hasTagCompound() && stack.stackTagCompound.hasKey("GogglesOfRevealing")) {
            return stack.stackTagCompound.getBoolean("GogglesOfRevealing");
        }
        return true;
    }

    public static void addIC2Active(List<ItemConfigField> fields, ItemStack stack, int slot, String translationKey) {
        fields.add(
            new TranslatableItemConfigField(References.BOOLEAN_ID, slot, "active", translationKey)
                .readFromItem(stack, false));
    }

    public static boolean enableNightVision(ItemStack stack) {
        if (stack.hasTagCompound() && stack.stackTagCompound.hasKey("Nightvision")) {
            return stack.stackTagCompound.getBoolean("Nightvision");
        }
        return false;
    }

    public static double getOrDefault(ItemStack stack, String key, double v) {
        if (stack != null && stack.hasTagCompound()
            && stack.getTagCompound()
                .hasKey(key)) {
            return stack.getTagCompound()
                .getDouble(key);
        }
        return v;
    }

    public static int getOrDefault(ItemStack stack, String key, int v) {
        if (stack != null && stack.hasTagCompound()
            && stack.getTagCompound()
                .hasKey(key)) {
            return stack.getTagCompound()
                .getInteger(key);
        }
        return v;
    }

    public static boolean getOrDefault(ItemStack stack, String key, boolean v) {
        if (stack != null && stack.hasTagCompound()
            && stack.getTagCompound()
                .hasKey(key)) {
            return stack.getTagCompound()
                .getBoolean(key);
        }
        return v;
    }
}
