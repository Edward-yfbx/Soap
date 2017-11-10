package com.yfbx.soaputil.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.yfbx.soap.net.NetConfig;

/**
 * Author: Edward
 * Date: 2017/11/11 0:52
 * Description:
 */

public class BaseActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onNetError() {
        if (NetConfig.isNetAvailable(this)) {
            Toast.makeText(this, "网络不可用！", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "服务器错误，请稍后再试！", Toast.LENGTH_SHORT).show();
        }
    }
}
