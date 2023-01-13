package com.haier.uhome.h5container.interceptor;

import java.util.Map;

/**
 * @description:
 * @author: pangrui
 * @email: pangrui@haier.com
 * @date: 2023/1/10 15:41
 */
public abstract class InterceptorGroup {

    Map<Integer, Class<? extends Interceptor>> interceptors;

    public InterceptorGroup() {
        if (interceptors == null) {
            interceptors = new UniqueKeyMap<>("More than one interceptors use same key !");
        }
        loadInto(interceptors);
    }

    public Map<Integer, Class<? extends Interceptor>> getInterceptorGroup() {
        return interceptors;
    }

    /**
     * Load interceptor to input
     *
     * @param interceptor input
     */
    public abstract void loadInto(Map<Integer, Class<? extends Interceptor>> interceptor);
}
