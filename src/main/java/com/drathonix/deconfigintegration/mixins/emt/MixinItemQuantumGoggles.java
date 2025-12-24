package com.drathonix.deconfigintegration.mixins.emt;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;

import com.brandon3055.draconicevolution.common.utills.ItemConfigField;
import com.drathonix.deconfigintegration.integration.ItemConfigFields;

import emt.item.armor.goggles.ItemQuantumGoggles;

/**
 * Necessary due to a small difference with how the nano goggles store nightvision toggling.
 * 
 * @author Jack Andersen
 * @since 1.0.2
 */
@Mixin(ItemQuantumGoggles.class)
public class MixinItemQuantumGoggles extends MixinItemNanoGoggles {

    @Override
    public List<ItemConfigField> getFields(ItemStack stack, int slot) {
        List<ItemConfigField> fields = new ArrayList<>();
        ItemConfigFields.addGogglesOfRevealing(fields, stack, slot);
        ItemConfigFields.addNightVision(fields, stack, slot);
        return fields;
    }
}
