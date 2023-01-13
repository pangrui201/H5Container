package com.haier.uhome.h5container.loader;

import android.graphics.Bitmap;

import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.ArrayList;
import java.util.List;


public class BridgeWebViewClient extends WebViewClient {

    private List<PageLoadListener> loadListeners;

    public BridgeWebViewClient() {
        loadListeners = new ArrayList<>();
    }


    public void registerPageLoadListener(PageLoadListener loadListener) {
        this.loadListeners.add(loadListener);
    }

    public void unRegisterPageLoadListener(PageLoadListener loadListener) {
        if (this.loadListeners.contains(loadListener)) {
            this.loadListeners.remove(loadListener);
        }
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        for (PageLoadListener loadListener :
                this.loadListeners) {
            loadListener.onPageStarted(view, url);
        }
    }


    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        for (PageLoadListener loadListener :
                this.loadListeners) {
            loadListener.onPageFinished(view, url);
        }
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        for (PageLoadListener loadListener :
                this.loadListeners) {
            loadListener.onPageError(view, errorCode, description, failingUrl);
        }
    }

}