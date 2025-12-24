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
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.drathonix.deconfigintegration.integration.ItemConfigFields;
import com.llamalad7.mixinextras.sugar.Local;

import fox.spiteful.avaritia.items.ItemArmorInfinity;

@Mixin(value = ItemArmorInfinity.abilityHandler.class)
public class MixinItemArmorInfinityAbilityHandler {

    /**
     * Allows toggling creative flight.
     *
     * @param instance the player capabilities.
     * @param value    the original value.
     * @param player   the target player.
     */
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
     * Allows modifying the infinity sanic speed boost for flight horizontal and global horizontal speed.
     *
     * @param in       the original speed value (unchanged with default settings)
     * @param flying   the player's flying state.
     * @param sneaking the player's sneaking state.
     * @param player   the target player.
     * @return the new speed boost value.
     */
    @ModifyVariable(
        method = "updatePlayerAbilityStatus",
        require = 1,
        at = @At(
            value = "FIELD",
            ordinal = 0,
            target = "Lnet/minecraft/entity/player/EntityPlayer;moveForward:F",
            shift = At.Shift.BEFORE,
            opcode = Opcodes.GETFIELD))
    protected float modifySpeedMulti(float in, @Local(ordinal = 4) boolean flying, @Local(ordinal = 6) boolean sneaking,
        @Local EntityPlayer player) {
        ItemStack boots = player.getCurrentArmor(0);
        return (float) (ItemConfigFields.getOrDefault(boots, "HorizontalSpeedBoost", 0.15D)
            * (flying ? ItemConfigFields.getOrDefault(boots, "FlightSpeedBoost", 1.1D) : 1.0D)
            * (sneaking ? 0.1f : 1.0f));
    }

    /**
     * Allows modifying the vertical flight speed multiplier.
     *
     * @param instance the target player.
     * @param value    the original value (discarded)
     */
    @Redirect(
        method = "updatePlayerAbilityStatus",
        require = 1,
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/entity/player/EntityPlayer;motionY:D",
            opcode = Opcodes.PUTFIELD))
    protected void modifyFlightVerticalSpeed(EntityPlayer instance, double value) {
        ItemStack boots = instance.getCurrentArmor(0);
        instance.motionY *= ItemConfigFields.getOrDefault(boots, "VerticalFlightSpeedMulti", 1.5D);
    }

    /**
     * Allows modifying the infinity armor step height.
     *
     * @param instance the target player.
     * @param value    the original value, discarded.
     * @param sneaking the player's sneaking state.
     */
    @Redirect(
        method = "updatePlayerAbilityStatus",
        require = 1,
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/entity/player/EntityPlayer;stepHeight:F",
            ordinal = 0,
            opcode = Opcodes.PUTFIELD))
    protected void modifyStepHeight(EntityPlayer instance, float value, @Local(ordinal = 6) boolean sneaking) {
        ItemStack boots = instance.getCurrentArmor(0);
        instance.stepHeight = (float) (ItemConfigFields.getOrDefault(boots, "StepHeight", 1.01D))
            * (sneaking ? 0.5F : 1F);
        System.out.println(instance.stepHeight);
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

    /**
     * Allows changing the jump height, maxes out at 4 blocks.
     *
     * @param event  the jump event.
     * @param ci     called to cancel the injected method.
     * @param player the jumping player.
     */
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
