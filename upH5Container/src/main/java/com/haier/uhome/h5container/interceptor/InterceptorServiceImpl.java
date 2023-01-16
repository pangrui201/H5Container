package com.haier.uhome.h5container.interceptor;

import android.content.Context;

import com.haier.uhome.h5container.exception.HandlerException;
import com.haier.uhome.h5container.Constant;
import com.haier.uhome.h5container.interceptor.request.RequestInterceptorGroupImpl;
import com.haier.uhome.h5container.message.Message;
import com.haier.uhome.h5container.thread.CancelableCountDownLatch;
import com.haier.uhome.h5container.thread.DefaultPoolExecutor;
import com.haier.uhome.h5container.utils.MapUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: pangrui
 * @email: pangrui@haier.com
 * @date: 2023/1/10 17:23
 */
public class InterceptorServiceImpl implements InterceptorService {

    private static volatile boolean interceptorHasInit;
    private List<Interceptor> requestInterceptors = new ArrayList<>();
    private static final Object interceptorInitLock = new Object();
    Map<Integer, Class<? extends Interceptor>> interceptorsClass;
    private ThreadPoolExecutor executor;


    public InterceptorServiceImpl() {
       this.executor =  DefaultPoolExecutor.getInstance();
    }

    @Override
    public void init(Context context) {
        this.executor.execute(new Runnable() {
            @Override
            public void run() {
                 interceptorsClass = new RequestInterceptorGroupImpl().getInterceptorGroup();
                if (MapUtils.isNotEmpty(interceptorsClass)) {
                    for (Map.Entry<Integer, Class<? extends Interceptor>> entry : interceptorsClass.entrySet()) {
                        Class<? extends Interceptor> interceptorClass = entry.getValue();
                        try {
                            Interceptor interceptor = interceptorClass.getConstructor().newInstance();
                            interceptor.init(context);
                            requestInterceptors.add(interceptor);
                        } catch (Exception ex) {
                            throw new HandlerException(Constant.TAG + "H5Container init interceptor error! name = [" + interceptorClass.getName() + "], reason = [" + ex.getMessage() + "]");
                        }
                    }
                    interceptorHasInit = true;
                    synchronized (interceptorInitLock) {
                        interceptorInitLock.notifyAll();
                    }
                }
            }
        });
    }

    @Override
    public void doInterceptions(Message message, InterceptorCallback callback) {
        if (MapUtils.isNotEmpty(interceptorsClass)) {
            checkInterceptorsInitStatus();
            if (!interceptorHasInit) {
                callback.onInterrupt(message,new HandlerException("Interceptors initialization takes too much time."));
                return;
            }
            this.executor.execute(new Runnable() {
                @Override
                public void run() {
                    CancelableCountDownLatch interceptorCounter = new CancelableCountDownLatch(interceptorsClass.size());
                    try {
                        _execute(0, interceptorCounter, message,callback);
                        interceptorCounter.await(300, TimeUnit.SECONDS);
                        if (interceptorCounter.getCount() > 0) {    // Cancel the navigation this time, if it hasn't return anythings.
                            callback.onInterrupt(message,new HandlerException("The interceptor processing timed out."));
                        } else {
                            callback.onContinue(message);
                        }
                    } catch (Exception e) {
                        callback.onInterrupt(message,e);
                    }
                }
            });
        } else {
            callback.onContinue(message);
        }
    }

    /**
     * Excute interceptor
     *
     * @param index   current interceptor index
     * @param counter interceptor counter
     * @param message message
     */
    private void _execute(final int index, final CancelableCountDownLatch counter, final Message message, InterceptorCallback callback) {
        if (index < requestInterceptors.size()) {
            Interceptor iInterceptor = requestInterceptors.get(index);
            iInterceptor.process(message, new InterceptorCallback() {
                @Override
                public void onContinue(Message message) {
                    // Last interceptor excute over with no exception.
                    counter.countDown();
                    _execute(index + 1, counter, message,callback);  // When counter is down, it will be execute continue ,but index bigger than interceptors size, then U know.
                }

                @Override
                public void onInterrupt(Message message,Throwable exception) {
                    // Last interceptor execute over with fatal exception.
                    counter.cancel();
                    callback.onInterrupt(message,exception);
                    // Be attention, maybe the thread in callback has been changed,
                    // then the catch block(L207) will be invalid.
                    // The worst is the thread changed to main thread, then the app will be crash, if you throw this exception!
//                    if (!Looper.getMainLooper().equals(Looper.myLooper())) {    // You shouldn't throw the exception if the thread is main thread.
//                        throw new HandlerException(exception.getMessage());
//                    }
                }
            });
        }
    }

    private static void checkInterceptorsInitStatus() {
        synchronized (interceptorInitLock) {
            while (!interceptorHasInit) {
                try {
                    interceptorInitLock.wait(10 * 1000);
                } catch (InterruptedException e) {
                    throw new HandlerException(Constant.TAG + "Interceptor init cost too much time error! reason = [" + e.getMessage() + "]");
                }
            }
        }
    }
}
