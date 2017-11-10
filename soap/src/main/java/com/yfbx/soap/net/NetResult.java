package com.yfbx.soap.net;

import com.google.gson.annotations.SerializedName;

/**
 * Author:Edward
 * Date:2017/9/19
 * Description:
 */

public class NetResult<T> {

//     Based on Json format:
//     {
//     "code":1,
//     "msg":"success",
//     "data":[ ] / { }
//     }

    @SerializedName(value = "code", alternate = {"status", "retCode"})
    public int code;
    @SerializedName(value = "msg", alternate = {"info", "retMsg"})
    public String msg;
    @SerializedName(value = "data", alternate = {"contents", "retData"})
    public T data;

}
