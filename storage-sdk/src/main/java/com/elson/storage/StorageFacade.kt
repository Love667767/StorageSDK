package com.elson.storage

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.elson.storage.config.StorageConfig
import com.elson.storage.executor.MainHandlerExecutor
import com.elson.storage.helper.MediaHelper
import com.elson.storage.request.AndroidQRequest
import com.elson.storage.request.BaseRequest
import com.elson.storage.request.LegacyRequest
import java.io.File
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Author : Elson
 * Date   : 2020/10/21
 * Desc   : 门面类
 */
object StorageFacade {

    internal var mConfig: StorageConfig? = null
    private var mExecutorService: ExecutorService? = null
    internal val mMainService: Executor by lazy { MainHandlerExecutor() }

    fun getExecutorService(): ExecutorService {
        if (mExecutorService == null) {
            mExecutorService = Executors.newCachedThreadPool()
        }
        return mExecutorService!!
    }

    fun getMainExecutor(): Executor {
        return mMainService
    }

    fun init(config: StorageConfig) {
        mConfig = config
        mExecutorService = config.mIOExecutorService
    }

    @JvmStatic
    fun with(context: Context): BaseRequest {
        return if (MediaHelper.checkAndroid_Q()) {
            AndroidQRequest(context.applicationContext)
        } else {
            LegacyRequest(context.applicationContext)
        }
    }

    @JvmStatic
    fun scanFile(context: Context, file: File) {
        context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)))
    }

    @JvmStatic
    fun showToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    @JvmStatic
    fun storeImage(context: Context, inputFile: File?, fileName: String?): BaseRequest {
        return with(context)
                .asImage()
                .setInputFile(inputFile)
                .setOutputFileName(fileName)
    }

    @JvmStatic
    fun storeVideo(context: Context, inputFile: File?, fileName: String?): BaseRequest {
        return with(context)
                .asVideo()
                .setInputFile(inputFile)
                .setOutputFileName(fileName)
    }

}