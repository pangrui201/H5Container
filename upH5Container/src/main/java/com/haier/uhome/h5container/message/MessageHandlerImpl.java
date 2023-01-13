package com.haier.uhome.h5container.message;


import com.haier.uhome.h5container.core.SendEngine;
import com.haier.uhome.h5container.core.SendEngineImpl;
import com.tencent.smtt.sdk.WebView;


/**
 * @description:
 * @author: pangrui
 * @email: pangrui@haier.com
 * @date: 2022/12/22 13:29
 */
public class MessageHandlerImpl implements MessageHandler {
    private SendEngine bridgeEngine;

    public MessageHandlerImpl(WebView webView) {
        bridgeEngine = new SendEngineImpl(webView);
    }

    /**
     * 消息处理器处理来自三种的消息:
     * 1.启动消息
     * 2.js访问插件后的ResponseMessage
     * 3.端上主动给js发的EventMessage
     * @param message
     */
    @Override
    public void handler(Message message) {
        dispatchMessage(message);
    }

    private void dispatchMessage(Message m) {/**/
        String messageJson = m.toJson();
        //escape special characters for json string
        messageJson = messageJson.replaceAll("(\\\\)([^utrn])", "\\\\\\\\$1$2");
        messageJson = messageJson.replaceAll("(?<=[^\\\\])(\")", "\\\\\"");
        bridgeEngine.sendToJS(messageJson);
    }
}
