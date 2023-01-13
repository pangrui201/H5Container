package com.haier.uhome.h5container.browse;


import androidx.annotation.NonNull;

import com.haier.uhome.h5container.loader.BridgeWebViewClient;
import com.haier.uhome.h5container.loader.PageLoadListener;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebView;

import java.util.Map;

public abstract class AbsWebViewClient extends BridgeWebViewClient {


    public AbsWebViewClient() {
        super();
        registerPageLoadListener(new PageLoadListener() {
            @Override
            public void onPageStarted(WebView view, String url) {

            }

            @Override
            public void onPageFinished(WebView view, String url) {

            }

            @Override
            public void onPageError(WebView view, int errorCode, String description, String failingUrl) {
                view.loadUrl(AbsWebViewClient.this.onPageError(failingUrl));

            }
        });
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        if (onPageHeaders(url) != null) {
            view.loadUrl(url, onPageHeaders(url));
        }
        return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        sslErrorHandler.proceed();//支持https,http
    }

    /**
     * return errorUrl
     * @param url
     * @return
     */
    public abstract String onPageError(String url);

    /**
     * HttpHeaders
     * return
     * @return
     */
    @NonNull
    public abstract Map<String, String> onPageHeaders(String url);
    
}
