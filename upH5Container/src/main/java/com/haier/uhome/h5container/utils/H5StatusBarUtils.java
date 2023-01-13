package com.haier.uhome.h5container.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.Window;

/**
 * @description:
 * @author: pangrui
 * @email: pangrui@haier.com
 * @date: 2023/1/9 18:49
 */
public class H5StatusBarUtils {

    public static boolean isSupport() {

        return Build.VERSION.SDK_INT >= 21;
    }

    @TargetApi(21)
    public static void setTransparentColor(Activity activity, int color) {
        if (isSupport() && activity != null) {
            Window window;
            (window = activity.getWindow()).clearFlags(67108864);
            window.addFlags(-2147483648);
            window.getDecorView().setSystemUiVisibility(1280);
            window.setStatusBarColor(color);
        }
    }

    public static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

}
