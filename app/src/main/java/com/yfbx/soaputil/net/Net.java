package com.yfbx.soaputil.net;

import android.os.AsyncTask;
import android.util.Log;

import com.yfbx.soaputil.BuildConfig;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Author: Edward
 * Date: 2018/3/3
 * Description:
 */


public class Net extends AsyncTask<Void, Void, String> {

    private static final String TAG = "NET";
    private static final String NAME_SPACE = "http://tempuri.org/";
    private static final String WSDL = Api.WSDL;
    private SoapObject soapObject;
    private SoapCallback callback;

    private Net(SoapObject soapObject) {
        this.soapObject = soapObject;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (callback != null) {
            callback.onStart();
        }
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            return executeSoap();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s != null) {
            log("请求成功:" + s);
        } else {
            log("请求失败");
        }

        if (callback != null) {
            callback.onComplete(s);
        }

    }

    /**
     * 方法名
     */
    public static Net method(String method) {
        return new Net(new SoapObject(NAME_SPACE, method));
    }

    /**
     * 请求参数
     */
    public Net put(String key, Object value) {
        soapObject.addProperty(key, value);
        return this;
    }

    /**
     * 请求
     */
    public void request(SoapCallback callback) {
        this.callback = callback;
        execute();
    }

    /**
     * 执行Soap请求
     */
    private String executeSoap() throws Exception {
        final SoapSerializationEnvelope se = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        se.dotNet = true;
        se.bodyOut = soapObject;
        HttpTransportSE ht = new HttpTransportSE(WSDL);

        ht.call(NAME_SPACE, se);
        Object object = se.bodyIn;
        SoapObject response = (SoapObject) object;

        return response.getProperty(0).toString();
    }

    /**
     * Log打印
     */
    private void log(String msg) {
        if (BuildConfig.DEBUG) {
            Log.i(TAG, msg);
        }
    }
}