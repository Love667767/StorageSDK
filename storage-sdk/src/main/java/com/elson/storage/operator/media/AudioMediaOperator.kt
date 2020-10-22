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
class AudioMediaOperator: IMediaOperator {

    override fun insertMedia(request: BaseRequest) {
        // do nothing
    }

    override fun insertMedia_Q(request: BaseRequest): Uri? {
        val relativePath: String = FileHelper.appendPath(Environment.DIRECTORY_MOVIES, request.mRelativePath)
       return MediaHelper.insertAudioQ(request.context, request.getOutputFile()!!.name, relativePath)
    }
}