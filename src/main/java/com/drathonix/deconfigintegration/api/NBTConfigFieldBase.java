package com.drathonix.deconfigintegration.api;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public abstract class NBTConfigFieldBase<T> implements NBTConfigField<T>{
    private T value;
    private final T defaultValue;
    private final String key;

    protected NBTConfigFieldBase(@Nonnull String key, T defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    @Override
    public @NotNull String getKey() {
        return key;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public T getDefaultValue() {
        return defaultValue;
    }

    @Override
    public T setValue(T value) {
        T ret = this.value;
        this.value = validate(value);
        return ret;
    }

    public abstract T validate(T value);
}
