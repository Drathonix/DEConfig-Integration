package com.drathonix.deconfigintegration.mixins.gravisuite;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;

import com.brandon3055.brandonscore.common.lib.References;
import com.brandon3055.draconicevolution.common.utills.IConfigurableItem;
import com.brandon3055.draconicevolution.common.utills.ItemConfigField;
import com.drathonix.deconfigintegration.bridge.AdvancedItemConfigField;
import com.drathonix.deconfigintegration.bridge.DEConfigurableExt;

import gravisuite.ItemAdvancedLappack;

@Mixin(ItemAdvancedLappack.class)
public class MixinItemAdvancedLappack implements DEConfigurableExt, IConfigurableItem {

    @Override
    public List<ItemConfigField> getFields(ItemStack stack, int slot) {

        List<ItemConfigField> fields = new ArrayList<>();
        fields.add(
            new AdvancedItemConfigField(References.INT_ID, slot, "toolMode", "gravisuite.Charging")
                .representAs(References.BOOLEAN_ID)
                .readFromItem(stack, 1)
                .setMinMaxAndIncromente(0, 1, 1));
        return fields;
    }

    @Override
    public boolean hasProfiles() {
        return false;
    }
}
