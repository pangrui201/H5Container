package com.haier.uhome.h5container.message;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @description:
 * @author: pangrui
 * @email: pangrui@haier.com
 * @date: 2022/12/28 16:24
 */
public class ResponseMessage extends Message {

    private static final String EXCEPTION_STR ="exception";

    private String exception;

    private String messageType;

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    @Override
    protected String getMessageType() {
        return MessageType.RESPONSE;
    }

    @Override
    public String toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ID_STR, getMessageId());
            jsonObject.put(DATA_STR, getData());
            jsonObject.put(TYPE_STR, getMessageType());
            jsonObject.put(EXCEPTION_STR, getException());
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResponseMessage toObject(String jsonStr) {
        ResponseMessage m = new ResponseMessage();
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            m.setMessageId(jsonObject.has(ID_STR) ? jsonObject.getString(ID_STR) : null);
            m.setData(jsonObject.has(DATA_STR) ? jsonObject.getString(DATA_STR) : null);
            m.setException(jsonObject.has(EXCEPTION_STR) ? jsonObject.getString(EXCEPTION_STR) : null);
            return m;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return m;
    }

}
