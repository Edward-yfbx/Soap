package com.yfbx.soaputil.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.yfbx.soaputil.events.NetFailEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Author: Edward
 * Date: 2017/10/4 23:53
 * Description:
 */

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetFail(NetFailEvent event) {
        Toast.makeText(this, "请求失败！", Toast.LENGTH_SHORT).show();
    }
}
