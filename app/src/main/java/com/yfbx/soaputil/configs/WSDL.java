package com.yfbx.soaputil.configs;

/**
 * Author: Edward
 * Date: 2017/11/11 1:00
 * Description:
 */

public class WSDL {


    private static final String DEBUG = "";
    private static final String TEST = "";
    private static final String ONLINE = "";


    public static String get() {
        switch (Config.TYPE) {
            case Config.DEBUG:
                return DEBUG;
            case Config.TEST:
                return TEST;
            case Config.ONLINE:
                return ONLINE;
            default:
                return DEBUG;
        }
    }
}
