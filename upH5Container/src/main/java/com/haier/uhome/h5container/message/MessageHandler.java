package com.haier.uhome.h5container.message;

/**
 * @description:
 * @author: pangrui
 * @email: pangrui@haier.com
 * @date: 2022/12/22 13:30
 */
public interface MessageHandler {

     <T extends Message> void handler(T message);
}
