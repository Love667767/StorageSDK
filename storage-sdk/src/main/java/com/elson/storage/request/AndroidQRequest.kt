package com.elson.storage.request

import android.content.Context
import com.elson.storage.operator.file.IFileOperator

/**
 * Author : Elson
 * Date   : 2020/10/21
 * Desc   :
 */
class AndroidQRequest(application: Context): BaseRequest(application) {



    override fun start(fileOperator: IFileOperator) {
        if (mInputFile == null || !mInputFile!!.exists()) {
            return
        }

        val filePath = mOutputFilePath

        // 非公共目录
        if (filePath != null) {

            fileOperator.operateFile(this)
            return
        }

        if (mMediaOperator == null) {
            return
        }
        mMediaOperator?.insertMedia_Q(this)
        fileOperator.operateFile_Q(this)
    }
}