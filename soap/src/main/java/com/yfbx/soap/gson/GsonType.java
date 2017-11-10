package com.yfbx.soap.gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Author:Edward
 * Date:2017/10/9
 * Description:
 */

public class GsonType implements ParameterizedType {

    private final Class raw;
    private final Type[] args;

    public GsonType(Class raw, Type[] args) {
        this.raw = raw;
        this.args = args != null ? args : new Type[0];
    }

    @Override
    public Type[] getActualTypeArguments() {
        return args;
    }

    @Override
    public Type getRawType() {
        return raw;
    }

    @Override
    public Type getOwnerType() {
        return null;
    }
}
