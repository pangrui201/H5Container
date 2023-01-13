package com.haier.uhome.h5container.message;

/**
 * @description:
 * @author: pangrui
 * @email: pangrui@haier.com
 * @date: 2022/12/28 16:18
 */
public abstract class Message {

    private String data;

    private String messageId;

    private String messageType = getMessageType();

    protected final static String DATA_STR = "data";
    protected final static String ID_STR = "messageId";
    protected final static String TYPE_STR = "messageType";

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    protected abstract String getMessageType();

    public abstract String toJson();


}
