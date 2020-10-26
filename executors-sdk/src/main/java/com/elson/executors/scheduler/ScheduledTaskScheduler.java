package com.elson.executors.scheduler;

import androidx.annotation.NonNull;

import com.elson.executors.factory.TaskThreadFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Author : liuqi
 * Date   : 2020/10/26
 * Desc   :
 */
public class ScheduledTaskScheduler implements TaskScheduler {

    private static final String THREAD_NAME_PREFIX = "Elson-Scheduled-";

    @NonNull
    @Override
    public ScheduledExecutorService get() {
        return Executors.newScheduledThreadPool(1, new TaskThreadFactory(THREAD_NAME_PREFIX));
    }
}
