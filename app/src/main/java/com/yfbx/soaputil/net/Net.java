package com.yfbx.soaputil.net;

import com.yfbx.soap.net.Soap;

/**
 * Author: Edward
 * Date: 2017/12/3
 * Description:
 */


public class Net extends Soap {


    @Override
    public String wsdl() {
        return Api.WSDL;
    }


    public static Soap api(String method) {
        Soap soap = new Net();
        soap.method(method);
        return soap;
    }

}
