package com.drathonix.deconfigintegration.bridge;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Can be implemented by an item class to provide draconic evolution with a configurable tag to read and modify.
 * 
 * @author Jack Andersen
 * @since 1.0.0
 */
public interface DEConfigurableExt {

    default @Nonnull NBTTagCompound deci$getConfigurableTag(@Nonnull ItemStack stack) {
        return stack.stackTagCompound;
    }
}
