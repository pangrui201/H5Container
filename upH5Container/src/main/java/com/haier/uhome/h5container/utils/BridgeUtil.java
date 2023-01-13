package com.haier.uhome.h5container.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.tencent.smtt.sdk.WebView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BridgeUtil {

    public static void webViewEvaluateLocalScript(WebView view, String path) {
        String jsContent = assetFile2Str(view.getContext(), path);
        view.evaluateJavascript( jsContent,null);
    }

    public static String assetFile2Str(Context c, String urlStr) {
        InputStream in = null;
        try {
            in = c.getAssets().open(urlStr);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            StringBuilder sb = new StringBuilder();
            do {
                line = bufferedReader.readLine();
                if (line != null && !line.matches("^\\s*\\/\\/.*")) {
                    sb.append(line);
                }
            } while (line != null);

            bufferedReader.close();
            in.close();

            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

    public static class StatusBarUtils {
        /**
         * MIUI状态栏字体黑色与白色标识位
         */
        static final String IMMERSION_STATUS_BAR_DARK_MIUI = "EXTRA_FLAG_STATUS_BAR_DARK_MODE";
        /**
         * MIUI导航栏图标黑色与白色标识位
         */
        static final String IMMERSION_NAVIGATION_BAR_DARK_MIUI = "EXTRA_FLAG_NAVIGATION_BAR_DARK_MODE";

        public static void setSpecialBarDarkMode(Activity mActivity, Window mWindow, boolean statusBarDarkFont,
                                                 int flymeOSStatusBarFontColor) {
            if (OSUtils.isMIUI6Later()) {
                //修改miui状态栏字体颜色
                SpecialBarFontUtils.setMIUIBarDark(mWindow, IMMERSION_STATUS_BAR_DARK_MIUI, statusBarDarkFont);
                //修改miui导航栏图标为黑色
                SpecialBarFontUtils.setMIUIBarDark(mWindow, IMMERSION_NAVIGATION_BAR_DARK_MIUI, statusBarDarkFont);

            } else if (OSUtils.isFlymeOS4Later()) {
                if (flymeOSStatusBarFontColor != 0) {
                    SpecialBarFontUtils.setStatusBarDarkIcon(mActivity, flymeOSStatusBarFontColor);
                } else {
                    SpecialBarFontUtils.setStatusBarDarkIcon(mActivity, statusBarDarkFont);
                }
            }
        }

        /**
         * android 6.0设置字体颜色
         */
        public static void darkModeForM(Activity mActivity, Window window, boolean dark, int color) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            int systemUiVisibility = window.getDecorView().getSystemUiVisibility();
            if (dark) {
                systemUiVisibility |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                systemUiVisibility &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            window.getDecorView().setSystemUiVisibility(systemUiVisibility);

            setSpecialBarDarkMode(mActivity, window, dark, color);
        }
    }
}
