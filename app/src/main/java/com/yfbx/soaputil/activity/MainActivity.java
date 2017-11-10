package com.yfbx.soaputil.activity;

import android.os.Bundle;
import android.view.View;

import com.yfbx.soap.gson.GsonUtil;
import com.yfbx.soap.net.NetResult;
import com.yfbx.soap.net.Soap;
import com.yfbx.soaputil.R;
import com.yfbx.soaputil.bean.Info;
import com.yfbx.soaputil.configs.WSDL;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testSoap();
            }
        });
    }


    private void testSoap() {

        Soap.with(WSDL.get(), "method").params("", "").request(new Soap.OnSoapListener() {
            @Override
            public void onResponse(String json) {
                NetResult<Info> infoNetResult = GsonUtil.fromJsonObject(json, Info.class);
            }

            @Override
            public void onFailure() {
                onNetError();
            }
        });
    }

}
