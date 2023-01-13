package com.haier.uhome.h5container.loader;


import com.tencent.smtt.sdk.WebView;

/**
 * @description:
 * @author: pangrui
 * @email: pangrui@haier.com
 * @date: 2022/12/22 10:01
 */
public interface PageLoadListener {

    void onPageStarted(WebView view, String url);

    void onPageFinished(WebView view, String url);

    void onPageError(WebView view, int errorCode,
                         String description, String failingUrl);
}