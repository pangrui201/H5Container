package com.haier.uhome.h5container.exception;


/**
 * @description:
 * @author: pangrui
 * @email: pangrui@haier.com
 * @date: 2023/1/10 16:17
 */
public class HandlerException extends RuntimeException {
    public HandlerException(String detailMessage) {
        super(detailMessage);
    }
}
