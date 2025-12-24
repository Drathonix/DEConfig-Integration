package com.drathonix.deconfigintegration.mixins.witchinggadgets;

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
import com.drathonix.deconfigintegration.bridge.TranslatableItemConfigField;

import witchinggadgets.WitchingGadgets;
import witchinggadgets.common.items.armor.ItemPrimordialArmor;

@Mixin(ItemPrimordialArmor.class)
public class MixinItemPrimordialArmor implements DEConfigurableExt, IConfigurableItem {

    @Override
    public List<ItemConfigField> getFields(ItemStack stack, int slot) {
        List<ItemConfigField> fields = new ArrayList<>();
        if (ItemArmor.class.cast(this).armorType == 3 && WitchingGadgets.isBootsActive) {
            fields.add(
                new TranslatableItemConfigField(References.BOOLEAN_ID, slot, "omni", "boots.omni")
                    .readFromItem(stack, true));
            fields.add(
                new EnumerableItemConfigField(References.DOUBLE_ID, slot, "speed", "boots.speedPercentage")
                    .setMinMaxAndIncromente(0D, 1D, 0.05D)
                    .readFromItem(stack, 0.5D));
            fields.add(
                new EnumerableItemConfigField(References.DOUBLE_ID, slot, "jump", "boots.jumpPercentage")
                    .setMinMaxAndIncromente(0D, 1D, 0.05D)
                    .readFromItem(stack, 0.5D));
        }
        fields.add(
            new EnumerableItemConfigField(References.INT_ID, slot, "currentMode", "wg.desc.primal")
                .representAsEnum(new String[] { "deci.none", "Aer", "Terra", "Ignis", "Aqua", "Ordo", "Perditio" })
                .useKeyDirectly()
                .setMinMaxAndIncromente(0, 6, 1)
                .readFromItem(stack, 0));
        return fields;
    }

    @Override
    public boolean hasProfiles() {
        return false;
    }

}
