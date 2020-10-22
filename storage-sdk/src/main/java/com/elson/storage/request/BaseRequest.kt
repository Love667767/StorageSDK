package com.elson.storage.request

import android.content.Context
import com.elson.storage.helper.PathHelper
import com.elson.storage.operator.file.DefaultFileOperator
import com.elson.storage.operator.file.IFileOperator
import com.elson.storage.operator.media.*
import com.elson.storage.operator.permission.IPermissionOperator
import java.io.File

/**
 * Author : Elson
 * Date   : 2020/10/21
 * Desc   :
 */
abstract class BaseRequest(var context: Context) {

    protected var mMediaOperator: IMediaOperator? = null
    protected var mPermissionOperator: IPermissionOperator? = null

    internal var mInputFile: File? = null
    internal var mOutputFilePath: File? = null
    internal var mOutputFileName: String? = null
    internal var mRelativePath : String? = null

    protected var mSynSystemMedia: Boolean = false

    // ------- Media props ---------
    internal var mDuration: Long = 0L
    internal var mWidth: Int = 0
    internal var mHeight: Int = 0

    // --------------------- Path ----------------------

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

    fun setInputFile(file: File?): BaseRequest {
        mInputFile = file
        return this
    }

    fun setCustomFilePath(outputFilePath: String? = null): BaseRequest {
        mOutputFilePath = PathHelper.getFileDir(context, outputFilePath)
        return this
    }

    fun setOutputFileName(fileName: String?): BaseRequest {
        mOutputFileName = fileName
        return this
    }

    fun getOutputFile(): File? {
        if (mOutputFilePath == null || mOutputFileName == null) {
            return null
        }
        return mOutputFilePath!!.resolve(mOutputFileName!!)
    }

    // ----------------------- Operator -----------------------

    fun setPermissionOperator(operator: IPermissionOperator): BaseRequest {
        mPermissionOperator = operator
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

    fun asDownload(): BaseRequest {
        mMediaOperator = DownloadMediaOperator()
        return this
    }

    // -------------------- Media props --------------------------

    fun setMediaDuration(duration: Long = 0): BaseRequest {
        this.mDuration = duration
        return this
    }

    fun setMediaSize(width: Int = 0,height: Int = 0): BaseRequest {
        this.mWidth = width
        this.mHeight = height
        return this
    }

    // -------------------- other --------------------------
    /**
     * 同步到公共相册
     */
    fun synSystemMedia(): BaseRequest {
        this.mSynSystemMedia = true
        return this
    }

    fun start() {
        start(DefaultFileOperator())
    }

    abstract fun start(fileOperator: IFileOperator)

    abstract fun setExternalPublishDir(filePath: String? = null): BaseRequest

}