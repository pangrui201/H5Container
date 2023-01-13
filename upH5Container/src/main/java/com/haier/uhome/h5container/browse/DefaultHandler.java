package com.haier.uhome.h5container.browse;

public class DefaultHandler implements PluginHandler {

    String TAG = "DefaultHandler";

    @Override
    public void handler(String pluginName, String data, CallBackFunction function) {
        if (function != null) {
            function.onCallBack("DefaultHandler response data");
        }
    }
}
