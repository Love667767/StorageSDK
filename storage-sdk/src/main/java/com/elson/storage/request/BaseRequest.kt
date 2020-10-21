package com.elson.storage.request

import android.content.Context
import com.elson.storage.helper.PathHelper
import com.elson.storage.operator.file.DefaultFileOperator
import com.elson.storage.operator.file.IFileOperator
import com.elson.storage.operator.media.AudioMediaOperator
import com.elson.storage.operator.media.IMediaOperator
import com.elson.storage.operator.media.ImageMediaOperator
import com.elson.storage.operator.media.VideoMediaOperator
import java.io.File

/**
 * Author : Elson
 * Date   : 2020/10/21
 * Desc   :
 */
abstract class BaseRequest(protected var context: Context) {

    protected var mMediaOperator: IMediaOperator? = null

    protected var mInputFile: File? = null
    protected var mOutputFilePath: File? = null
    protected var mOutputFileName: String? = null

    fun setFileDir(filePath: String? = null): BaseRequest {
        mOutputFilePath = PathHelper.getFileDir(context, filePath)
        return this
    }

    fun setCacheDir(filePath: String? = null): BaseRequest {
        mOutputFilePath = PathHelper.getCacheDir(context, filePath)
        return this
    }

    fun setExternalFileDir(filePath: String? = null): BaseRequest {
        mOutputFilePath = PathHelper.getExternalFileDir(context, filePath)
        return this
    }

    fun setExternalCacheDir(filePath: String? = null): BaseRequest {
        mOutputFilePath = PathHelper.getExternalCacheDir(context, filePath)
        return this
    }

    fun setCustomFilePath(filePath: String? = null): BaseRequest {
        mOutputFilePath = PathHelper.getFileDir(context, filePath)
        return this
    }

    fun setMediaOperator(operator: IMediaOperator): BaseRequest {
        mMediaOperator = operator
        return this
    }

    fun asImage(): BaseRequest {
        mMediaOperator = ImageMediaOperator()
        return this
    }

    fun asVideo(): BaseRequest {
        mMediaOperator = VideoMediaOperator()
        return this
    }

    fun asAudio(): BaseRequest {
        mMediaOperator = AudioMediaOperator()
        return this
    }

    fun setInputFileName(file: File?) : BaseRequest{
        mInputFile = file
        return this
    }

    fun setOutputFileName(fileName: String?) : BaseRequest{
        mOutputFileName = fileName
        return this
    }

    fun getOutputFile(): File? {
        if (mOutputFilePath == null || mOutputFileName == null) {
            return null
        }
        return mOutputFilePath!!.resolve(mOutputFileName!!)
    }

    fun getInputFile(): File? {
        return mInputFile
    }

    fun start() {
        start(DefaultFileOperator())
    }

    abstract fun start(fileOperator: IFileOperator)

}