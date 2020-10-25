package com.elson.storage.request

import android.content.Context
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import com.elson.storage.config.MediaConstant
import com.elson.storage.helper.FileHelper
import com.elson.storage.helper.MimeTypeHelper
import com.elson.storage.operator.file.DefaultFileOperator
import com.elson.storage.operator.media.MultiTypeMediaOperator
import com.elson.storage.request.callback.Callback

/**
 * Author : Elson
 * Date   : 2020/10/21
 * Desc   : 沙盒模式下的文件保存
 */
open class AndroidQRequest<T>(application: Context) : BaseRequest<T>(application) {

    override fun setExtPublishDir(filePath: String?): BaseRequest<T> {
        isPublishDir = true
        mRelativePath = filePath
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

        mMediaType = MimeTypeHelper.getMediaType(mOutputFileName!!)
        if (isAutoMatchFolder && isPublishDir) {
            mRelativePath = getMediaDirWithMediaType(mMediaType)
        }

        mChainInterceptor.process(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onSuccess() {
        if (mMediaOperator == null) {
            mMediaOperator = MultiTypeMediaOperator()
        }

        var fileOperator = mFileOperator
        if (fileOperator == null) {
            fileOperator = DefaultFileOperator()
        }

        // 非公共目录
        val filePath = mOutputFilePath
        if (filePath != null) {
            // 1.若文件夹不存在，先创建。
            FileHelper.mkdirs(filePath)

            // 2.检测该文件是否存在。
            val outputFile = filePath.resolve(mOutputFileName!!)
            FileHelper.deleteFile(outputFile)

            fileOperator.operateFile(this, outputFile)
            return
        }

        // 公共目录
        if (mPermissionOperator == null) {
            mMediaOperator?.insertMedia_Q(this)?.apply {
                fileOperator.operateFile_Q(this@AndroidQRequest, this)
            }
            return
        }

        // 权限判断
        mPermissionOperator!!.applyPermissions {
            mMediaOperator?.insertMedia_Q(this)?.apply {
                fileOperator.operateFile_Q(this@AndroidQRequest, this)
            }
        }
    }
}