package com.haier.uhome.h5container.message.task;

/**
 * @description:
 * @author: pangrui
 * @email: pangrui@haier.com
 * @date: 2023/1/5 18:03
 */
public interface TaskHandler {

        void start();

        void startDelay(long ms);

        void stop();
}
