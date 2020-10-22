package com.elson.storage.executor

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor

/**
 * Author : liuqi
 * Date   : 2020/10/22
 * Desc   :
 */
class MainHandlerExecutor(var handler: Handler? = Handler(Looper.getMainLooper())) : Executor {

    override fun execute(command: Runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            command.run()
        } else {
            handler?.post(command)
        }
    }
}