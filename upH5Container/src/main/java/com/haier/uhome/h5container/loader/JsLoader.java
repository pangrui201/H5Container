package com.haier.uhome.h5container.loader;


import com.tencent.smtt.sdk.WebView;

/**
 * @description:
 * @author: pangrui
 * @email: pangrui@haier.com
 * @date: 2022/12/22 13:53
 */
public interface JsLoader {

  boolean isLoaded() ;

   void load(WebView view);

}
