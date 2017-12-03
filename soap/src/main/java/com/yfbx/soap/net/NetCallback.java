package com.yfbx.soap.net;

import java.util.List;

/**
 * Author: Edward
 * Date: 2017/12/3
 * Description:
 */


public abstract class NetCallback<T> {


    public void onStart() {
    }


    public void onCompelete() {
    }


    public void onError() {
    }

    public abstract void onSuccess(NetResult<T> result, NetResult<List<T>> resultList);


}
