package com.drathonix.deconfigintegration.api;

import javax.annotation.Nonnull;

@SuppressWarnings("unused")
public interface NBTConfigField<T> {
    @Nonnull
    String getKey();
    T getValue();
    T getDefaultValue();
    T setValue(T value);
}
