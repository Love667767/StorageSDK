package com.elson.storage.operator.media.factory

import com.elson.storage.config.MediaConstant
import com.elson.storage.operator.media.*

/**
 * Author : liuqi
 * Date   : 2020/10/24
 * Desc   :
 */
object MediaOperatorFactory {

    @JvmStatic
    fun createOperator(mediaType: Int): IMediaOperator {
        return when(mediaType) {
            MediaConstant.MEDIA_TYPE_IMAGE -> MediaImageOperator()
            MediaConstant.MEDIA_TYPE_VIDEO -> MediaVideoOperator()
            MediaConstant.MEDIA_TYPE_AUDIO -> MediaAudioOperator()
            MediaConstant.MEDIA_TYPE_DOWNLOAD -> MediaDownloadOperator()
            else -> MediaEmptyOperator()
        }
    }
}