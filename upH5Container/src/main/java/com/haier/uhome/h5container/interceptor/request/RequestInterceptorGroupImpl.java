package com.haier.uhome.h5container.interceptor.request;

import com.haier.uhome.h5container.interceptor.Interceptor;
import com.haier.uhome.h5container.interceptor.InterceptorGroup;

import java.util.Map;

/**
 * @description:
 * @author: pangrui
 * @email: pangrui@haier.com
 * @date: 2023/1/10 15:52
 */
public class RequestInterceptorGroupImpl extends InterceptorGroup {

    public RequestInterceptorGroupImpl() {
        super();
    }

    @Override
    public void loadInto(Map<Integer, Class<? extends Interceptor>> interceptor) {

        interceptor.put(0, ShowTitleInterceptor.class);
    }
}
