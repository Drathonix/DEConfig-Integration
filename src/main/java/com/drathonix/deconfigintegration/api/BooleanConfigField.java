package com.drathonix.deconfigintegration.api;

public class BooleanConfigField extends EnumerableConfigField<Boolean>{
    protected BooleanConfigField(String key, boolean defaultValue) {
        super(key, defaultValue, false,true);
    }
}
