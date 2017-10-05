package com.yfbx.soaputil.soap;

import com.yfbx.soaputil.events.JsonEvent;
import com.yfbx.soaputil.events.NetEvent;
import com.yfbx.soaputil.events.NetFailEvent;
import com.yfbx.soaputil.gson.GsonUtil;
import com.yfbx.soaputil.gson.Result;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Edward
 * Date: 2017/10/4 23:00
 * Description:
 */

public class SoapUtil<T> {

    private static final String TAG = SoapUtil.class.getName();
    private String method;
    private HashMap<String, String> params;
    private Class<T> clazz;

    public static final String NAME_SPACE = "http://tempuri.org/";
    public static final String WSDL = "http://61.93.114.100:8002/POSTestWebService/ServiceFile.asmx";

    public static <T> void request(String method, HashMap<String, String> params, Class<T> clazz) {
        SoapUtil<T> soapUtil = new SoapUtil<>(method, params, clazz);
        EventBus.getDefault().post(soapUtil);
    }

    public SoapUtil(String method, HashMap<String, String> params, Class<T> clazz) {
        this.method = method;
        this.params = params;
        this.clazz = clazz;
        EventBus.getDefault().register(this);
    }

    /**
     * 执行请求
     */
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void call(SoapUtil event) {
        SoapObject soapObject = new SoapObject(NAME_SPACE, method);
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                soapObject.addProperty(entry.getKey(), entry.getValue());
            }
        }

        final SoapSerializationEnvelope se = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        se.dotNet = true;
        se.bodyOut = soapObject;
        HttpTransportSE ht = new HttpTransportSE(WSDL);

        try {
            ht.call(NAME_SPACE, se);
            Object object = se.bodyIn;
            SoapObject response = (SoapObject) object;
            if (response.getPropertyCount() > 0) {
                parseJson(response.getProperty(0).toString(), clazz);

            }
        } catch (Exception e) {
            e.printStackTrace();
            EventBus.getDefault().post(new NetFailEvent());
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 解析Json
     */
    private void parseJson(String json, Class<T> clazz) {
        EventBus.getDefault().post(new JsonEvent<>(json, clazz));//手动解析
        boolean isJsonArray = false;

        try {
            Result<T> result = GsonUtil.fromJsonObject(json, clazz);
            EventBus.getDefault().post(new NetEvent<T>(clazz.getName(), result, false));
        } catch (Exception e) {
            e.printStackTrace();
            isJsonArray = true;
        }

        if (isJsonArray) {
            Result<List<T>> result = GsonUtil.fromJsonArray(json, clazz);
            EventBus.getDefault().post(new NetEvent<T>(clazz.getName(), result));
        }

        EventBus.getDefault().unregister(this);

    }
}
