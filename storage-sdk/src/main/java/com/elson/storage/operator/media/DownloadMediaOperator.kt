package com.elson.storage.operator.media

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import com.elson.storage.helper.MediaHelper
import com.elson.storage.request.BaseRequest

/**
 * Author : Elson
 * Date   : 2020/10/21
 * Desc   :
 */
class DownloadMediaOperator: IMediaOperator {

    override fun insertMedia(request: BaseRequest) {
        // do nothing
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun insertMedia_Q(request: BaseRequest): Uri? {
        return MediaHelper.insertDownloadQ(request.context, request.getOutputFile()!!.path)
    }
}