package com.haier.uhome.h5container.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


import com.haier.uhome.h5container.ContainerPluginManager;
import com.haier.uhome.h5container.R;
import com.haier.uhome.h5container.browse.BridgeWebView;
import com.haier.uhome.h5container.browse.CallBackFunction;
import com.haier.uhome.h5container.browse.PluginHandler;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends BaseContainerActivity {

    private static final String TAG = "MainActivity";
    // UI references.
    private ArrayList<String> mHandlers = new ArrayList<>();

    private static CallBackFunction mfunction;

    int RESULT_CODE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.layout_activity);
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        containerManager.sendEvent("resume","我是resumeData");

    }

    @Override
    protected void onPause() {
        super.onPause();
        containerManager.sendEvent("pause","我是pauseData");
    }

    @Override
    public BridgeWebView supportedWebView() {
        return findViewById(R.id.webview);
    }

    @Override
    public Map<String, String> pageHeaders() {
        return null;
    }

    @Override
    public String pageUrl() {
        return "file:///android_asset/demo.html";
    }

    @Override
    public String pageError() {
        return null;
    }

    private void init() {
        bridgeWebView = (BridgeWebView) findViewById(R.id.webview);
        mHandlers.add("login");
        mHandlers.add("callNative");
        mHandlers.add("callFromJs");
        mHandlers.add("open");

        //回调js的方法
        containerManager.registerPlugins(mHandlers, new PluginHandler() {
            @Override
            public void handler(String pluginName, String data, CallBackFunction function) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (pluginName.equals("login")) {
                            Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
                            //        // 调用js

                        } else if (pluginName.equals("callNative")) {
                            Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
                            function.onCallBack("我在西安");
                        } else if (pluginName.equals("callFromJs")) {
                            Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
                            // 想调用你的方法：
                            function.onCallBack("回调");
                            containerManager.sendEvent("callFromNative", "hello H5, 我是java主动发消息");
                        }
                        if (pluginName.equals("open")) {
                            mfunction = function;

                        }
                    }
                });
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == RESULT_CODE) {
            mfunction.onCallBack(intent.getData().toString());
        }
    }
}

