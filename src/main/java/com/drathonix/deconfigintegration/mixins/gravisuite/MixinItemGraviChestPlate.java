package com.drathonix.deconfigintegration.mixins.gravisuite;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;

import com.brandon3055.brandonscore.common.lib.References;
import com.brandon3055.draconicevolution.common.utills.IConfigurableItem;
import com.brandon3055.draconicevolution.common.utills.ItemConfigField;
import com.drathonix.deconfigintegration.bridge.DEConfigurableExt;

import gravisuite.ItemGraviChestPlate;

@Mixin(ItemGraviChestPlate.class)
public class MixinItemGraviChestPlate implements DEConfigurableExt, IConfigurableItem {

    @Override
    public List<ItemConfigField> getFields(ItemStack stack, int slot) {
        List<ItemConfigField> fields = new ArrayList<>();
        fields.add(new ItemConfigField(References.BOOLEAN_ID, slot, "isFlyActive").readFromItem(stack, false));
        fields.add(new ItemConfigField(References.BOOLEAN_ID, slot, "isLevitationActive").readFromItem(stack, false));
        return fields;
    }

    @Override
    public boolean hasProfiles() {
        return false;
    }
}
