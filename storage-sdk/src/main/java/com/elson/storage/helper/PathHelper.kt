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

    @JvmStatic
    fun getFileDir(context: Context, filePath: String? = null): File {
        return FileHelper.appendPath(context.filesDir, filePath)!!
    }

    @JvmStatic
    fun getCacheDir(context: Context, filePath: String? = null): File {
        return FileHelper.appendPath(context.cacheDir, filePath)!!
    }

    @JvmStatic
    fun getExternalFileDir(context: Context, filePath: String? = null): File? {
        return context.getExternalFilesDir(filePath)
    }

    @JvmStatic
    fun getExternalCacheDir(context: Context, filePath: String? = null): File? {
        return FileHelper.appendPath(context.externalCacheDir, filePath)
    }

    @JvmStatic
    fun getLegacyExternalImageDir(context: Context, filePath: String? = null): File? {
        return Environment.getDataDirectory()
    }

    @JvmStatic
    fun getLegacyExternalVideoDir(context: Context, filePath: String? = null): File? {
        return context.getExternalFilesDir(filePath)
    }

    @JvmStatic
    fun getExternalPublishVideoDir(context: Context, filePath: String? = null): File? {
        return FileHelper.appendPath(context.externalCacheDir, filePath)
    }
}