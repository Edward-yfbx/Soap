package com.yfbx.soap.gson;

import com.google.gson.Gson;
import com.yfbx.soap.net.NetResult;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Author:Edward
 * Date:2017/9/19
 * Description:
 */

public class GsonUtil {


    /**
     * data is JsonObject
     */
    public static <T> NetResult<T> fromJsonObject(String jsonStr, Class<T> clazz) {
        Type type = new GsonType(NetResult.class, new Class[]{clazz});
        return new Gson().fromJson(jsonStr, type);
    }

    /**
     * data is JsonArray
     */
    public static <T> NetResult<List<T>> fromJsonArray(String jsonStr, Class<T> clazz) {
        Type listType = new GsonType(List.class, new Class[]{clazz});
        Type type = new GsonType(NetResult.class, new Type[]{listType});
        return new Gson().fromJson(jsonStr, type);
    }

    public static String toJson(Object src) {
        return new Gson().toJson(src);
    }

}
