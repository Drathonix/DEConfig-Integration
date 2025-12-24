package com.drathonix.deconfigintegration.mixins.emt;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;

import com.brandon3055.draconicevolution.common.utills.ItemConfigField;
import com.drathonix.deconfigintegration.integration.ItemConfigFields;

import emt.item.armor.goggles.ItemNanoGoggles;

/**
 * Allows toggling night vision and node vision.
 * 
 * @author Jack Andersen
 * @since 1.0.2
 */
@Mixin(ItemNanoGoggles.class)
public class MixinItemNanoGoggles extends MixinItemElectricGoggles {

    @Override
    public List<ItemConfigField> getFields(ItemStack stack, int slot) {
        List<ItemConfigField> fields = new ArrayList<>();
        ItemConfigFields.addGogglesOfRevealing(fields, stack, slot);
        ItemConfigFields.addIC2Active(fields, stack, slot, "Nightvision");
        return fields;
    }
}
