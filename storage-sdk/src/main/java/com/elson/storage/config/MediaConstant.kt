package com.elson.storage.config

/**
 * Author : liuqi
 * Date   : 2020/10/24
 * Desc   :
 */
class MediaConstant {

    companion object {
        const val MEDIA_TYPE_MISSING = 0
        const val MEDIA_TYPE_IMAGE = 1
        const val MEDIA_TYPE_VIDEO = 2
        const val MEDIA_TYPE_AUDIO = 3
        const val MEDIA_TYPE_DOWNLOAD = 4

        const val IMAGE_PNG = ".png"
        const val IMAGE_JPEG = ".jpeg"
        const val IMAGE_JPG = ".jpg"
        const val IMAGE_WEBP = ".webp"
        const val IMAGE_BMP = ".bmp"
        const val IMAGE_GIF = ".gif"

        const val VIDEO_3GP = ".3gp"
        const val VIDEO_MP4 = ".mp4"
        const val VIDEO_MPEG4 = ".mpeg4"

        const val AUDIO_MP3 = ".mp3"
        const val AUDIO_WAV = ".wav"
        const val AUDIO_MKV = ".mkv"


        const val MIME_TYPE_IMAGE_PNG = "image/png"
        const val MIME_TYPE_IMAGE_JPEG = "image/jpeg"
        const val MIME_TYPE_IMAGE_GIF = "image/gif"
        const val MIME_TYPE_IMAGE_WEBP = "image/webp"

        const val MIME_TYPE_VIDEO_3GP = "video/3gp"
        const val MIME_TYPE_VIDEO_MP4 = "video/mp4"

        const val MIME_TYPE_AUDIO_DEFAULT = "audio/*"
    }

}