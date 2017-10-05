package com.yfbx.soaputil.events;


import com.yfbx.soaputil.gson.Result;

import java.util.List;

/**
 * Author: Edward
 * Date: 2017/10/4 18:05
 * Description:
 */

public class NetEvent<T> {


    private String className;
    private Result<T> result;
    private Result<List<T>> resultList;

    /**
     * @param flag 并无作用，只为避免两个构造的参数类型一样
     */
    public NetEvent(String className, Result<T> result, boolean flag) {
        this.className = className;
        this.result = result;
    }

    public NetEvent(String className, Result<List<T>> resultList) {
        this.className = className;
        this.resultList = resultList;
    }

    public Result<T> getResult() {
        return result;
    }

    public String getClassName() {
        return className;
    }

    public Result<List<T>> getResultList() {
        return resultList;
    }
}
