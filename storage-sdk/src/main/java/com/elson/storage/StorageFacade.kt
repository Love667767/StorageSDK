package com.elson.storage

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.elson.storage.config.StorageConfig
import com.elson.storage.executor.MainHandlerExecutor
import com.elson.storage.request.BaseRequest
import com.elson.storage.request.RequestManager
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

    fun getConfig(): StorageConfig {
        return if (mConfig == null) StorageConfig() else mConfig!!
    }

    fun init(config: StorageConfig) {
        mConfig = config
        mExecutorService = config.mIOExecutorService
    }

    @JvmStatic
    fun with(context: Context): RequestManager {
        return RequestManager(context)
    }

    @JvmStatic
    fun scanFile(context: Context, file: File) {
        context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)))
    }

    @JvmStatic
    fun showToast(context: Context, text: String) {
        mMainService.execute {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 沙盒
     */
    @JvmStatic
    fun storeImgExtAppDir(context: Context, inputFile: File?, fileName: String?) {
        return with(context)
                .load(inputFile)
                .setExtAppFileDir(getConfig().getExtImageDir())
                .setOutputFileName(fileName)
                .start()
    }

    @JvmStatic
    fun storeImgPublishDir(context: Context, inputFile: File?, fileName: String?): BaseRequest<*> {
        return with(context)
                .load(inputFile)
                .setExtPublishDir(getConfig().getPublishImageDir())
                .setOutputFileName(fileName)
                .synSystemMedia()
    }

    @JvmStatic
    fun storeVideoPublishDir(context: Context, inputFile: File?, fileName: String?): BaseRequest<*> {
        return with(context)
                .load(inputFile)
                .setExtPublishDir(getConfig().getPublishVideoDir())
                .setOutputFileName(fileName)
                .synSystemMedia()
    }

    @JvmStatic
    fun storeAudioPublishDir(context: Context, inputFile: File?, fileName: String?): BaseRequest<*> {
        return with(context)
                .load(inputFile)
                .setExtPublishDir(getConfig().getPublishAudioDir())
                .setOutputFileName(fileName)
                .synSystemMedia()
    }

    @JvmStatic
    fun storeDownloadPublishDir(context: Context, inputFile: File?, fileName: String?): BaseRequest<*> {
        return with(context)
                .load(inputFile)
                .setExtPublishDir(getConfig().getPublishDownloadDir())
                .setOutputFileName(fileName)
    }

}