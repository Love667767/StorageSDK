package com.elson.executors.scheduler;

import androidx.annotation.NonNull;

import com.elson.executors.factory.TaskThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Author : liuqi
 * Date   : 2020/10/26
 * Desc   :
 */
public class ComputationTaskScheduler implements TaskScheduler {

    private static final String THREAD_NAME_PREFIX = "Elson-Computation-";

    @NonNull
    @Override
    public ExecutorService get() {
        int maxCount = Runtime.getRuntime().availableProcessors();
        return new ThreadPoolExecutor(1, maxCount,
                10L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), new TaskThreadFactory(THREAD_NAME_PREFIX));
    }
}
