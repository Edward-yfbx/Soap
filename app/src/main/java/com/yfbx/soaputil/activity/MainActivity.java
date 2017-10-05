package com.yfbx.soaputil.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yfbx.soaputil.R;
import com.yfbx.soaputil.bean.Info;
import com.yfbx.soaputil.events.JsonEvent;
import com.yfbx.soaputil.events.NetEvent;
import com.yfbx.soaputil.gson.GsonUtil;
import com.yfbx.soaputil.soap.SoapUtil;
import com.yfbx.soaputil.utils.Soap;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoapUtil.request("DownloadData", null, Info.class);
            }
        });
    }

    /**
     * 请求结果
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRequestEvent(NetEvent<Info> event) {
        if (event.getClassName().equals(Info.class.getName())) {
            Toast.makeText(this, "请求成功！", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 手动解析
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onJsontEvent(JsonEvent<Info> event) {
        Class<Info> clazz = event.getClazz();
        if (clazz.getName().equals(Info.class.getName())) {
            Type type = new GsonUtil.GsonType(Info.class, null);
            Info info = new Gson().fromJson(event.getJson(), type);
            Log.i("解析JSon", "onJsontEvent: " + info.getUser());
        }
    }


    private void testSoap() {
        Soap.method("DownloadData").params("", "").request(new Soap.OnSoapListener() {
            @Override
            public void onResponse(String result) {

            }

            @Override
            public void onFailure(int errorCode, String error) {

            }
        });
    }
}
