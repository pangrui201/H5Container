package com.haier.uhome.h5container.browse;


import org.json.JSONException;

public interface PluginHandler {

    void handler(String pluginName,String data, CallBackFunction function);

}
