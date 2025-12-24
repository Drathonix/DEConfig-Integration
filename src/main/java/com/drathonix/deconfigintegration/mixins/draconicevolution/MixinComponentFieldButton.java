package com.drathonix.deconfigintegration.mixins.draconicevolution;

import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.brandon3055.brandonscore.common.utills.DataUtills;
import com.brandon3055.draconicevolution.client.gui.componentguis.GUIToolConfig;
import com.brandon3055.draconicevolution.client.gui.guicomponents.ComponentFieldButton;
import com.brandon3055.draconicevolution.common.lib.References;
import com.brandon3055.draconicevolution.common.utills.IConfigurableItem;
import com.brandon3055.draconicevolution.common.utills.ItemConfigField;
import com.drathonix.deconfigintegration.bridge.EnumerableItemConfigField;

@Mixin(value = ComponentFieldButton.class)
public class MixinComponentFieldButton {

    @Shadow(remap = false)
    public ItemConfigField field;

    @Shadow(remap = false)
    public GUIToolConfig gui;

    @Inject(
        method = "mouseClicked",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/audio/SoundHandler;playSound(Lnet/minecraft/client/audio/ISound;)V",
            shift = At.Shift.AFTER),
        cancellable = true)
    public void deci$supportAdvancedFields(int x, int y, int button, CallbackInfo ci) {
        if (field instanceof EnumerableItemConfigField aicf) {
            if (aicf.representativeType == References.BOOLEAN_ID && aicf.datatype != References.BOOLEAN_ID) {
                aicf.value = !aicf.castToBoolean();
                aicf.value = aicf.castBooleanToInt();
                aicf.sendChanges();
                ItemStack stack = gui.player.inventory.getStackInSlot(field.slot);
                if (stack != null && stack.getItem() instanceof IConfigurableItem) {
                    DataUtills.writeObjectToCompound(
                        IConfigurableItem.ProfileHelper.getProfileCompound(stack),
                        field.value,
                        field.datatype,
                        field.name);
                }
                ci.cancel();
            } else if (aicf.representativeType == EnumerableItemConfigField.ENUM_REPR_ID) {
                aicf.value = aicf.nextEnumValue();
                aicf.sendChanges();
                ItemStack stack = gui.player.inventory.getStackInSlot(field.slot);
                if (stack != null && stack.getItem() instanceof IConfigurableItem) {
                    DataUtills.writeObjectToCompound(
                        IConfigurableItem.ProfileHelper.getProfileCompound(stack),
                        field.value,
                        field.datatype,
                        field.name);
                }
                ci.cancel();
            }
        }
    }
}
