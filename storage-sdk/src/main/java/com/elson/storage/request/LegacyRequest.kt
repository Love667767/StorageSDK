package com.elson.storage.request

import android.content.Context
import com.elson.storage.helper.FileHelper
import com.elson.storage.helper.PathHelper
import com.elson.storage.operator.file.IFileOperator

/**
 * Author : Elson
 * Date   : 2020/10/21
 * Desc   : 非沙盒模式下的文件读写
 */
class LegacyRequest(application: Context) : BaseRequest(application) {


    override fun setExternalPublishDir(filePath: String?): BaseRequest {
        mOutputFilePath = PathHelper.getLegacyExternalRootDir(context, filePath)
        return this
    }

    override fun start(fileOperator: IFileOperator) {
        if (mInputFile == null || !mInputFile!!.exists()
                || mOutputFilePath == null || mOutputFileName.isNullOrBlank()) {
            return
        }

        if (mPermissionOperator == null) {
            executeStore(fileOperator)
            return
        }
        mPermissionOperator!!.applyPermissions {
            executeStore(fileOperator)
        }
    }

    private fun executeStore(fileOperator: IFileOperator) {
        val filePath = mOutputFilePath
        if (filePath != null) {
            // 1.若文件夹不存在，先创建。
            FileHelper.mkdirs(filePath)

            // 2.检测该文件是否存在。
            val outputFile = filePath.resolve(mOutputFileName!!)
            FileHelper.deleteFile(outputFile)

            fileOperator.operateFile(this, outputFile)
            if (mSynSystemMedia) {
                mMediaOperator?.insertMedia(this)
            }
        }
    }
}