
package com.drathonix.deconfigintegration.mixins.ic2;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;

import com.brandon3055.brandonscore.common.lib.References;
import com.brandon3055.draconicevolution.common.utills.IConfigurableItem;
import com.brandon3055.draconicevolution.common.utills.ItemConfigField;
import com.drathonix.deconfigintegration.bridge.AdvancedItemConfigField;
import com.drathonix.deconfigintegration.bridge.DEConfigurableExt;

import ic2.core.item.armor.ItemArmorNanoSuit;

@Mixin(ItemArmorNanoSuit.class)
public class MixinIC2NanoSuitDEIntegration implements DEConfigurableExt, IConfigurableItem {

    @Override
    public List<ItemConfigField> getFields(ItemStack stack, int slot) {
        int armorType = ((ItemArmorNanoSuit) stack.getItem()).armorType;
        List<ItemConfigField> fields = new ArrayList<>();
        if (armorType == 0) {
            fields.add(new ItemConfigField(References.BOOLEAN_ID, slot, "Nightvision").readFromItem(stack, false));
            fields.add(
                new AdvancedItemConfigField(References.SHORT_ID, slot, "HudMode", "HudMode")
                    .representAsEnum(new String[] { "ic2.off", "ic2.on", "ic2.extended" })
                    .readFromItem(stack, (short) 0));
        }
        return fields;
    }

    @Override
    public boolean hasProfiles() {
        return false;
    }
}
