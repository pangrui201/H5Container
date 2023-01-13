package com.haier.uhome.h5container.activity;

import com.haier.uhome.h5container.browse.BridgeWebView;

import java.util.Map;

/**
 * @description:
 * @author: pangrui
 * @email: pangrui@haier.com
 * @date: 2022/12/26 17:02
 */
public interface PageContainer {

    BridgeWebView supportedWebView();

    Map<String, String> pageHeaders();

    String pageUrl();

    String pageError();

}
