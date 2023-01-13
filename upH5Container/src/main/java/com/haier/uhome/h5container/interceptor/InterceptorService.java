package com.haier.uhome.h5container.interceptor;

import android.content.Context;

import com.haier.uhome.h5container.message.Message;

/**
 * @description:
 * @author: pangrui
 * @email: pangrui@haier.com
 * @date: 2023/1/10 17:21
 */
public interface InterceptorService {

    /**
     * Do your init work in this method, it well be call when processor has been load.
     *
     * @param context ctx
     */
    void init(Context context);

    /**
     * Do interceptions
     */
    void doInterceptions(Message message, InterceptorCallback callback);

}
