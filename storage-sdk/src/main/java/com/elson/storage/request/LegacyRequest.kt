package com.elson.storage.request

import android.content.Context
import com.elson.storage.config.MediaConstant
import com.elson.storage.helper.FileHelper
import com.elson.storage.helper.MimeTypeHelper
import com.elson.storage.helper.PathHelper
import com.elson.storage.operator.file.DefaultFileOperator
import com.elson.storage.operator.file.IFileOperator
import com.elson.storage.operator.media.MultiTypeMediaOperator
import com.elson.storage.request.callback.Callback

/**
 * Author : Elson
 * Date   : 2020/10/21
 * Desc   : 非沙盒模式下的文件读写
 */
class LegacyRequest<T>(application: Context) : BaseRequest<T>(application) {

    override fun setExtPublishDir(filePath: String?): BaseRequest<T> {
        isPublishDir = true
        mOutputFilePath = PathHelper.getLegacyExternalRootDir(filePath)
        return this
    }

    override fun start(callback: Callback) {
        mCallback = callback
        if (mInputModel == null || mOutputFilePath == null || mOutputFileName.isNullOrBlank()) {
            callback.onFailed(context, MediaConstant.MEDIA_TYPE_MISSING)
            return
        }
        mMediaType = MimeTypeHelper.getMediaType(mOutputFileName!!)
        if (isAutoMatchFolder && isPublishDir) {
            val path = getMediaDirWithMediaType(mMediaType)
            mOutputFilePath = mOutputFilePath!!.resolve(path)
        }

        mChainInterceptor.process(this)
    }

    override fun onSuccess() {
        if (mMediaOperator == null) {
            mMediaType = MimeTypeHelper.getMediaType(mOutputFileName!!)
            mMediaOperator = MultiTypeMediaOperator()
        }

        var fileOperator = mFileOperator
        if (fileOperator == null) {
            fileOperator = DefaultFileOperator()
        }

        if (mPermissionOperator == null) {
            executeStore(fileOperator)
            return
        }
        mPermissionOperator!!.applyPermissions {
            executeStore(fileOperator)
        }
    }

    private fun executeStore(fileOperator: IFileOperator<*>) {
        val filePath = mOutputFilePath
        if (filePath != null) {
            // 1.若文件夹不存在，先创建。
            FileHelper.mkdirs(filePath)

            // 2.检测该文件是否存在。
            val outputFile = filePath.resolve(mOutputFileName!!)
            FileHelper.deleteFile(outputFile)

            fileOperator.operateFile(this, outputFile)
            if (isSynSystemMedia) {
                mMediaOperator?.insertMedia(this)
            }
        }
    }
}