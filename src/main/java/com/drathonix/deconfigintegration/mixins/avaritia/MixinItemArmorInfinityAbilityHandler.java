package com.drathonix.deconfigintegration.mixins.avaritia;

import java.util.Set;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingEvent;

import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.drathonix.deconfigintegration.integration.ItemConfigFields;
import com.llamalad7.mixinextras.sugar.Local;

import fox.spiteful.avaritia.items.ItemArmorInfinity;

@Mixin(value = ItemArmorInfinity.abilityHandler.class)
public class MixinItemArmorInfinityAbilityHandler {

    @Redirect(
        method = "updatePlayerAbilityStatus",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/entity/player/PlayerCapabilities;allowFlying:Z",
            opcode = Opcodes.PUTFIELD,
            ordinal = 0),
        require = 1)
    protected void interceptFlight(PlayerCapabilities instance, boolean value, @Local EntityPlayer player) {
        ItemStack stack = player.getCurrentArmor(2);
        // The first condition should never occur, but I'm being safe.
        // The stack compound can be null though.
        if (stack != null && stack.stackTagCompound != null && stack.stackTagCompound.hasKey("flight")) {
            instance.allowFlying = stack.stackTagCompound.getBoolean("flight");
        } else {
            instance.allowFlying = value;
        }
    }

    /**
     * Adds night vision to the infinity helm. This is not included by Avaritia but almost every other super helmet in
     * the modpack has night vision, so I'm upgrading it. It's endgame so it deserves it.
     *
     * @param instance a set containing a list of player names who have the infinity helmet equipped.
     * @param object   the player name.
     * @param hasHat   whether the player has an infinity helmet equipped.
     * @param player   the player to apply buffs to.
     * @return true if the set contains the player name.
     * @param <T> String
     */
    @Redirect(
        method = "updatePlayerAbilityStatus",
        at = @At(value = "INVOKE", target = "Ljava/util/Set;contains(Ljava/lang/Object;)Z", ordinal = 0),
        remap = false,
        require = 1)
    public <T> boolean addNightVision(Set<T> instance, T object, @Local boolean hasHat, @Local EntityPlayer player) {
        if (instance.contains(object)) {
            if (hasHat) {
                ItemStack stack = player.getCurrentArmor(3);
                if (ItemConfigFields.enableNightVision(stack)) {
                    player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 300, 0, true));
                }
            }
            return true;
        }
        return false;
    }

    @Inject(
        method = "jumpBoost",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/entity/player/EntityPlayer;motionY:D",
            opcode = Opcodes.GETFIELD,
            ordinal = 0),
        cancellable = true,
        require = 1)
    protected void interceptJump(LivingEvent.LivingJumpEvent event, CallbackInfo ci, @Local EntityPlayer player) {
        ItemStack stack = player.getCurrentArmor(1);
        if (stack != null && stack.stackTagCompound != null) {
            player.motionY += stack.stackTagCompound.getDouble("ArmorJumpMult");
            ci.cancel();
        }
    }
}
