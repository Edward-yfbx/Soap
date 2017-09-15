package com.yfbx.soaputil;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.yfbx.soap.Soap;

public class MainActivity extends AppCompatActivity {


    private static final String WSDL = "http://www.xxx.com/ServiceFile.asmx";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        testSoap();
    }


    private void testSoap() {
        Soap.wsdl(WSDL).method("DownloadData").params("", "").request(new Soap.OnSoapListener() {
            @Override
            public void onResponse(String result) {
                Log.i("请求结果", result);
            }

            @Override
            public void onFailure(int errorCode, String error) {
                Log.i("请求失败", error);
            }
        });


    }
}
