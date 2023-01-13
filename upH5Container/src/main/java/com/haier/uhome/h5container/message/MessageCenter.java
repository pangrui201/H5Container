package com.haier.uhome.h5container.message;

import android.os.SystemClock;
import android.text.TextUtils;

import com.haier.uhome.h5container.Constant;
import com.haier.uhome.h5container.ContainerPluginManager;
import com.haier.uhome.h5container.browse.BridgeWebView;
import com.haier.uhome.h5container.browse.CallBackFunction;
import com.haier.uhome.h5container.browse.DefaultHandler;
import com.haier.uhome.h5container.browse.PluginHandler;
import com.haier.uhome.h5container.interceptor.InterceptorCallback;
import com.haier.uhome.h5container.interceptor.InterceptorService;
import com.haier.uhome.h5container.interceptor.InterceptorServiceImpl;
import com.haier.uhome.h5container.message.task.LoopTask;
import com.haier.uhome.h5container.message.task.LoopTaskHandler;
import com.haier.uhome.h5container.message.task.TaskHandler;
import com.tencent.smtt.sdk.WebView;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @description:
 * @author: pangrui
 * @email: pangrui@haier.com
 * @date: 2022/12/22 14:29
 */
public class MessageCenter {

    private AtomicLong uniqueId = new AtomicLong(0);
    private MessageHandler messageHandler;
    PluginHandler defaultHandler = new DefaultHandler();
    private BridgeWebView bridgeWebView;
    private LinkedBlockingQueue<Message> messageQueue = new LinkedBlockingQueue<>();
    private TaskHandler taskHandler;
    private InterceptorService requestMessageInterceptorService;

    public MessageCenter(WebView webView) {
        this.bridgeWebView = (BridgeWebView) webView;
        if (messageHandler == null) {
            messageHandler = new MessageHandlerImpl(webView);
        }
        requestMessageInterceptorService = new InterceptorServiceImpl();
        requestMessageInterceptorService.init(bridgeWebView.getContext());
        initLoopTask();
    }

    private void initLoopTask() {
        taskHandler = new LoopTaskHandler(new LoopTask(LoopTask.defaultLoopInterval) {
            @Override
            public void run() {
                if (!messageQueue.isEmpty()) {
                    Message message = messageQueue.poll();
                    messageHandler.handler(message);
                } else {
                    taskHandler.stop();
                }
            }
        });
    }

    public void receiveMsgFromNative(String eventName, String data) {
        EventMessage message = assembleMessage(eventName, data);
        messageQueue.add(message);
        if (this.bridgeWebView.isJsFrameworkLoad()) {
            if (!((LoopTaskHandler) this.taskHandler).isRun()) {
                this.taskHandler.start();
            }
        }
    }

    public void receiveMsgFromJS(String message) {
        RequestMessage m = RequestMessage.toObject(message);
        requestMessageInterceptorService.doInterceptions(m, new InterceptorCallback() {
            @Override
            public void onContinue(Message message) {
                handlerMessageByPlugin((RequestMessage) message);
            }

            @Override
            public void onInterrupt(Message message, Throwable exception) {
                ResponseMessage responseMsg = new ResponseMessage();
                responseMsg.setMessageId(message.getMessageId());
                responseMsg.setData(message.getData());
                if (exception != null) {
                    responseMsg.setException(exception.getMessage());
                }
                messageQueue.add(responseMsg);
                startLoopMessage();
            }

        });
    }

    private void handlerMessageByPlugin(RequestMessage m) {
        CallBackFunction responseFunction;
        String requestId = m.getMessageId();
        if (!TextUtils.isEmpty(requestId)) {
            responseFunction = new CallBackFunction() {
                @Override
                public void onCallBack(String data) {
                    ResponseMessage responseMsg = new ResponseMessage();
                    responseMsg.setMessageId(requestId);
                    responseMsg.setData(data);
                    messageQueue.add(responseMsg);
                    startLoopMessage();
                }
            };
        } else {
            responseFunction = new CallBackFunction() {
                @Override
                public void onCallBack(String data) {
                    //to nothing
                }
            };
        }
        ContainerPluginManager containerManager = bridgeWebView.getContainerManager();
        PluginHandler handler;
        if (!TextUtils.isEmpty(m.getRequestName())) {
            handler = containerManager.findPluginHandlerByName(m.getRequestName());
        } else {
            handler = defaultHandler;
        }
        handler.handler(m.getRequestName(), m.getData(), responseFunction);
    }

    private void startLoopMessage() {
        if (bridgeWebView.isJsFrameworkLoad()) {
            taskHandler.start();
        }
    }

    /**
     * @param handler default handler,handle messages send by js without assigned handler name,
     *                if js message has handler name, it will be handled by named handlers registered by native
     */
    public void setDefaultHandler(PluginHandler handler) {
        this.defaultHandler = handler;
    }


    private EventMessage assembleMessage(String eventName, String data) {
        EventMessage m = new EventMessage();
        if (!TextUtils.isEmpty(eventName)) {
            m.setEventName(eventName);
        }
        if (!TextUtils.isEmpty(data)) {
            m.setData(data);
        }
        m.setMessageId(createUniqueId());
        return m;
    }

    public void startTaskHandler() {
        if (!((LoopTaskHandler) this.taskHandler).isRun()) {
            this.taskHandler.start();
        }
    }

    private String createUniqueId() {
        return String.format(Constant.CALLBACK_ID_FORMAT, uniqueId.incrementAndGet() + (Constant.UNDERLINE_STR + SystemClock.currentThreadTimeMillis()));
    }

}
