package com.elson.storage.operator.media

import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import com.elson.storage.helper.FileHelper
import com.elson.storage.helper.MediaHelper
import com.elson.storage.request.BaseRequest

/**
 * Author : Elson
 * Date   : 2020/10/21
 * Desc   :
 */
class VideoMediaOperator: IMediaOperator {

    override fun insertMedia(request: BaseRequest) {
        MediaHelper.insertVideo(request.context, request.getOutputFile()!!.path, 0, 0, request.mDuration)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun insertMedia_Q(request: BaseRequest): Uri? {
        val relativePath: String = FileHelper.appendPath(Environment.DIRECTORY_PICTURES, request.mRelativePath)
        return MediaHelper.insertVideoQ(request.context, request.getOutputFile()!!.name, request.mDuration, relativePath)
    }
}