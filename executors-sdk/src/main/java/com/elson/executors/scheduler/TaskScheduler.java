package com.elson.executors.scheduler;

import androidx.annotation.NonNull;

import java.util.concurrent.ExecutorService;

/**
 * Author : liuqi
 * Date   : 2020/10/26
 * Desc   :
 */
public interface TaskScheduler {

    @NonNull
    ExecutorService get();
}
