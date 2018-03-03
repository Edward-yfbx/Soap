package com.yfbx.soaputil.net;

/**
 * Author: Edward
 * Date: 2018/3/3
 * Description:
 */


public abstract class SoapCallback {


    public void onStart() {
    }


    public abstract void onComplete(String result);


}
