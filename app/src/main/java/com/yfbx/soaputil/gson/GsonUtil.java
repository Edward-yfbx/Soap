package com.yfbx.soaputil.gson;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Author:Edward
 * Date:2017/9/19
 * Description:
 */

public class GsonUtil {


    /**
     * data 是JsonObject的情况
     */
    public static <T> Result<T> fromJsonObject(String jsonStr, Class<T> clazz) {
        Type type = new GsonType(Result.class, new Class[]{clazz});
        return new Gson().fromJson(jsonStr, type);
    }

    /**
     * data 是JsonArray的情况
     */
    public static <T> Result<List<T>> fromJsonArray(String jsonStr, Class<T> clazz) {
        Type listType = new GsonType(List.class, new Class[]{clazz}); // 生成List<T> 中的 List<T>
        Type type = new GsonType(Result.class, new Type[]{listType}); // 根据List<T>生成完整的Result<List<T>>
        return new Gson().fromJson(jsonStr, type);
    }

    /**
     * Gson泛型封装
     */
    public static class GsonType implements ParameterizedType {

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
}
