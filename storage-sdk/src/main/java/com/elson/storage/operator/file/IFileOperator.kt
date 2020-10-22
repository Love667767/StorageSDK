package com.elson.storage.operator.file

import android.net.Uri
import com.elson.storage.request.BaseRequest
import java.io.File

/**
 * Author : Elson
 * Date   : 2020/10/21
 * Desc   :
 */
interface IFileOperator {

    fun operateFile(request: BaseRequest, outputFile: File)

    fun operateFile_Q(request: BaseRequest, outUri: Uri)
}