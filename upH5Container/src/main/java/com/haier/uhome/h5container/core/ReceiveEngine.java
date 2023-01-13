package com.haier.uhome.h5container.core;

import org.json.JSONException;

/**
 * @description:
 * @author: pangrui
 * @email: pangrui@haier.com
 * @date: 2022/12/22 15:19
 */
public interface ReceiveEngine {

    void  receiveFromJs(String message) throws JSONException;
}
