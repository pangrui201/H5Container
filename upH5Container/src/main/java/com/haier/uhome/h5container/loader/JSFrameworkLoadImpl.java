package com.haier.uhome.h5container.loader;

import com.tencent.smtt.sdk.WebView;

/**
 * @description:
 * @author: pangrui
 * @email: pangrui@haier.com
 * @date: 2023/1/5 14:02
 */
public class JSFrameworkLoadImpl implements PageLoadListener{
    private JsLoader jsLoader;
    private JsLoaderResultCallBack callBack;
    public JSFrameworkLoadImpl(JsLoaderResultCallBack jsLoaderResultCallBack){
        this.jsLoader = new JsFrameworkLoader();
        this.callBack = jsLoaderResultCallBack;
    }

    @Override
    public void onPageStarted(WebView view, String url) {
            this.jsLoader.load(view);
            if(callBack != null){
                callBack.onResultCallBack(this.jsLoader.isLoaded());
            }
    }

    @Override
    public void onPageFinished(WebView view, String url) {

    }

    @Override
    public void onPageError(WebView view, int errorCode, String description, String failingUrl) {

    }
}
