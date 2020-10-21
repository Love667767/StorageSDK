package com.elson.storage.helper

import android.text.TextUtils
import java.util.*

/**
 * Author : Elson
 * Date   : 2020/10/21
 * Desc   :
 */
object MimeTypeHelper {

    fun getImageMimeType(fileName: String): String {
        val type = FileHelper.getFileSuffix(fileName)?.toLowerCase(Locale.ROOT)
        return ""
    }

    @JvmStatic
    fun isSuffixOfImage(name: String): Boolean {
        return (!TextUtils.isEmpty(name) && name.endsWith(".PNG") || name.endsWith(".png") || name.endsWith(
            ".jpeg"
        )
                || name.endsWith(".gif") || name.endsWith(".GIF") || name.endsWith(".jpg")
                || name.endsWith(".webp") || name.endsWith(".WEBP") || name.endsWith(".JPEG")
                || name.endsWith(".bmp"))
    }
}