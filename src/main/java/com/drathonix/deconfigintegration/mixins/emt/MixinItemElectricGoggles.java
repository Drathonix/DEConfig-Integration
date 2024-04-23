package com.drathonix.deconfigintegration.mixins.emt;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.brandon3055.brandonscore.common.lib.References;
import com.brandon3055.draconicevolution.common.utills.IConfigurableItem;
import com.brandon3055.draconicevolution.common.utills.ItemConfigField;
import com.drathonix.deconfigintegration.bridge.DEConfigurableExt;

import emt.item.armor.goggles.ItemElectricGoggles;

@Mixin(ItemElectricGoggles.class)
public class MixinItemElectricGoggles implements DEConfigurableExt, IConfigurableItem {

    @Override
    public List<ItemConfigField> getFields(ItemStack stack, int slot) {
        List<ItemConfigField> fields = new ArrayList<>();
        fields.add(new ItemConfigField(References.BOOLEAN_ID, slot, "GogglesOfRevealing").readFromItem(stack, true));
        return fields;
    }

    @Override
    public boolean hasProfiles() {
        return false;
    }

    @Inject(method = "showNodes", at = @At("HEAD"), cancellable = true, remap = false)
    public void supportToggling(ItemStack itemstack, EntityLivingBase player, CallbackInfoReturnable<Boolean> cir) {
        if (itemstack.hasTagCompound() && itemstack.stackTagCompound.hasKey("GogglesOfRevealing")) {
            cir.setReturnValue(itemstack.stackTagCompound.getBoolean("GogglesOfRevealing"));
        }
    }
}
