package com.haier.uhome.h5container;


import com.haier.uhome.h5container.browse.PluginHandler;

import java.util.List;

public interface ContainerPluginInterface {

    public void sendEvent(String eventName,String data);

    public void registerPlugin(String pluginName, PluginHandler handler);

    public void unRegisterPlugin(String pluginName);

    public void registerPlugins(List<String> pluginNames, PluginHandler handler);

    public void unRegisterPlugins(List<String> pluginNames);

    public PluginHandler findPluginHandlerByName(String pluginName);
}
