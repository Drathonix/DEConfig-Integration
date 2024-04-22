package com.drathonix.deconfigintegration.mixins.ic2;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.brandon3055.brandonscore.common.lib.References;
import com.brandon3055.draconicevolution.common.utills.IConfigurableItem;
import com.brandon3055.draconicevolution.common.utills.ItemConfigField;
import com.drathonix.deconfigintegration.bridge.AdvancedItemConfigField;
import com.drathonix.deconfigintegration.bridge.DEConfigurableExt;

import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import ic2.core.init.MainConfig;
import ic2.core.item.armor.ItemArmorQuantumSuit;
import ic2.core.util.ConfigUtil;

@Mixin(ItemArmorQuantumSuit.class)
public class MixinIC2QuantumDEIntegration implements DEConfigurableExt, IConfigurableItem {

    @Override
    public List<ItemConfigField> getFields(ItemStack stack, int slot) {
        int armorType = ((ItemArmorQuantumSuit) stack.getItem()).armorType;
        List<ItemConfigField> fields = new ArrayList<>();
        switch (armorType) {
            case 0 -> {
                fields.add(new ItemConfigField(References.BOOLEAN_ID, slot, "Nightvision").readFromItem(stack, false));
                fields.add(
                    new AdvancedItemConfigField(References.SHORT_ID, slot, "HudMode", "HudMode")
                        .representAsEnum(new String[] { "ic2.off", "ic2.on", "ic2.extended" })
                        .readFromItem(stack, (short) 0));
                return fields;
            }
            case 1 -> {
                fields.add(new ItemConfigField(References.BOOLEAN_ID, slot, "jetpack").readFromItem(stack, false));
                fields.add(new ItemConfigField(References.BOOLEAN_ID, slot, "hoverMode").readFromItem(stack, false));
                return fields;
            }
            case 2 -> {
                fields
                    .add(new ItemConfigField(References.BOOLEAN_ID, slot, "quantumSprint").readFromItem(stack, true));
                return fields;
            }
            default -> {
                return fields;
            }
        }
    }

    // Allow disabling Quantum Sprint through Draconic evolution GUI.
    @Inject(
        method = "onArmorTick",
        at = @At(
            value = "INVOKE",
            target = "Lic2/core/Platform;profilerStartSection(Ljava/lang/String;)V",
            ordinal = 2),
        locals = LocalCapture.CAPTURE_FAILSOFT,
        cancellable = true,
        remap = false)
    public void hodgepodge$useBetterLeggingsSprintLogic(World world, EntityPlayer player, ItemStack itemStack,
        CallbackInfo ci, NBTTagCompound nbtData) {
        boolean enableQuantumSpeedOnSprint;
        if (IC2.platform.isRendering()) {
            enableQuantumSpeedOnSprint = ConfigUtil.getBool(MainConfig.get(), "misc/quantumSpeedOnSprint")
                && nbtData.getBoolean("quantumSprint");
        } else {
            enableQuantumSpeedOnSprint = true;
        }

        if (ElectricItem.manager.canUse(itemStack, 1000.0) && (player.onGround || player.isInWater())
            && IC2.keyboard.isForwardKeyDown(player)
            && (enableQuantumSpeedOnSprint && player.isSprinting()
                || !enableQuantumSpeedOnSprint && IC2.keyboard.isBoostKeyDown(player))) {
            byte speedTicker = nbtData.getByte("speedTicker");
            ++speedTicker;
            if (speedTicker >= 10) {
                speedTicker = 0;
                ElectricItem.manager.use(itemStack, 1000.0, (EntityLivingBase) null);
            }

            nbtData.setByte("speedTicker", speedTicker);
            float speed = 0.22F;
            if (player.isInWater()) {
                speed = 0.1F;
                if (IC2.keyboard.isJumpKeyDown(player)) {
                    player.motionY += 0.10000000149011612;
                }
            }

            if (speed > 0.0F) {
                player.moveFlying(0.0F, 1.0F, speed);
            }
        }

        IC2.platform.profilerEndSection();
        // This is fine given the code executed after the case statement is just disabled by hodgepodge already.
        ci.cancel();
    }

    @Override
    public boolean hasProfiles() {
        return false;
    }
}
