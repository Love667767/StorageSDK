package com.elson.storage.helper

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import java.io.File
import java.util.*

/**
 * Author : Elson
 * Date   : 2020/10/21
 * Desc   :
 */
object MediaHelper {

    fun checkAndroid_Q(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !Environment.isExternalStorageLegacy()
    }

    fun insertImage(context: Context, file: File) {
        MediaStore.Images.Media.insertImage(context.contentResolver, file.parent, file.name, file.name)
    }

    fun insertImage_Q(context: Context, fileName: String): Uri? {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis().toString())
        values.put(MediaStore.Images.Media.MIME_TYPE, MimeTypeHelper.getImageMimeType(fileName))
//        values.put(MediaStore.Images.Media.RELATIVE_PATH, PictureMimeType.DCIM)
        return context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
    }





}