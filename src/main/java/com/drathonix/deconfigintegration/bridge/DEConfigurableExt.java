package com.drathonix.deconfigintegration.bridge;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface DEConfigurableExt {

    default @Nonnull NBTTagCompound hodgepodge$getConfigurableTag(@Nonnull ItemStack stack) {
        return stack.stackTagCompound;
    }
}
