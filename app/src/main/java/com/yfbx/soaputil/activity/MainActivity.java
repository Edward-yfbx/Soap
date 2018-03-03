package com.yfbx.soaputil.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yfbx.soaputil.R;
import com.yfbx.soaputil.net.Api;
import com.yfbx.soaputil.net.Net;
import com.yfbx.soaputil.net.SoapCallback;

public class MainActivity extends Activity {

    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.text);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testSoap();
            }
        });
    }


    private void testSoap() {

        Net.method(Api.TEST_CONNECTION).request(new SoapCallback() {
            @Override
            public void onComplete(String result) {
                text.setText(result);
            }
        });
    }

}
