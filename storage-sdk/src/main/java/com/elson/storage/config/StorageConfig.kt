package com.elson.storage.config

import android.os.Environment
import com.elson.storage.helper.MediaHelper
import java.util.concurrent.ExecutorService

/**
 * Author : Elson
 * Date   : 2020/10/21
 * Desc   :
 */
class StorageConfig {

    var mRootDir = "Elson"
    var mImageDir = "${mRootDir}/${Environment.DIRECTORY_PICTURES}"
    var mVideoDir = "${mRootDir}/${Environment.DIRECTORY_MOVIES}"
    var mAudioDir = "${mRootDir}/${Environment.DIRECTORY_MUSIC}"
    var mDownloadDir = "${mRootDir}/${Environment.DIRECTORY_DOWNLOADS}"

    var mLogDir = mRootDir
    var mDefaultDir = mRootDir

    var mIOExecutorService: ExecutorService? = null


    // ----------- 沙盒内的目录 ----------
    fun getExtImageDir(): String {
        return checkVersion(Environment.DIRECTORY_PICTURES, mRootDir)
    }

    fun getExtVideoDir(): String {
        return checkVersion(Environment.DIRECTORY_MOVIES, mRootDir)
    }

    fun getExtAudioDir(): String {
        return checkVersion(Environment.DIRECTORY_MUSIC, mRootDir)
    }

    fun getExtDownloadDir(): String {
        return checkVersion(Environment.DIRECTORY_DOWNLOADS, mRootDir)
    }

    // ----------- 公共存储目录 ----------

    fun getPublishImageDir(): String {
        return checkVersion(mImageDir, mRootDir)
    }

    fun getPublishVideoDir(): String {
        return checkVersion(mVideoDir, mRootDir)
    }

    fun getPublishAudioDir(): String {
        return checkVersion(mAudioDir, mRootDir)
    }

    fun getPublishDownloadDir(): String {
        return checkVersion(mDownloadDir, mRootDir)
    }

    private fun checkVersion(legacyDir: String, androidQDir: String): String {
        return if (MediaHelper.checkAndroid_Q()) androidQDir else legacyDir
    }

}