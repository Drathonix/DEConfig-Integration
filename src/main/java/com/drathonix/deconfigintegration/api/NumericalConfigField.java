package com.drathonix.deconfigintegration.api;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public abstract class NumericalConfigField<N extends Number> extends NBTConfigFieldBase<N> {
    private double minimum = Double.MIN_VALUE;
    private double maximum = Double.MAX_VALUE;
    private final Class<N> type;
    @SuppressWarnings("unchecked")
    public NumericalConfigField(@Nonnull String key, @Nonnull N defaultValue) {
        super(key,defaultValue);
        this.type = (Class<N>)defaultValue.getClass();
    }
    public NumericalConfigField(@Nonnull String key, @NotNull N defaultValue, double minimum, double maximum) {
        this(key,defaultValue);
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public double getMinimum() {
        return minimum;
    }

    public void setMinimum(double minimum) {
        this.minimum = minimum;
    }

    public double getMaximum() {
        return maximum;
    }

    public void setMaximum(double maximum) {
        this.maximum = maximum;
    }

    @Override
    public N validate(N value) {
        return asType(Math.max((Math.min(value.doubleValue(),maximum)),minimum));
    }

    @SuppressWarnings("unchecked")
    private N asType(Number value) {
        if(type == Byte.class) {
            return (N)(Byte)value.byteValue();
        }
        if(type == Short.class) {
            return (N)(Short)value.shortValue();
        }
        if(type == Integer.class) {
            return (N)(Integer)value.intValue();
        }
        if(type == Long.class) {
            return (N)(Long)value.longValue();
        }
        if(type == Float.class) {
            return (N)(Float)value.floatValue();
        }
        if(type == Double.class) {
            return (N)(Double)value.doubleValue();
        }
        throw new IllegalStateException(type + " is not a child of Number!");
    }
}
