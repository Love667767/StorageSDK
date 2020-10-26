package com.elson.executors.scheduler;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.elson.executors.TaskExecutors;

/**
 * Author : liuqi
 * Date   : 2020/10/26
 * Desc   :
 */
public class MainTaskExecutor implements MainExecutor {

    private Handler mMainHandler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(@NonNull Runnable runnable) {
        if (TaskExecutors.isMainThread()) {
            runnable.run();
        } else {
            mMainHandler.post(runnable);
        }
    }

    @Override
    public void execute(@NonNull Runnable runnable, long delay) {
        mMainHandler.postDelayed(runnable, delay);
    }

    @Override
    public void cancel(@NonNull Runnable runnable) {
        mMainHandler.removeCallbacks(runnable);
    }
}
