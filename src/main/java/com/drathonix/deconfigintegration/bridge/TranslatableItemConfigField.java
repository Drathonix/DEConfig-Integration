package com.drathonix.deconfigintegration.bridge;

import net.minecraft.util.StatCollector;

import com.brandon3055.draconicevolution.common.utills.ItemConfigField;

public class TranslatableItemConfigField extends ItemConfigField {

    private final String translationKey;

    public TranslatableItemConfigField(int datatype, int slot, String nbtName, String translationKey) {
        super(datatype, slot, nbtName);
        this.translationKey = translationKey;
    }

    @Override
    public String getLocalizedName() {
        return StatCollector.translateToLocal("button.de." + translationKey + ".name");
    }
}
