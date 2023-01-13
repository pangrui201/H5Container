package com.haier.uhome.h5container.interceptor;

import com.haier.uhome.h5container.message.Message;

/**
 * @description:
 * @author: pangrui
 * @email: pangrui@haier.com
 * @date: 2023/1/10 15:44
 */
public interface InterceptorCallback {
    /**
     * Continue process
     *
     * @param message msg
     */
    void onContinue(Message message);

    /**
     * Interrupt process, pipeline will be destroy when this method called.
     *
     * @param exception Reson of interrupt.
     */
    void onInterrupt(Message message,Throwable exception);
}
