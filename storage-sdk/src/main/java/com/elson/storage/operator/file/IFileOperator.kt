package com.elson.storage.operator.file

import com.elson.storage.request.BaseRequest

/**
 * Author : Elson
 * Date   : 2020/10/21
 * Desc   :
 */
interface IFileOperator {

    fun operateFile(request: BaseRequest)

    fun operateFile_Q(request: BaseRequest)
}