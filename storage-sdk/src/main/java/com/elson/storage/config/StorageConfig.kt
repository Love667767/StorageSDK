package com.elson.storage.config

import android.os.Environment
import com.elson.storage.helper.MediaHelper
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService

/**
 * Author : Elson
 * Date   : 2020/10/21
 * Desc   :
 */
class StorageConfig(var mRootDir: String = "") {

    var mImageDir = "${mRootDir}/${Environment.DIRECTORY_DCIM}"
    var mVideoDir = "${mRootDir}/${Environment.DIRECTORY_MOVIES}"
    var mAudioDir = "${mRootDir}/${Environment.DIRECTORY_MUSIC}"
    var mDownloadDir = "${mRootDir}/${Environment.DIRECTORY_DOWNLOADS}"

    var mIOExecutorService: ExecutorService? = null

    var mMainExecutor: Executor? = null

}