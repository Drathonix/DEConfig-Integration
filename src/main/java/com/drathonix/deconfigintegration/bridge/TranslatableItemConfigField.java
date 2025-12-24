package com.drathonix.deconfigintegration.bridge;

import net.minecraft.util.StatCollector;

import com.brandon3055.draconicevolution.common.utills.ItemConfigField;

/**
 * Generic translatable item config field which reads the field's name from the translation table using the key "button.de.{@link TranslatableItemConfigField#translationKey}.name"
 * @author Jack Andersen
 * @since 1.0.0
 */
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
