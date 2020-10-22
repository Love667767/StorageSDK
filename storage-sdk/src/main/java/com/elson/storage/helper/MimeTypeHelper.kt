package com.elson.storage.helper

import android.text.TextUtils
import java.util.*

/**
 * Author : Elson
 * Date   : 2020/10/21
 * Desc   :
 */
object MimeTypeHelper {

    @JvmStatic
    fun isSuffixOfImage(name: String): Boolean {
        return (!TextUtils.isEmpty(name) && name.endsWith(".PNG") || name.endsWith(".png") || name.endsWith(
            ".jpeg"
        )
                || name.endsWith(".gif") || name.endsWith(".GIF") || name.endsWith(".jpg")
                || name.endsWith(".webp") || name.endsWith(".WEBP") || name.endsWith(".JPEG")
                || name.endsWith(".bmp"))
    }

    /**
     * 获取照片的mine_type
     */
    @JvmStatic
    fun getImageMimeType(path: String): String {
        val lowerPath = path.toLowerCase(Locale.ROOT)
        if (lowerPath.endsWith("jpg") || lowerPath.endsWith("jpeg")) {
            return "image/jpeg"
        } else if (lowerPath.endsWith("png")) {
            return "image/png"
        } else if (lowerPath.endsWith("gif")) {
            return "image/gif"
        }
        return "image/jpeg"
    }


    @JvmStatic
    fun getAudioMimeType(path: String): String? {
//            val lowerPath = path.toLowerCase(Locale.ROOT)
////            if (lowerPath.endsWith("jpg") || lowerPath.endsWith("jpeg")) {
////                return "image/jpeg"
////            } else if (lowerPath.endsWith("png")) {
////                return "image/png"
////            } else if (lowerPath.endsWith("gif")) {
////                return "image/gif"
////            }
////            return "image/jpeg"
        return null
    }

    /**
     * 获取video的mine_type,暂时只支持mp4,3gp
     */
    @JvmStatic
    fun getVideoMimeType(path: String): String {
        val lowerPath = path.toLowerCase(Locale.ROOT)
        if (lowerPath.endsWith("mp4") || lowerPath.endsWith("mpeg4")) {
            return "video/mp4"
        } else if (lowerPath.endsWith("3gp")) {
            return "video/3gp"
        }
        return "video/mp4"
    }
}