package com.haier.uhome.h5container.message;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @description:
 * @author: pangrui
 * @email: pangrui@haier.com
 * @date: 2022/12/28 16:20
 */
public class RequestMessage extends Message {

    private String requestName;


    public String getRequestName() {
        return requestName;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

    private final static String REQUEST_NAME_STR = "requestName";

    @Override
    protected String getMessageType() {
        return MessageType.REQUEST;
    }

    public String toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ID_STR, getMessageId());
            jsonObject.put(DATA_STR, getData());
            jsonObject.put(TYPE_STR, getMessageType());
            jsonObject.put(REQUEST_NAME_STR, getRequestName());
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static RequestMessage toObject(String jsonStr) {
        RequestMessage m = new RequestMessage();
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            m.setMessageId(jsonObject.has(ID_STR) ? jsonObject.getString(ID_STR) : null);
            m.setData(jsonObject.has(DATA_STR) ? jsonObject.getString(DATA_STR) : null);
            m.setRequestName(jsonObject.has(REQUEST_NAME_STR) ? jsonObject.getString(REQUEST_NAME_STR) : null);
            return m;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return m;
    }
}
