package com.haier.uhome.h5container.loader;


import com.tencent.smtt.sdk.WebView;

import com.haier.uhome.h5container.utils.BridgeUtil;
import com.haier.uhome.h5container.Constant;
/**
 * @description:
 * @author: pangrui
 * @email: pangrui@haier.com
 * @date: 2022/12/22 13:53
 */
public class JsFrameworkLoader implements JsLoader {

    private boolean isLoaded = false;

    @Override
    public boolean isLoaded() {
        return isLoaded;
    }

    @Override
    public void load(WebView view) {
        if (Constant.JS_FRAMEWORK_SRC != null) {
            isLoaded = false;
            BridgeUtil.webViewEvaluateLocalScript(view, Constant.JS_FRAMEWORK_SRC);
            isLoaded = true;
        }
    }

}
