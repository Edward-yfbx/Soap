package com.yfbx.soap;

import android.os.Handler;
import android.os.Looper;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Author: Edward
 * Date: 2017/7/29 19:01
 * Description:
 */

public class Soap {

    private static final String NAME_SPACE = "http://tempuri.org/";

    public static final int CONNECTION_FAIL = -1;
    public static final int ARGS_ERROR = -2;
    public static final int SERVER_ERROR = -3;
    private String wsdl;

    private Handler handler;
    private OnSoapListener listener;
    private SoapObject soapObject;

    private static Soap soap;

    private Soap(String wsdl) {
        this.wsdl = wsdl;
    }

    public static Soap wsdl(String wsdl) {
        soap = new Soap(wsdl);
        return soap;
    }

    public Soap method(String method) {
        soapObject = new SoapObject(NAME_SPACE, method);
        return soap;
    }

    public Soap params(String key, Object value) {
        soapObject.addProperty(key, value);
        return soap;
    }

    /**
     * 请求
     */
    public void request(OnSoapListener listener) {
        this.listener = listener;
        new RequestThread().start();
    }

    private class RequestThread extends Thread {

        @Override
        public void run() {
            Looper.prepare();
            handler = new Handler();
            execute();
            Looper.loop();
        }
    }

    /**
     * 执行请求
     */
    private String execute() {
        final SoapSerializationEnvelope se = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        se.dotNet = true;
        se.bodyOut = soapObject;
        HttpTransportSE ht = new HttpTransportSE(wsdl);

        try {
            ht.call(NAME_SPACE, se);
        } catch (IOException e) {
            e.printStackTrace();
            handleError(CONNECTION_FAIL, "Connection Fail!");
            return null;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            handleError(ARGS_ERROR, "Args Error!");
            return null;
        }
        Object object = se.bodyIn;
        SoapObject response;
        try {
            response = (SoapObject) object;
        } catch (ClassCastException e) {
            e.printStackTrace();
            handleError(SERVER_ERROR, "Server Error!");
            return null;
        }

        if (response.getPropertyCount() > 0) {
            String result = response.getProperty(0).toString();
            handlerResult(result);
            return result;
        }
        return null;
    }

    /**
     * 错误回调,回到UI线程
     */
    private void handleError(final int code, final String error) {
        if (listener == null || handler == null) {
            return;
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                listener.onFailure(code, error);
            }
        });
    }

    /**
     * 结果回调,回到UI线程
     */
    private void handlerResult(final String result) {
        if (listener == null || handler == null) {
            return;
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                listener.onResponse(result);
            }
        });
    }


    /**
     * 回调接口
     */
    public interface OnSoapListener {
        void onResponse(String result);

        void onFailure(int errorCode, String error);
    }
}
