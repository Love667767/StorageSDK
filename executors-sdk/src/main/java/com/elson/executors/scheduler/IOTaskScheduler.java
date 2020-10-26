package com.elson.executors.scheduler;

import com.elson.executors.factory.TaskThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author : liuqi
 * Date   : 2020/10/26
 * Desc   :
 */
public class IOTaskScheduler implements TaskScheduler {

    private static final String THREAD_NAME_PREFIX = "Elson-IO-";

    @Override
    public ExecutorService get() {
        return Executors.newCachedThreadPool(new TaskThreadFactory(THREAD_NAME_PREFIX));
    }
}
