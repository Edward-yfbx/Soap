package com.yfbx.soaputil.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yfbx.soap.net.NetCallback;
import com.yfbx.soap.net.NetResult;
import com.yfbx.soaputil.R;
import com.yfbx.soaputil.bean.User;
import com.yfbx.soaputil.net.Api;
import com.yfbx.soaputil.net.Net;

import java.util.List;

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

        Net.api(Api.TEST_CONNECTION).request(User.class, new NetCallback<User>() {
            @Override
            public void onSuccess(NetResult<User> result, NetResult<List<User>> resultList) {
                text.setText(result.json);
            }

            @Override
            public void onError() {
                text.setText("请求失败");

            }
        });
    }

}
