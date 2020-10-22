package com.elson.storage.operator.file

import android.net.Uri
import com.elson.storage.StorageFacade
import com.elson.storage.helper.FileHelper
import com.elson.storage.request.BaseRequest
import java.io.File

/**
 * Author : Elson
 * Date   : 2020/10/21
 * Desc   :
 */
class DefaultFileOperator(): IFileOperator {

    override fun operateFile(request: BaseRequest, outputFile: File) {
        StorageFacade.getExecutorService().execute {
            FileHelper.saveFile2ExternalDir(request.mInputFile, outputFile)
            StorageFacade.getMainExecutor().execute {
                StorageFacade.showToast(request.context, "图片保存成功: ${request.getOutputFile()!!.absolutePath}")
            }
        }
    }

    override fun operateFile_Q(request: BaseRequest, outUri: Uri) {
        StorageFacade.getExecutorService().execute {
            FileHelper.saveFile2ExternalDir(request.context, request.mInputFile, outUri)
            StorageFacade.getMainExecutor().execute {
                StorageFacade.showToast(request.context, "图片保存成功")
            }
        }
    }
}