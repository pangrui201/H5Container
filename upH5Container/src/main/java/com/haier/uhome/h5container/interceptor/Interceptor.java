package com.haier.uhome.h5container.interceptor;

import android.content.Context;

import com.haier.uhome.h5container.message.Message;

/**
 * @description:
 * @author: pangrui
 * @email: pangrui@haier.com
 * @date: 2023/1/10 15:42
 */
public interface Interceptor {

    void init(Context context);

    /**
     * The operation of this interceptor.
     *
     * @param  message msg
     * @param callback cb
     */
    void process(Message message, InterceptorCallback callback);

}
