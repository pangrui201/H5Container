package com.haier.uhome.h5container.browse;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;

import com.haier.uhome.h5container.loader.JSFrameworkLoadImpl;
import com.haier.uhome.h5container.loader.JsLoaderResultCallBack;
import com.tencent.smtt.sdk.WebView;
import androidx.annotation.NonNull;
import com.haier.uhome.h5container.ContainerPluginManager;
import com.haier.uhome.h5container.message.MessageCenter;
import com.haier.uhome.h5container.utils.WebSettingTools;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.Map;

@SuppressLint("SetJavaScriptEnabled")
public class BridgeWebView extends WebView {

    private final String TAG = "BridgeWebView";

    private ContainerPluginManager containerManager;
    private boolean isJsFrameworkLoad = false;

    public BridgeWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BridgeWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public BridgeWebView(Context context) {
        super(context);
        init();
    }

    private void init() {
        WebSettingTools.setting(this);
        this.addJavascriptInterface(new ReceiveEngineImpl(), "_uplusNative");
        containerManager = new ContainerPluginManager(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void onDestory() {
        setVisibility(View.GONE);
        loadUrl("about:blank");
        stopLoading();
        setWebChromeClient(null);
//        setWebViewClient(null);
        super.destroy();
    }

    /**
     * Loads the given URL.
     *
     * @param url the URL of the resource to load
     */
    public void loadUrl(String url) {
        loadUrl(url, null);
    }

    public void loadUrl(String jsUrl, Map<String, String> additionalHttpHeaders) {
        if (jsUrl.startsWith("file:") || additionalHttpHeaders == null) {
            super.loadUrl(jsUrl);
        } else {
            super.loadUrl(jsUrl, additionalHttpHeaders);
        }
    }

    public ContainerPluginManager getContainerManager() {
        return containerManager;
    }

    @Override
    public void setWebViewClient(@NonNull WebViewClient client) {
        if (!(client instanceof AbsWebViewClient)) {
            throw new IllegalArgumentException("IllegalArgumentException: you must set a WebViewClient that instanceof PageLoadListenerClient");
        }
        ((AbsWebViewClient) client).registerPageLoadListener(new JSFrameworkLoadImpl(new JsLoaderResultCallBack() {
            @Override
            public void onResultCallBack(boolean loaded) {
                BridgeWebView.this.isJsFrameworkLoad = loaded;
                if(loaded){
                    BridgeWebView.this.notifyHandlerMessage();
                }
            }
        }));
        super.setWebViewClient(client);
    }

    public boolean isJsFrameworkLoad(){
        return this.isJsFrameworkLoad;
    }

    private void notifyHandlerMessage() {
        MessageCenter messageCenter = containerManager.getMessageCenter();
        messageCenter.startTaskHandler();
    }


    class ReceiveEngineImpl implements com.haier.uhome.h5container.core.ReceiveEngine {

        @JavascriptInterface
        public void exec(String message) {
            Log.d(TAG, "BridgeJsAPI exec: " + message);
                receiveFromJs(message);
        }

        @Override
        public void receiveFromJs(String message){
            MessageCenter messageCenter = containerManager.getMessageCenter();
            messageCenter.receiveMsgFromJS(message);
        }
    }
}
