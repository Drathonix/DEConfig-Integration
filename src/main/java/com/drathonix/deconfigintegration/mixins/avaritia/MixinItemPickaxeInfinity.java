package com.drathonix.deconfigintegration.mixins.avaritia;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import com.brandon3055.brandonscore.common.lib.References;
import com.brandon3055.draconicevolution.common.utills.IConfigurableItem;
import com.brandon3055.draconicevolution.common.utills.ItemConfigField;
import com.drathonix.deconfigintegration.bridge.DEConfigurableExt;
import com.drathonix.deconfigintegration.bridge.EnumerableItemConfigField;
import com.drathonix.deconfigintegration.integration.ItemConfigFields;
import com.llamalad7.mixinextras.sugar.Local;

import fox.spiteful.avaritia.items.tools.ItemPickaxeInfinity;

/**
 * Not Yet Implemented, the intended plan is to change this to work similar to the staff of power as that would make it
 * actually useful. Currently the tool just breaks a 16x9x16 space around the player. Which is just too much.
 */
@Mixin(ItemPickaxeInfinity.class)
public class MixinItemPickaxeInfinity implements DEConfigurableExt, IConfigurableItem {

    @Override
    public List<ItemConfigField> getFields(ItemStack stack, int slot) {
        List<ItemConfigField> fields = new ArrayList<>();
        fields.add(new ItemConfigField(References.INT_ID, 17, slot, "ToolDigAOE").setMinMaxAndIncromente(2, 17, 1));
        fields.add(new ItemConfigField(References.INT_ID, 9, slot, "ToolDigDepth").setMinMaxAndIncromente(1, 9, 1));
        fields.add(
            new EnumerableItemConfigField(References.INT_ID, slot, "silk", "enchantment.untouching")
                .representAs(References.BOOLEAN_ID)
                .readFromItem(stack, 0)
                .setMinMaxAndIncromente(0, 1, 1));
        fields.add(
            new EnumerableItemConfigField(References.INT_ID, slot, "dumbmode", "deci.dumbmode")
                .representAs(References.BOOLEAN_ID)
                .readFromItem(stack, 0)
                .setMinMaxAndIncromente(0, 1, 1));
        return fields;
    }

    @ModifyArgs(
        remap = false,
        method = "breakOtherBlock",
        at = @At(
            value = "INVOKE",
            target = "Lfox/spiteful/avaritia/items/tools/ToolHelper;removeBlocksInIteration(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;IIIIIIIIILnet/minecraft/block/Block;[Lnet/minecraft/block/material/Material;ZIZ)V"))
    public void modularBehavior(Args args, @Local(ordinal = 1) boolean facingWall,
        @Local(argsOnly = true) ItemStack stack) {
        args.set(14, ItemConfigFields.getOrDefault(stack, "silk", false));
        int size = ItemConfigFields.getOrDefault(stack, "ToolDigAOE", 17) / 2;
        int depth = ItemConfigFields.getOrDefault(stack, "ToolDigDepth", 9) / 2;
        args.set(6, -size);
        args.set(7, facingWall ? Math.max(-depth, -1) : -depth);
    }

    @Override
    public boolean hasProfiles() {
        return false;
    }
}
