package com.drathonix.deconfigintegration.mixins.avaritia;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.drathonix.deconfigintegration.integration.ItemConfigFields;

import fox.spiteful.avaritia.items.ItemArmorInfinity;

@Mixin(ItemArmorInfinity.class)
public class MixinItemArmorInfinityThaumcraft {

    @Inject(method = "showNodes", at = @At("RETURN"), cancellable = true, remap = false)
    public void interceptShowNodes(ItemStack itemStack, EntityLivingBase entityLivingBase,
        CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) {
            cir.setReturnValue(ItemConfigFields.enableGogglesOfRevealing(itemStack));
        }
    }
}
