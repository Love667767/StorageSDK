package com.elson.storage.operator.media

import android.net.Uri
import android.os.Environment
import com.elson.storage.helper.FileHelper
import com.elson.storage.helper.MediaHelper
import com.elson.storage.request.BaseRequest

/**
 * Author : Elson
 * Date   : 2020/10/21
 * Desc   :
 */
class ImageMediaOperator: IMediaOperator {

    override fun insertMedia(request: BaseRequest) {
//        MediaHelper.insertImage(request.context, request.getOutputFile()!!)
        MediaHelper.insertImage(request.context, request.getOutputFile()!!.absolutePath, 0, 0)
    }

    override fun insertMedia_Q(request: BaseRequest): Uri? {
        val relativePath: String = FileHelper.appendPath(Environment.DIRECTORY_PICTURES, request.mRelativePath)
        return MediaHelper.insertImageQ(request.context, FileHelper.getImageFileName(request.mOutputFileName), relativePath)
    }
}