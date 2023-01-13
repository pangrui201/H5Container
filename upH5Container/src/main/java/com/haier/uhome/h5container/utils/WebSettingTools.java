package com.haier.uhome.h5container.utils;


import android.os.Build;
import android.view.KeyEvent;
import android.view.View;

import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

/**
 * @description:
 * @author: pangrui
 * @email: pangrui@haier.com
 * @date: 2022/12/26 17:14
 */
public class WebSettingTools {

    public static void setting(WebView webView) {
        WebSettings webSettings = webView.getSettings();
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        // 判断系统版本是不是5.0或之上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //让系统不屏蔽混合内容和第三方Cookie
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
            webSettings.setMixedContentMode(WebSettings.LOAD_NORMAL);
        }
        // 不支持缩放
        webSettings.setSupportZoom(false);

        // 自适应屏幕大小
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        //
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                        webView.goBack();
                        return true;
                    }
                }
                return false;
            }
        });
        /**
         *  Webview在安卓5.0之前默认允许其加载混合网络协议内容
         *  在安卓5.0之后，默认不允许加载http与https混合内容，需要设置webview允许其加载混合网络协议内容
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.LOAD_NORMAL);
        }

        //自动播放音频autoplay
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            webSettings.setMediaPlaybackRequiresUserGesture(false);
        }

        webSettings.setJavaScriptEnabled(true);//设置WebView是否允许执行JavaScript脚本,默认false
        webSettings.setSupportZoom(true);//WebView是否支持使用屏幕上的缩放控件和手势进行缩放,默认值true
        webSettings.setBuiltInZoomControls(true);//是否使用内置的缩放机制
        webSettings.setDisplayZoomControls(false);//使用内置的缩放机制时是否展示缩放控件,默认值true

        webSettings.setUseWideViewPort(true);//是否支持HTML的“viewport”标签或者使用wide viewport
        webSettings.setLoadWithOverviewMode(true);//是否允许WebView度超出以概览的方式载入页面,默认false
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//设置布局,会引起WebView的重新布局(relayout),默认值NARROW_COLUMNS

        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);//线程优先级(在API18以上已废弃。不建议调整线程优先级，未来版本不会支持这样做)
        webSettings.setEnableSmoothTransition(true);//已废弃,将来会成为空操作（no-op）,设置当panning或者缩放或者持有当前WebView的window没有焦点时是否允许其光滑过渡,若为true,WebView会选择一个性能最大化的解决方案。例如过渡时WebView的内容可能不更新。若为false,WebView会保持精度（fidelity）,默认值false。
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//重写使用缓存的方式，默认值LOAD_DEFAULT
        webSettings.setPluginState(WebSettings.PluginState.ON);//在API18以上已废弃。未来将不支持插件,不要使用
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//让JavaScript自动打开窗口,默认false

        //webview 中localStorage无效的解决方法
        webSettings.setDomStorageEnabled(true);//DOM存储API是否可用,默认false
        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);//设置应用缓存内容的最大值
//        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
//        webSettings.setAppCachePath(appCachePath);//设置应用缓存文件的路径
//        webSettings.setAllowFileAccess(true);//是否允许访问文件,默认允许
//        webSettings.setAppCacheEnabled(true);//应用缓存API是否可用,默认值false,结合setAppCachePath(String)使用
    }
}
