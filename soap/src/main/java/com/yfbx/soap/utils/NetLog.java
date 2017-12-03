package com.yfbx.soap.utils;

import android.util.Log;

/**
 * Author: Edward
 * Date: 2017/12/3
 * Description:
 */


public class NetLog {

    private static final String TAG = "Soap";

    private static boolean isDebug;

    public static void setDebug(boolean isDebug) {
        NetLog.isDebug = isDebug;
    }

    public static void i(String info) {
        if (isDebug) {
            Log.i(TAG, info);
        }
    }

    public static void e(String info) {
        if (isDebug) {
            Log.e(TAG, info);
        }
    }
}
