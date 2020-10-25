package com.elson.storage.operator.media

import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import com.elson.storage.helper.FileHelper
import com.elson.storage.helper.MediaHelper
import com.elson.storage.request.BaseRequest

/**
 * Author : Elson
 * Date   : 2020/10/21
 * Desc   :
 */
class MediaDownloadOperator: IMediaOperator {

    override fun insertMedia(request: BaseRequest<*>) {
        // do nothing
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun insertMedia_Q(request: BaseRequest<*>): Uri? {
        MediaHelper.deleteFile_Q(request.context, request.mOutputFileName, MediaStore.Downloads.EXTERNAL_CONTENT_URI)

        val relativePath: String = FileHelper.appendPath(Environment.DIRECTORY_DOWNLOADS, request.mRelativePath)
        request.mTag = relativePath
        return MediaHelper.insertDownloadQ(request.context, request.mOutputFileName!!, relativePath)
    }
}