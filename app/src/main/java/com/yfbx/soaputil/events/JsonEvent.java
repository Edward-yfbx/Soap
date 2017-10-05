package com.yfbx.soaputil.events;

/**
 * Author: Edward
 * Date: 2017/10/4 23:36
 * Description:
 */

public class JsonEvent<T> {

    private String json;
    private Class<T> clazz;

    public JsonEvent(String json, Class<T> clazz) {
        this.json = json;
        this.clazz = clazz;
    }

    public String getJson() {
        return json;
    }

    public Class<T> getClazz() {
        return clazz;
    }
}
