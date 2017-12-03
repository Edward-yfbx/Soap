package com.yfbx.soap.net;

import android.os.Handler;
import android.os.Looper;

import com.yfbx.soap.gson.GsonUtil;
import com.yfbx.soap.utils.NetLog;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.List;

/**
 * Author: Edward
 * Date: 2017/7/29 19:01
 * Description:
 */

public abstract class Soap {

    private static final String NAME_SPACE = "http://tempuri.org/";

    private Handler handler = new Handler();
    private SoapObject soapObject;
    private NetCallback callback;

    public abstract String wsdl();

    protected String nameSpace() {
        return NAME_SPACE;
    }

    /**
     * 方法名
     */
    public Soap method(String method) {
        soapObject = new SoapObject(nameSpace(), method);
        return this;
    }

    /**
     * 请求参数
     */
    public Soap put(String key, Object value) {
        soapObject.addProperty(key, value);
        return this;
    }

    /**
     * 请求
     */
    public <T> void request(Class<T> clazz, NetCallback<T> callback) {
        this.callback = callback;
        this.callback.onStart();
        new RequestThread<T>(clazz).start();
    }

    /**
     * 请求线程
     */
    private class RequestThread<T> extends Thread {

        private Class<T> clazz;

        RequestThread(Class<T> clazz) {
            this.clazz = clazz;
        }

        @Override
        public void run() {
            Looper.prepare();
            try {
                //请求结果
                String response = execute();
                if (response == null) {
                    onError();
                    return;
                }
                NetLog.i(response);

                //data中是JsonObject
                NetResult<T> result = GsonUtil.fromJsonObject(response, clazz);
                if (result != null) {
                    onResult(result);
                    return;
                }

                //data中是JsonArray
                NetResult<List<T>> resultList = GsonUtil.fromJsonArray(response, clazz);
                if (resultList != null) {
                    onResultList(resultList);
                    return;
                }

                //json非规范格式
                NetResult<T> jsonResult = new NetResult<>();
                jsonResult.json = response;
                onResult(jsonResult);
            } catch (Exception e) {
                e.printStackTrace();
                onError();
            }
            Looper.loop();
        }
    }

    /**
     * 执行请求
     */
    private String execute() throws Exception {
        final SoapSerializationEnvelope se = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        se.dotNet = true;
        se.bodyOut = soapObject;
        HttpTransportSE ht = new HttpTransportSE(wsdl());

        ht.call(NAME_SPACE, se);
        Object object = se.bodyIn;
        SoapObject response = (SoapObject) object;

        if (response.getPropertyCount() > 0) {
            return response.getProperty(0).toString();
        }
        return null;
    }

    /**
     * 结果回调
     */
    private <T> void onResult(final NetResult<T> result) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(result, null);
            }
        });
    }

    private <T> void onResultList(final NetResult<List<T>> resultList) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(null, resultList);
            }
        });
    }

    private void onError() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.onError();
            }
        });
    }


}
