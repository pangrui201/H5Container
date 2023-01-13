package com.haier.uhome.h5container.utils;

import static com.haier.uhome.h5container.utils.PetrelLog.logger;

import android.net.Uri;
import android.text.TextUtils;

import com.haier.uhome.h5container.bean.UrlParams;

import java.net.URLDecoder;

/**
 * @description:
 * @author: pangrui
 * @email: pangrui@haier.com
 * @date: 2023/1/9 17:00
 */
public class UrlUtil {
    private static final String UNDERNEATH_STATUS_BAR = "underneathStatusBar";
    private static final String KEY_IS_SHOW_TITLE = "show_title_bar";
    private static final String KEY_PAGE_TITLE = "page_title";
    private static final String KEY_IS_SHOW_AD = "adshow";
    private static final String KEY_IS_AD_TEXT = "adtext";
    private static final String KEY_IS_AD_BUTTON = "adbutton";
    private static final String KEY_IS_AD_SHOW_CLOSE = "adshowclose";
    private static final String KEY_IS_AD_ACTION = "adaction";
    private static final String KEY_IS_AD_URL = "adurl";

    public static UrlParams parseUrl(String url) {
        String isShowTitle = getUrlParams(url, KEY_IS_SHOW_TITLE);
        String hideStatusBar = getUrlParams(url, UNDERNEATH_STATUS_BAR);
        String title = getUrlParams(url, KEY_PAGE_TITLE);
        UrlParams urlParams = new UrlParams();
        logger().error("nebula open page title1={}", title);
        urlParams.setOpenWitchTranslucentStatusBar("1".equalsIgnoreCase(hideStatusBar));
        urlParams.setPageTitle(title);
        urlParams.setShowTitle("true".equalsIgnoreCase(isShowTitle));
        urlParams.setAdShow("true".equalsIgnoreCase(getUrlParams(url, KEY_IS_SHOW_AD)));
        urlParams.setAdShowClose(!"false".equalsIgnoreCase(getUrlParams(url, KEY_IS_AD_SHOW_CLOSE)));
        urlParams.setAdAction(getUrlParams(url, KEY_IS_AD_ACTION));
        try {
            String adText = getUrlParams(url, KEY_IS_AD_TEXT);
            String adButton = getUrlParams(url, KEY_IS_AD_BUTTON);
            String adUrl = getUrlParams(url, KEY_IS_AD_URL);
            urlParams.setAdText(TextUtils.isEmpty(adText) ? "" : URLDecoder.decode(adText, "UTF-8"));
            urlParams.setAdButton(TextUtils.isEmpty(adButton) ? "" : URLDecoder.decode(adButton, "UTF-8"));
            urlParams.setAdUrl(TextUtils.isEmpty(adUrl) ? "" : URLDecoder.decode(adUrl, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return urlParams;
    }

    public static String getUrlParams(String url, String key) {
        if (TextUtils.isEmpty(url)) {
            return "";
        } else {
            try {
                Uri uri = Uri.parse(url);
                String value = uri.getQueryParameter(key);
                if (!TextUtils.isEmpty(value)) {
                    return value;
                }
            } catch (Exception var10) {
                logger().warn("error " + var10);
            }

            if (url.indexOf("?") > 0) {
                String query = url.substring(url.indexOf("?") + 1);
                if (!TextUtils.isEmpty(query)) {
                    String[] params = query.split("[?&#/]");
                    String[] var5 = params;
                    int var6 = params.length;

                    for(int var7 = 0; var7 < var6; ++var7) {
                        String param = var5[var7];
                        String[] keyValue = param.split("=");
                        if (keyValue != null && keyValue.length == 2 && keyValue[0].equals(key)) {
                            return keyValue[1];
                        }
                    }
                }
            }

            return null;
        }
    }
}
