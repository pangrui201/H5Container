package com.haier.uhome.h5container.message.task;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;


/**
 * @description:
 * @author: pangrui
 * @email: pangrui@haier.com
 * @date: 2023/1/5 17:24
 */
public class LoopTaskHandler extends Handler implements TaskHandler {

    private static final String TAG = "TimerTaskHandler";

    private LoopTask task;

    private volatile boolean run;

    public LoopTaskHandler(LoopTask task) {
        super(Looper.getMainLooper());
        this.task = task;
    }

    @Override
    public void handleMessage(Message msg) {
        if (!run) {
            return;
        }
        LoopTask currentTask = this.task;
        if (null == currentTask) {
            Log.e(TAG, "handleMessage: No task，please create it first!");
        } else {
            currentTask.run();
            if (currentTask.isLoop()) {
                long loopInterval = currentTask.getLoopInterval();
                sendEmptyMessageDelayed(0, loopInterval);
                Log.i(TAG, "handleMessage: The task is a loop task, and it will be executed after " + loopInterval + " millis");
            } else {
                Log.i(TAG, "handleMessage: The task is not a loop task, it has been executed ");
            }
        }
    }

    public boolean isRun() {
        return run;
    }

    @Override
    public void start() {
        if (run) {
            return;
        }
        run = true;
        sendEmptyMessage(0);
    }

    @Override
    public void startDelay(long ms) {
        if (run) {
            return;
        }
        run = true;
        sendEmptyMessageDelayed(0, ms);
    }

    @Override
    public void stop() {
        run = false;
        removeMessages(0);
        removeCallbacksAndMessages(null);
    }

}


