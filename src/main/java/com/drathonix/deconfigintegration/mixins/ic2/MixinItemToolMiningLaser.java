package com.drathonix.deconfigintegration.mixins.ic2;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;

import com.brandon3055.brandonscore.common.lib.References;
import com.brandon3055.draconicevolution.common.utills.IConfigurableItem;
import com.brandon3055.draconicevolution.common.utills.ItemConfigField;
import com.drathonix.deconfigintegration.bridge.DEConfigurableExt;
import com.drathonix.deconfigintegration.bridge.EnumerableItemConfigField;

import ic2.core.item.tool.ItemToolMiningLaser;

@Mixin(ItemToolMiningLaser.class)
public class MixinItemToolMiningLaser implements DEConfigurableExt, IConfigurableItem {

    @Override
    public List<ItemConfigField> getFields(ItemStack stack, int slot) {
        List<ItemConfigField> fields = new ArrayList<>();
        fields.add(
            new EnumerableItemConfigField(References.INT_ID, slot, "laserSetting", "ic2.laserSetting")
                .representAsEnum(
                    new String[] { "ic2.mode.mining", "ic2.mode.lowFocus", "ic2.mode.longRange", "ic2.mode.horizontal",
                        "ic2.mode.superHeat", "ic2.mode.scatter" })
                .readFromItem(stack, false));
        return fields;
    }

    @Override
    public boolean hasProfiles() {
        return false;
    }
}
