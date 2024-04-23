package com.drathonix.deconfigintegration.mixins.emt;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;

import com.brandon3055.brandonscore.common.lib.References;
import com.brandon3055.draconicevolution.common.utills.ItemConfigField;
import com.drathonix.deconfigintegration.bridge.TranslatableItemConfigField;

import emt.item.armor.goggles.ItemNanoGoggles;

@Mixin(ItemNanoGoggles.class)
public class MixinItemNanoGoggles extends MixinItemElectricGoggles {

    @Override
    public List<ItemConfigField> getFields(ItemStack stack, int slot) {
        List<ItemConfigField> fields = new ArrayList<>();
        fields.add(new ItemConfigField(References.BOOLEAN_ID, slot, "GogglesOfRevealing").readFromItem(stack, true));
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
