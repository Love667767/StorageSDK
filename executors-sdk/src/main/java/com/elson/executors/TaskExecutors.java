package com.elson.executors;

import android.os.Looper;

import androidx.annotation.NonNull;

import com.elson.executors.scheduler.ComputationTaskScheduler;
import com.elson.executors.scheduler.IOTaskScheduler;
import com.elson.executors.scheduler.MainExecutor;
import com.elson.executors.scheduler.MainTaskExecutor;
import com.elson.executors.scheduler.ScheduledTaskScheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Author : liuqi
 * Date   : 2020/10/25
 * Desc   :
 */
public class TaskExecutors {

    static final MainExecutor MAIN;
    static final ExecutorService IO;
    static final ExecutorService COMPUTATION;
    static final ScheduledExecutorService SCHEDULED;

    static {
        MAIN = new MainTaskExecutor();
        IO = new IOTaskScheduler().get();
        COMPUTATION = new ComputationTaskScheduler().get();
        SCHEDULED = new ScheduledTaskScheduler().get();
    }

    public static MainExecutor mainThread() {
        return MAIN;
    }

    public static ExecutorService io() {
        return IO;
    }

    public static ExecutorService computation() {
        return COMPUTATION;
    }

    public static ScheduledExecutorService scheduledThread() {
        return SCHEDULED;
    }


    // ------------------- Easy method ----------------------

    public static void runOnUIThread(Runnable runnable) {
        MAIN.execute(runnable);
    }

    public static void runOnUIThread(Runnable runnable, long delay) {
        MAIN.execute(runnable, delay);
    }

    public static void cancelOnUIThread(Runnable runnable) {
        MAIN.cancel(runnable);
    }

    public static Future runOnIOThread(Runnable runnable) {
        return IO.submit(runnable);
    }

    public static Future runOnComputeThread(Runnable runnable) {
        return COMPUTATION.submit(runnable);
    }

    /**
     * 延迟任务
     */
    public static ScheduledFuture schedule(@NonNull Runnable command, long delay, @NonNull TimeUnit unit) {
        return SCHEDULED.schedule(command, delay, unit);
    }

    /**
     * 定时任务
     */
    public static ScheduledFuture scheduleAtFixedRate(@NonNull Runnable command,
                                                      long initialDelay, long period,
                                                      @NonNull TimeUnit unit) {
        return SCHEDULED.scheduleAtFixedRate(command, initialDelay, period, unit);
    }

    public static boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }
}
