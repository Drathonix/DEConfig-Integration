package com.drathonix.deconfigintegration.api;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;

public abstract class EnumerableConfigField<T> extends NBTConfigFieldBase<T> {
    private final LinkedHashSet<T> options;

    @SafeVarargs
    protected EnumerableConfigField(String key, T defaultValue, T... options) {
        super(key, defaultValue);
        this.options = new LinkedHashSet<>(Arrays.asList(options));
    }

    @Override
    public T validate(T value) {
        return options.contains(value) ? value : getValue();
    }
}
