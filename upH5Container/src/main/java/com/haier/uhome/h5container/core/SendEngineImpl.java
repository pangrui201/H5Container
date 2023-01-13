package com.haier.uhome.h5container.core;

import android.os.Looper;

import com.haier.uhome.h5container.Constant;
import com.tencent.smtt.sdk.WebView;

/**
 * @description:
 * @author: pangrui
 * @email: pangrui@haier.com
 * @date: 2022/12/22 13:56
 */
public class SendEngineImpl implements SendEngine {

    private WebView engine;

    public SendEngineImpl(WebView webView) {
        this.engine = webView;
    }

    @Override
    public void sendToJS(String message) {
        String javascriptCommand = String.format(Constant.JS_HANDLE_MESSAGE_FROM_JAVA, message);
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            this.engine.evaluateJavascript(javascriptCommand, null);
        }else {
            this.engine.post(new Runnable() {
                @Override
                public void run() {
                    SendEngineImpl.this.engine.evaluateJavascript(javascriptCommand, null);
                }
            });
        }
    }
}
