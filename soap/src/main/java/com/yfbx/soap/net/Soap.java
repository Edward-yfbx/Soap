package com.yfbx.soap.net;

import android.os.Handler;
import android.os.Looper;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Author: Edward
 * Date: 2017/7/29 19:01
 * Description:
 */

public class Soap {

    private static final String NAME_SPACE = "http://tempuri.org/";

    private static String wsdl;
    private Handler handler;
    private OnSoapListener listener;
    private SoapObject soapObject;


    private Soap(String method) {
        soapObject = new SoapObject(NAME_SPACE, method);
    }

    /**
     * wsdl 和 方法名
     */
    public static Soap with(String wsdl, String method) {
        Soap.wsdl = wsdl;
        return new Soap(method);
    }

    /**
     * 请求参数
     */
    public Soap params(String key, Object value) {
        soapObject.addProperty(key, value);
        return this;
    }

    /**
     * 请求
     */
    public void request(OnSoapListener listener) {
        this.listener = listener;
        new RequestThread().start();
    }

    /**
     * 请求线程
     */
    private class RequestThread extends Thread {

        @Override
        public void run() {
            Looper.prepare();
            handler = new Handler();
            try {
                handlerResult(execute());
            } catch (Exception e) {
                e.printStackTrace();
                handleError();
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
        HttpTransportSE ht = new HttpTransportSE(wsdl);

        ht.call(NAME_SPACE, se);
        Object object = se.bodyIn;
        SoapObject response = (SoapObject) object;

        if (response.getPropertyCount() > 0) {
            return response.getProperty(0).toString();
        }
        return null;
    }

    /**
     * 错误回调,回到UI线程
     */
    private void handleError() {
        if (listener != null && handler != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onFailure();
                }
            });
        }
    }

    /**
     * 结果回调,回到UI线程
     */
    private void handlerResult(final String result) {
        if (listener != null && handler != null && result != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onResponse(result);
                }
            });
        }
    }


    /**
     * 回调接口
     */
    public interface OnSoapListener {
        void onResponse(String json);

        void onFailure();
    }
}
