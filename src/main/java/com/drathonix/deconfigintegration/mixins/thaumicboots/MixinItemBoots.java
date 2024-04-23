package com.drathonix.deconfigintegration.mixins.thaumicboots;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;

import com.brandon3055.brandonscore.common.lib.References;
import com.brandon3055.draconicevolution.common.utills.IConfigurableItem;
import com.brandon3055.draconicevolution.common.utills.ItemConfigField;
import com.drathonix.deconfigintegration.bridge.AdvancedItemConfigField;
import com.drathonix.deconfigintegration.bridge.DEConfigurableExt;

import thaumicboots.api.ItemBoots;

@Mixin(ItemBoots.class)
public class MixinItemBoots implements DEConfigurableExt, IConfigurableItem {

    @Override
    public List<ItemConfigField> getFields(ItemStack stack, int slot) {
        List<ItemConfigField> fields = new ArrayList<>();
        fields.add(new ItemConfigField(References.BOOLEAN_ID, slot, ItemBoots.TAG_MOD_OMNI).readFromItem(stack, true));
        fields.add(
            new AdvancedItemConfigField(References.DOUBLE_ID, slot, "speed", "boots.speedPercentage")
                .setMinMaxAndIncromente(0D, 1D, 0.05D)
                .readFromItem(stack, 0.5D));
        fields.add(
            new AdvancedItemConfigField(References.DOUBLE_ID, slot, "jump", "boots.jumpPercentage")
                .setMinMaxAndIncromente(0D, 1D, 0.05D)
                .readFromItem(stack, 0.5D));
        return fields;
    }

    @Override
    public boolean hasProfiles() {
        return false;
    }
}
