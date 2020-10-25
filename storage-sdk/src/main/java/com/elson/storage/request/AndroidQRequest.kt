package com.elson.storage.request

import android.content.Context
import com.elson.storage.config.MediaConstant
import com.elson.storage.helper.FileHelper
import com.elson.storage.helper.MimeTypeHelper
import com.elson.storage.request.callback.Callback

/**
 * Author : Elson
 * Date   : 2020/10/21
 * Desc   : 沙盒模式下的文件保存
 */
open class AndroidQRequest<T>(application: Context) : BaseRequest<T>(application) {

    override fun setExtPublishDir(filePath: String?, filePath_Q: String?): BaseRequest<T> {
        mRelativePath = filePath_Q
        return this
    }

    override fun start(callback: Callback) {
        mCallback = callback

        if (mInputModel == null) {
            callback.onFailed(context, MediaConstant.MEDIA_TYPE_MISSING)
            return
        }
        if (mRelativePath.isNullOrBlank() && (mOutputFilePath == null || mOutputFileName.isNullOrBlank())) {
            callback.onFailed(context, MediaConstant.MEDIA_TYPE_MISSING)
            return
        }

        if (mMediaType == MediaConstant.MEDIA_TYPE_MISSING) {
            mMediaType = MimeTypeHelper.getMediaType(mOutputFileName!!)
        }

        mChainInterceptor.process(this)
    }

    override fun onSuccess() {
        // 1.非公共目录
        val filePath = mOutputFilePath
        if (filePath != null) {
            // 1.1若文件夹不存在，先创建。
            FileHelper.mkdirs(filePath)

            // 1.2检测该文件是否存在。
            val outputFile = filePath.resolve(mOutputFileName!!)
            FileHelper.deleteFile(outputFile)

            // 1.3保存文件
            getFileOperator().operateFile(this, outputFile)
            return
        }

        // 2.公共目录
        getMediaOperator().insertMedia_Q(this)?.apply {
            getFileOperator().operateFile_Q(this@AndroidQRequest, this)
        }
    }
}