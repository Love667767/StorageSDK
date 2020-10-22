package com.elson.storage.helper

import android.content.Context
import android.os.Environment
import java.io.File

/**
 * Author : Elson
 * Date   : 2020/10/21
 * Desc   :
 */
object PathHelper {

    /**
     * 手机内文件目录
     */
    @JvmStatic
    fun getFileDir(context: Context, filePath: String? = null): File {
        return FileHelper.appendPath(context.filesDir, filePath)!!
    }

    /**
     * 手机内缓存目录
     */
    @JvmStatic
    fun getCacheDir(context: Context, filePath: String? = null): File {
        return FileHelper.appendPath(context.cacheDir, filePath)!!
    }

    /**
     * SD卡应用文件目录
     */
    @JvmStatic
    fun getExternalFileDir(context: Context, filePath: String? = null): File? {
        return context.getExternalFilesDir(filePath)
    }

    /**
     * SD卡应用缓存目录
     */
    @JvmStatic
    fun getExternalCacheDir(context: Context, filePath: String? = null): File? {
        return FileHelper.appendPath(context.externalCacheDir, filePath)
    }

    /**
     * SD卡根目录
     */
    @JvmStatic
    fun getLegacyExternalRootDir(context: Context, filePath: String? = null): File? {
        if (filePath.isNullOrBlank()) {
            return Environment.getExternalStorageDirectory()
        }
        return Environment.getExternalStorageDirectory().resolve(filePath)
    }

    /**
     * SD卡系统图片目录
     */
    @JvmStatic
    fun getLegacyExternalImageDir(context: Context): File? {
        return getLegacyExternalRootDir(context, Environment.DIRECTORY_PICTURES)
    }

    /**
     * SD卡系统视频目录
     */
    @JvmStatic
    fun getLegacyExternalVideoDir(context: Context): File? {
        return getLegacyExternalRootDir(context, Environment.DIRECTORY_MOVIES)
    }

    /**
     * SD卡系统音频目录
     */
    @JvmStatic
    fun getLegacyExternalAudioDir(context: Context): File? {
        return getLegacyExternalRootDir(context, Environment.DIRECTORY_MUSIC)
    }

}