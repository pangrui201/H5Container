package com.haier.uhome.h5container.message;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @description:
 * @author: pangrui
 * @email: pangrui@haier.com
 * @date: 2022/12/28 16:31
 */
public class EventMessage extends Message {


    private String eventName;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    private final static String EVENT_NAME_STR = "eventName";

    @Override
    protected String getMessageType() {
        return MessageType.EVENT;
    }

    @Override
    public String toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(DATA_STR, getData());
            jsonObject.put(EVENT_NAME_STR, getEventName());
            jsonObject.put(ID_STR, getMessageId());
            jsonObject.put(TYPE_STR, getMessageType());
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static EventMessage toObject(String jsonStr) {
//        EventMessage m = new EventMessage();
//        try {
//            JSONObject jsonObject = new JSONObject(jsonStr);
//            m.setEventName(jsonObject.has(EVENT_NAME_STR) ? jsonObject.getString(EVENT_NAME_STR) : null);
//            m.setData(jsonObject.has(DATA_STR) ? jsonObject.getString(DATA_STR) : null);
//            return m;
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return m;
//    }
}
