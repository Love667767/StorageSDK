package com.elson.executors.factory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author : liuqi
 * Date   : 2020/10/26
 * Desc   :
 */
public class TaskThreadFactory implements ThreadFactory {

    private final AtomicInteger mThreadId = new AtomicInteger(0);

    private String prefix;
    private int priority;

    public TaskThreadFactory(String prefix) {
        this.prefix = prefix;
        this.priority = Thread.NORM_PRIORITY;
    }

    public TaskThreadFactory(String prefix, int priority) {
        this.prefix = prefix;
        this.priority = priority;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        Thread t = new Thread(runnable);
        t.setPriority(priority);
        t.setName(String.format("%s-%d", prefix, mThreadId.getAndIncrement()));
        return t;
    }
}
