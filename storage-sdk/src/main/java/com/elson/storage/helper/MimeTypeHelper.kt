package com.elson.storage.helper

import com.elson.storage.config.MediaConstant
import java.io.File

/**
 * Author : Elson
 * Date   : 2020/10/21
 * Desc   : 媒体类型
 *
 */
object MimeTypeHelper {

    @JvmStatic
    fun getMediaType(file: File?): Int {
        if (file == null) {
            return MediaConstant.MEDIA_TYPE_MISSING
        }
        return getMediaType(file.name)
    }

    @JvmStatic
    fun getMediaType(file: String): Int {
        val mediaSuffix = FileHelper.getFileSuffix(file)
        if (isSuffixOfImage(mediaSuffix)) {
            return MediaConstant.MEDIA_TYPE_IMAGE
        } else if (isSuffixOfVideo(mediaSuffix)) {
            return MediaConstant.MEDIA_TYPE_VIDEO
        } else if (isSuffixOfAudio(mediaSuffix)) {
            return MediaConstant.MEDIA_TYPE_AUDIO
        } else {
            return MediaConstant.MEDIA_TYPE_DOWNLOAD
        }
    }

    @JvmStatic
    fun isSuffixOfImage(suffix: String): Boolean {
        return when (suffix) {
            MediaConstant.IMAGE_BMP,
            MediaConstant.IMAGE_JPEG,
            MediaConstant.IMAGE_JPG,
            MediaConstant.IMAGE_PNG,
            MediaConstant.IMAGE_GIF,
            MediaConstant.IMAGE_WEBP -> true
            else -> false
        }
    }

    @JvmStatic
    fun isSuffixOfVideo(suffix: String): Boolean {
        return when (suffix) {
            MediaConstant.VIDEO_3GP,
            MediaConstant.VIDEO_MP4,
            MediaConstant.VIDEO_MPEG4 -> true
            else -> false
        }
    }

    @JvmStatic
    fun isSuffixOfAudio(suffix: String): Boolean {
        return when (suffix) {
            MediaConstant.AUDIO_MKV,
            MediaConstant.AUDIO_MP3,
            MediaConstant.AUDIO_WAV -> true
            else -> false
        }
    }

    /**
     * 获取照片的mine_type
     */
    @JvmStatic
    fun getImageMimeType(path: String): String {
        return when (FileHelper.getFileSuffix(path)) {
            MediaConstant.IMAGE_GIF -> MediaConstant.MIME_TYPE_IMAGE_GIF
            MediaConstant.IMAGE_PNG -> MediaConstant.MIME_TYPE_IMAGE_PNG
            MediaConstant.IMAGE_WEBP -> MediaConstant.MIME_TYPE_IMAGE_WEBP
            else -> MediaConstant.MIME_TYPE_IMAGE_JPEG
        }
    }


    @JvmStatic
    fun getAudioMimeType(path: String): String? {
        return when (FileHelper.getFileSuffix(path)) {
            MediaConstant.AUDIO_WAV,
            MediaConstant.AUDIO_MP3,
            MediaConstant.AUDIO_MKV -> MediaConstant.MIME_TYPE_AUDIO_DEFAULT
            else -> MediaConstant.MIME_TYPE_AUDIO_DEFAULT
        }
    }

    /**
     * 获取video的mine_type,暂时只支持mp4,3gp
     */
    @JvmStatic
    fun getVideoMimeType(path: String): String {
        val lowerPath = FileHelper.getFileSuffix(path)
        return if (lowerPath == MediaConstant.VIDEO_3GP) {
            MediaConstant.MIME_TYPE_VIDEO_3GP
        } else {
            MediaConstant.MIME_TYPE_VIDEO_MP4
        }
    }


}