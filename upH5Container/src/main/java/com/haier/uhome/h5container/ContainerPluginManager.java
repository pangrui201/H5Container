package com.haier.uhome.h5container;


import com.haier.uhome.h5container.browse.CallBackFunction;
import com.haier.uhome.h5container.browse.PluginHandler;
import com.haier.uhome.h5container.message.MessageCenter;
import com.tencent.smtt.sdk.WebView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @description:
 * @author: pangrui
 * @email: pangrui@haier.com
 * @date: 2022/12/22 11:04
 */
public class ContainerPluginManager implements ContainerPluginInterface {

    private MessageCenter messageCenter;
    private WebView webView;

    Map<String, PluginHandler> pluginHandlers = new HashMap<String, PluginHandler>();

    public ContainerPluginManager(WebView webView) {
        this.webView = webView;
        this.messageCenter = new MessageCenter(webView);
    }

    public MessageCenter getMessageCenter() {
        return messageCenter;
    }


    @Override
    public void sendEvent(String eventName, String data) {
        messageCenter.receiveMsgFromNative(eventName, data);
    }

    /**
     * register handler,so that javascript can call it
     *
     * @param pluginName
     * @param handler
     */
    public void registerPlugin(String pluginName, PluginHandler handler) {
        pluginHandlers.put(pluginName, handler);
    }

    @Override
    public void unRegisterPlugin(String pluginName) {
        if (pluginHandlers.containsKey(pluginName)) {
            pluginHandlers.remove(pluginName);
        }
    }

    /**
     * 批量注册本地java方法，以供js端调用
     *
     * @param pluginNames 插件名称数组
     * @param handler     回调接口
     */
    @Override
    public void registerPlugins(final List<String> pluginNames, final PluginHandler handler) {
        if (handler != null) {
            for (final String pluginName : pluginNames) {
                this.registerPlugin(pluginName, new PluginHandler() {
                    @Override
                    public void handler(String pluginName, String data, CallBackFunction function) {
                        handler.handler(pluginName, data, function);
                    }
                });
            }
        }
    }

    @Override
    public void unRegisterPlugins(List<String> pluginNames) {
        for (String pluginName : pluginNames) {
            unRegisterPlugin(pluginName);
        }
    }

    @Override
    public PluginHandler findPluginHandlerByName(String pluginName) {
        if (pluginHandlers.containsKey(pluginName)) {
            return pluginHandlers.get(pluginName);
        }
        return null;
    }

}
