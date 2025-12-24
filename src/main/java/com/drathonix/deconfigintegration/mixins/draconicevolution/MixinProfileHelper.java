package com.drathonix.deconfigintegration.mixins.draconicevolution;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.brandon3055.draconicevolution.common.utills.IConfigurableItem;
import com.drathonix.deconfigintegration.bridge.DEConfigurableExt;

/**
 * This mixin returns the item's configurable tag instead of the profile tag if the item implements
 * {@link DEConfigurableExt}
 * 
 * @author Jack Andersen
 * @since 1.0.0
 */
@Mixin(IConfigurableItem.ProfileHelper.class)
public class MixinProfileHelper {

    // This should be moved to GTNH Draconic evo in the future but for now, this mixin works perfectly fine.
    // Needed to allow configuring items that don't have profile NBT (I.E. quantum armor)
    @Inject(
        method = "getProfileCompound",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/nbt/NBTTagCompound;hasKey(Ljava/lang/String;)Z",
            ordinal = 0,
            shift = At.Shift.BEFORE),
        cancellable = true)
    private static void deci$interceptForCustomItems(ItemStack stack, CallbackInfoReturnable<NBTTagCompound> cir) {
        if (stack.getItem() instanceof DEConfigurableExt customConfigurableItem) {
            cir.setReturnValue(customConfigurableItem.deci$getConfigurableTag(stack));
        }
    }
}
