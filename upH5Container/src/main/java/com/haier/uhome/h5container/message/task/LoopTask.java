package com.haier.uhome.h5container.message.task;

/**
 * @description:
 * @author: pangrui
 * @email: pangrui@haier.com
 * @date: 2023/1/5 17:25
 */
abstract public class LoopTask {

    private boolean isLoop;

    public static final long defaultLoopInterval = 20;
    /**
     * 循环间隔
     * 单位：毫秒
     */
    private long loopInterval;

    /**
     * 普通任务构造
     * 默认循环标志 mIsLoop = false
     * 默认循环间隔 mLoopInterval = 0
     */
    public LoopTask() {
    }

    /**
     * 循环任务构造
     * 此构造执行时会设置循环标志 mIsLoop = true;
     *
     * @param loopInterval 循环间隔
     */
    public LoopTask(long loopInterval) {
        isLoop = true;
        this.loopInterval = Math.abs(loopInterval);
    }

    /**
     * 将要执行的业务
     */
    public abstract void run();

    /**
     * @return 是否为循环任务的标志
     */
    public boolean isLoop() {
        return isLoop;
    }

    /**
     * @return 循环间隔
     */
    public long getLoopInterval() {
        return loopInterval;
    }
}
