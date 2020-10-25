package com.elson.storage.operator.file

import android.graphics.Bitmap
import android.net.Uri
import com.elson.storage.StorageFacade
import com.elson.storage.helper.FileHelper
import com.elson.storage.request.AndroidQRequest
import com.elson.storage.request.BaseRequest
import okio.Source
import java.io.File

/**
 * Author : Elson
 * Date   : 2020/10/21
 * Desc   :
 */
class DefaultFileOperator: IFileOperator<Any> {

    override fun operateFile(request: BaseRequest<*>, outputFile: File) {
        StorageFacade.getExecutorService().execute {
            val success = when(request.mInputModel!!::class.java) {
                File::class.java -> FileHelper.saveFile2ExternalDir(request.mInputModel as File, outputFile)
                Bitmap::class.java -> FileHelper.saveBitmap2ExternalDir(request.mInputModel as Bitmap, outputFile)
                Source::class.java -> FileHelper.saveSource2ExternalDir(request.mInputModel as Source, outputFile)
                else -> false
            }
            if (success) {
                request.mCallback?.onSuccess(request.context, outputFile.absolutePath, request.mMediaType)
            } else {
                request.mCallback?.onFailed(request.context, request.mMediaType)
            }
        }
    }

    override fun operateFile_Q(request: BaseRequest<*>, outUri: Uri) {
        StorageFacade.getExecutorService().execute {
            val success = when(request.mInputModel!!::class.java) {
                File::class.java -> FileHelper.saveFile2ExternalDir(request.context, request.mInputModel as File, outUri)
                Bitmap::class.java -> FileHelper.saveBitmap2ExternalDir(request.context, request.mInputModel as Bitmap, outUri)
                Source::class.java -> FileHelper.saveSource2ExternalDir(request.context, request.mInputModel as Source, outUri)
                else -> false
            }
            if (success) {
                request.mCallback?.onSuccess(request.context, request.mTag, request.mMediaType)
            } else {
                request.mCallback?.onFailed(request.context, request.mMediaType)
            }
        }
    }
}