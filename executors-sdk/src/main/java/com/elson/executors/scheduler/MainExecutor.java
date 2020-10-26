package com.elson.executors.scheduler;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;

/**
 * Author : liuqi
 * Date   : 2020/10/26
 * Desc   :
 */
public interface MainExecutor extends Executor {

    void execute(@NonNull Runnable runnable, long delay);

    void cancel(@NonNull Runnable runnable);
}
