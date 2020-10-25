package com.elson.storage.request

import android.content.Context
import com.elson.storage.config.MediaConstant
import com.elson.storage.helper.FileHelper
import com.elson.storage.helper.MimeTypeHelper
import com.elson.storage.helper.PathHelper
import com.elson.storage.request.callback.Callback

/**
 * Author : Elson
 * Date   : 2020/10/21
 * Desc   : 非沙盒模式下的文件读写
 */
class LegacyRequest<T>(application: Context) : BaseRequest<T>(application) {

    override fun setExtPublishDir(filePath: String?, filePath_Q: String?): BaseRequest<T> {
        mOutputFilePath = PathHelper.getLegacyExternalRootDir(filePath)
        return this
    }

    override fun start(callback: Callback) {
        mCallback = callback
        if (mInputModel == null || mOutputFilePath == null || mOutputFileName.isNullOrBlank()) {
            callback.onFailed(context, MediaConstant.MEDIA_TYPE_MISSING)
            return
        }
        if (mMediaType == MediaConstant.MEDIA_TYPE_MISSING) {
            mMediaType = MimeTypeHelper.getMediaType(mOutputFileName!!)
        }

        mChainInterceptor.process(this)
    }

    override fun onSuccess() {
        getPermissionOperator().applyPermissions {
            val filePath = mOutputFilePath
            if (filePath != null) {
                // 1.若文件夹不存在，先创建。
                FileHelper.mkdirs(filePath)

                // 2.检测该文件是否存在。
                val outputFile = filePath.resolve(mOutputFileName!!)
                FileHelper.deleteFile(outputFile)

                // 3.保存文件
                getFileOperator().operateFile(this, outputFile)

                // 4.同步到公共媒体库
                if (isSynSystemMedia) {
                    getMediaOperator().insertMedia(this)
                }
            }
        }
    }

}