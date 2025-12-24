package com.drathonix.deconfigintegration.bridge;

import net.minecraft.util.StatCollector;
import net.minecraft.util.StringUtils;

import com.brandon3055.draconicevolution.common.lib.References;

/**
 * A config field whose value is one of an enumerable set of values rather than a bounded number.
 * @author Jack Andersen
 * @since 1.0.2
 */
public class EnumerableItemConfigField extends TranslatableItemConfigField {

    public static final int ENUM_REPR_ID = -1;

    // Represents how the field should be rendered in GUI. Some mods use integers instead of booleans for boolean
    // data, this allows using boolean representation for those cases.
    public int representativeType;
    private String[] enumTranslationKeys;

    public EnumerableItemConfigField(int datatype, int slot, String nbtName, String translationKey) {
        super(datatype, slot, nbtName, translationKey);
        this.representativeType = datatype;
    }

    public EnumerableItemConfigField representAs(int representativeType) {
        this.representativeType = representativeType;
        return this;
    }

    public EnumerableItemConfigField representAsEnum(String[] translationKeys) {
        return representAsEnum(translationKeys, asDatatype(0), asDatatype(translationKeys.length - 1), asDatatype(1));
    }

    public EnumerableItemConfigField representAsEnum(String[] translationKeys, Object trueMin, Object trueMax,
                                                     Object increment) {
        this.enumTranslationKeys = translationKeys;
        setMinMaxAndIncromente(trueMin, trueMax, 1);
        return representAs(-1);
    }

    public String getFormattedValue() {
        if (representativeType == References.INT_ID && !StringUtils.isNullOrEmpty(modifier) && modifier.equals("AOE")) {
            int i = (Integer) value;
            i *= 2;
            return (i + 1) + "x" + (i + 1);
        } else if (representativeType == References.BOOLEAN_ID) {
            return castToBoolean() ? StatCollector.translateToLocal("gui.de.on.txt")
                : StatCollector.translateToLocal("gui.de.off.txt");
        } else if (representativeType == References.FLOAT_ID && !StringUtils.isNullOrEmpty(modifier)
            && modifier.equals("PERCENT")) {
                return Math.round((Float) value * 100D) + "%";
            } else if (representativeType == References.FLOAT_ID && !StringUtils.isNullOrEmpty(modifier)
                && modifier.equals("PLUSPERCENT")) {
                    return "+" + Math.round((Float) value * 100D) + "%";
                } else if (representativeType == ENUM_REPR_ID) {
                    return StatCollector.translateToLocal(enumTranslationKeys[((Number) value).intValue()]);
                } else {
                    return String.valueOf(value);
                }

    }

    public Object asDatatype(Number number) {
        switch (datatype) {
            case References.BYTE_ID -> {
                return number.byteValue();
            }
            case References.SHORT_ID -> {
                return number.shortValue();
            }
            case References.INT_ID -> {
                return number.intValue();
            }
            case References.LONG_ID -> {
                return number.longValue();
            }
            case References.FLOAT_ID -> {
                return number.floatValue();
            }
            case References.DOUBLE_ID -> {
                return number.doubleValue();
            }
        }
        throw new IllegalArgumentException("Data type is not numerical!");
    }

    public boolean castToBoolean() {
        if (value instanceof Number) {
            return ((Number) value).intValue() != 0;
        } else {
            return false;
        }
    }

    public Object castBooleanToInt() {
        if (representativeType == References.BOOLEAN_ID) {
            switch (datatype) {
                case References.BYTE_ID -> {
                    return (byte) ((boolean) value ? 1 : 0);
                }
                case References.SHORT_ID -> {
                    return (short) ((boolean) value ? 1 : 0);
                }
                case References.INT_ID -> {
                    return (boolean) value ? 1 : 0;
                }
                case References.LONG_ID -> {
                    return (boolean) value ? 1L : 0L;
                }
                case References.FLOAT_ID -> {
                    return (boolean) value ? 1F : 0F;
                }
                case References.DOUBLE_ID -> {
                    return (boolean) value ? 1D : 0D;
                }
                case References.BOOLEAN_ID -> {
                    return value;
                }
            }
        }
        return 0;
    }

    public Object nextEnumValue() {
        if (value instanceof Number num && min instanceof Number minimum && max instanceof Number maximum) {
            long next = num.longValue();
            long ordinal = next - minimum.longValue();
            return asDatatype((ordinal + 1L) % (maximum.longValue() + 1));
        }
        if (value instanceof Boolean) {
            return !(Boolean) value;
        }
        return 0;
    }
}
