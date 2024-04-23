package com.drathonix.deconfigintegration.mixins.ic2;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;

import com.brandon3055.brandonscore.common.lib.References;
import com.brandon3055.draconicevolution.common.utills.IConfigurableItem;
import com.brandon3055.draconicevolution.common.utills.ItemConfigField;
import com.drathonix.deconfigintegration.bridge.DEConfigurableExt;
import com.drathonix.deconfigintegration.bridge.TranslatableItemConfigField;

import ic2.core.item.armor.ItemArmorNightvisionGoggles;

@Mixin(ItemArmorNightvisionGoggles.class)
public class MixinItemArmorNightvisionGoggles implements DEConfigurableExt, IConfigurableItem {

    @Override
    public List<ItemConfigField> getFields(ItemStack stack, int slot) {
        List<ItemConfigField> fields = new ArrayList<>();
        fields.add(
            new TranslatableItemConfigField(References.BOOLEAN_ID, slot, "active", "Nightvision")
                .readFromItem(stack, false));
        return fields;
    }

    @Override
    public boolean hasProfiles() {
        return false;
    }
}
