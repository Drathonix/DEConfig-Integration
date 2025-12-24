
package com.drathonix.deconfigintegration.mixins.ic2;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import com.brandon3055.brandonscore.common.lib.References;
import com.brandon3055.draconicevolution.common.utills.IConfigurableItem;
import com.brandon3055.draconicevolution.common.utills.ItemConfigField;
import com.drathonix.deconfigintegration.bridge.DEConfigurableExt;
import com.drathonix.deconfigintegration.bridge.EnumerableItemConfigField;

import ic2.core.item.armor.ItemArmorNanoSuit;

@Mixin(ItemArmorNanoSuit.class)
public class MixinItemArmorNanoSuit implements DEConfigurableExt, IConfigurableItem {

    @Override
    public List<ItemConfigField> getFields(ItemStack stack, int slot) {
        int armorType = deci$asItemArmor().armorType;
        List<ItemConfigField> fields = new ArrayList<>();
        if (armorType == 0) {
            fields.add(new ItemConfigField(References.BOOLEAN_ID, slot, "Nightvision").readFromItem(stack, false));
            fields.add(
                new EnumerableItemConfigField(References.SHORT_ID, slot, "HudMode", "HudMode")
                    .representAsEnum(new String[] { "ic2.off", "ic2.on", "ic2.extended" })
                    .readFromItem(stack, (short) 0));
        }
        return fields;
    }

    @Unique
    public ItemArmor deci$asItemArmor() {
        return ItemArmor.class.cast(this);
    }

    @Override
    public boolean hasProfiles() {
        return false;
    }
}
