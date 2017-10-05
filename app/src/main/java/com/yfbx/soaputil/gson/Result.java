package com.yfbx.soaputil.gson;

/**
 * Author:Edward
 * Date:2017/9/19
 * Description:
 */

public class Result<T> {

    /**
     * 基于以下JSON格式：
     * {
     * "code":1,
     * "msg":"请求成功",
     * "data":[ ] 或 { }
     * }
     */

    public int code;
    public String msg;
    public T data;


}
