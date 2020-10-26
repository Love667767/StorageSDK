package com.elson.storage

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import com.elson.storage.config.StorageConfig
import com.elson.storage.executor.MainHandlerExecutor
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

    private var mConfig: StorageConfig? = null
    private var mExecutorService: ExecutorService? = null
    private var mMainService: Executor?=null

    fun getExecutorService(): ExecutorService {
        if (mExecutorService == null) {
            mExecutorService = Executors.newCachedThreadPool()
        }
        return mExecutorService!!
    }

    fun getMainExecutor(): Executor {
        if (mMainService == null) {
            mMainService = MainHandlerExecutor()
        }
        return mMainService!!
    }

    fun getConfig(): StorageConfig {
        return if (mConfig == null) StorageConfig() else mConfig!!
    }

    fun init(config: StorageConfig) {
        mConfig = config
        mExecutorService = config.mIOExecutorService
        mMainService = config.mMainExecutor
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
        getMainExecutor().execute {
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
                .setExtAppFileDir(Environment.DIRECTORY_PICTURES)
                .setOutputFileName(fileName)
                .asImage()
                .start()
    }

    @JvmStatic
    fun <T> storeImgPublishDir(context: Context, inputFile: T?, fileName: String?) {
        return with(context)
                .load(inputFile)
                .setExtPublishDir(getConfig().mImageDir, getConfig().mRootDir)
                .setOutputFileName(fileName)
                .asImage()
                .synSystemMedia()
                .start()
    }

    @JvmStatic
    fun <T> storeVideoPublishDir(context: Context, inputFile: T?, fileName: String?) {
        return with(context)
                .load(inputFile)
                .setExtPublishDir(getConfig().mVideoDir, getConfig().mRootDir)
                .setOutputFileName(fileName)
                .asVideo()
                .synSystemMedia()
                .start()
    }

    @JvmStatic
    fun <T> storeAudioPublishDir(context: Context, inputFile: T?, fileName: String?) {
        return with(context)
                .load(inputFile)
                .setExtPublishDir(getConfig().mAudioDir, getConfig().mRootDir)
                .setOutputFileName(fileName)
                .asAudio()
                .synSystemMedia()
                .start()
    }

    @JvmStatic
    fun <T> storeDownloadPublishDir(context: Context, inputFile: T?, fileName: String?) {
        return with(context)
                .load(inputFile)
                .setExtPublishDir(getConfig().mDownloadDir, getConfig().mRootDir)
                .setOutputFileName(fileName)
                .asDownload()
                .start()
    }

}