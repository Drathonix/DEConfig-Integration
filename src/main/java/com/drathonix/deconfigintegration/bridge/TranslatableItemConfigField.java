package com.drathonix.deconfigintegration.bridge;

import net.minecraft.util.StatCollector;

import com.brandon3055.draconicevolution.common.utills.ItemConfigField;

/**
 * Generic translatable item config field which reads the field's name from the translation table using the key
 * "button.de.{@link TranslatableItemConfigField#translationKey}.name"
 *
 * @author Jack Andersen
 * @since 1.0.0
 */
public class TranslatableItemConfigField extends ItemConfigField {

    private final String translationKey;
    private boolean useKeyDirectly;

    public TranslatableItemConfigField(int datatype, int slot, String nbtName, String translationKey) {
        super(datatype, slot, nbtName);
        this.translationKey = translationKey;
    }

    public TranslatableItemConfigField useKeyDirectly() {
        this.useKeyDirectly = true;
        return this;
    }

    @Override
    public String getLocalizedName() {
        return StatCollector.translateToLocal(useKeyDirectly ? translationKey : "button.de." + translationKey + ".name");
    }
}
/
