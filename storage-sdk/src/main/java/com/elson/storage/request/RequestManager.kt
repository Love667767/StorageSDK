package com.elson.storage.request

import android.content.Context
import android.graphics.Bitmap
import com.elson.storage.helper.MediaHelper
import okio.Source
import java.io.File

/**
 * Author : liuqi
 * Date   : 2020/10/24
 * Desc   :
 */
class RequestManager(var context: Context) {

    fun load(file: File?): BaseRequest<File> {
        return if (MediaHelper.checkAndroid_Q()) {
            AndroidQRequest<File>(context.applicationContext)
        } else {
            LegacyRequest<File>(context.applicationContext)
        }.apply {
            mInputModel = file
        }
    }

    fun load(bitmap: Bitmap?): BaseRequest<Bitmap> {
        return if (MediaHelper.checkAndroid_Q()) {
            AndroidQRequest<Bitmap>(context.applicationContext)
        } else {
            LegacyRequest<Bitmap>(context.applicationContext)
        }.apply {
            mInputModel = bitmap
        }
    }

    fun load(source: Source?): BaseRequest<Source> {
        return if (MediaHelper.checkAndroid_Q()) {
            AndroidQRequest<Source>(context.applicationContext)
        } else {
            LegacyRequest<Source>(context.applicationContext)
        }.apply {
            mInputModel = source
        }
    }

    fun load(model: Any?): BaseRequest<*> {
        return if (MediaHelper.checkAndroid_Q()) {
            AndroidQRequest<Any>(context.applicationContext)
        } else {
            LegacyRequest<Any>(context.applicationContext)
        }.apply {
            mInputModel = model
        }
    }
}