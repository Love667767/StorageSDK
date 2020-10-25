package com.elson.storage.request.callback

import android.content.Context
import com.elson.storage.StorageFacade
import com.elson.storage.config.MediaConstant

/**
 * Author : liuqi
 * Date   : 2020/10/24
 * Desc   :
 */
interface Callback {

    fun onSuccess(context: Context, path: String, mediaType: Int)

    fun onFailed(context: Context, mediaType: Int)
}

class DefaultCallback : Callback {

    override fun onSuccess(context: Context, path: String, mediaType: Int) {
        StorageFacade.showToast(context, "${getMediaTypeName(mediaType)}保存到: ${path}")
    }

    override fun onFailed(context: Context, mediaType: Int) {
        StorageFacade.showToast(context, "${getMediaTypeName(mediaType)}保存失败")
    }

    private fun getMediaTypeName(mediaType: Int): String {
        return when (mediaType) {
            MediaConstant.MEDIA_TYPE_IMAGE -> "图片"
            MediaConstant.MEDIA_TYPE_VIDEO -> "视频"
            MediaConstant.MEDIA_TYPE_AUDIO -> "音频"
            MediaConstant.MEDIA_TYPE_DOWNLOAD -> "文件"
            else -> "文件"
        }
    }

}