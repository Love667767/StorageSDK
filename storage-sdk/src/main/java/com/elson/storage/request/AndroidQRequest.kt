package com.elson.storage.request

import android.content.Context
import com.elson.storage.helper.FileHelper
import com.elson.storage.helper.PathHelper
import com.elson.storage.operator.file.IFileOperator

/**
 * Author : Elson
 * Date   : 2020/10/21
 * Desc   : 沙盒模式下的文件保存
 */
open class AndroidQRequest(application: Context) : BaseRequest(application) {

    override fun setExternalPublishDir(filePath: String?): BaseRequest {
        mRelativePath = filePath
        return this
    }


    override fun start(fileOperator: IFileOperator) {
        if (mRelativePath.isNullOrBlank() && (mOutputFilePath == null || mOutputFileName.isNullOrBlank())) {
            return
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