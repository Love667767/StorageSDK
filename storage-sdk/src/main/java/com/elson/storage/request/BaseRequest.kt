package com.elson.storage.request

import android.content.Context
import com.elson.storage.StorageFacade
import com.elson.storage.config.MediaConstant
import com.elson.storage.helper.PathHelper
import com.elson.storage.operator.file.IFileOperator
import com.elson.storage.interceptor.ChainInterceptor
import com.elson.storage.interceptor.Interceptor
import com.elson.storage.operator.file.DefaultFileOperator
import com.elson.storage.operator.media.*
import com.elson.storage.operator.media.factory.MediaOperatorFactory
import com.elson.storage.operator.permission.DefaultPermissionOperator
import com.elson.storage.operator.permission.IPermissionOperator
import com.elson.storage.request.callback.Callback
import com.elson.storage.request.callback.DefaultCallback
import java.io.File

/**
 * Author : Elson
 * Date   : 2020/10/21
 * Desc   :
 */
abstract class BaseRequest<T>(var context: Context) {

    protected var mMediaOperator: IMediaOperator? = null
    protected var mFileOperator: IFileOperator<*>? = null
    protected var mPermissionOperator: IPermissionOperator? = null
    protected val mChainInterceptor by lazy { ChainInterceptor() }

    internal var mInputModel: T? = null
    internal var mOutputFilePath: File? = null
    internal var mOutputFileName: String? = null
    internal var mRelativePath: String? = null

    internal var mTag: String = ""
    internal var mCallback: Callback? = null
    protected var isSynSystemMedia: Boolean = false
    internal var mMediaType = MediaConstant.MEDIA_TYPE_MISSING

    // ------- Media props ---------
    internal var mDuration: Long = 0L
    internal var mWidth: Int = 0
    internal var mHeight: Int = 0


    // --------------------- File Path ----------------------

    fun setFileDir(filePath: String? = null): BaseRequest<T> {
        mOutputFilePath = PathHelper.getFileDir(context, filePath)
        return this
    }

    fun setCacheDir(filePath: String? = null): BaseRequest<T> {
        mOutputFilePath = PathHelper.getCacheDir(context, filePath)
        return this
    }

    fun setExtAppFileDir(filePath: String? = null): BaseRequest<T> {
        mOutputFilePath = PathHelper.getExternalFileDir(context, filePath)
        return this
    }

    fun setExtAppCacheDir(filePath: String? = null): BaseRequest<T> {
        mOutputFilePath = PathHelper.getExternalCacheDir(context, filePath)
        return this
    }

    /**
     * 设置外部公共目录的文件路径
     * @param filePath：老版本的文件存储路径
     * @param filePath_Q：Android Q对应的文件存储路径
     */
    abstract fun setExtPublishDir(filePath: String?, filePath_Q: String?): BaseRequest<T>

    fun setOutputFileName(fileName: String?): BaseRequest<T> {
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

    fun addInterceptor(interceptor: Interceptor): BaseRequest<T> {
        mChainInterceptor.addInterceptor(interceptor)
        return this
    }

    fun addInterceptors(vararg interceptors: Interceptor): BaseRequest<T> {
        mChainInterceptor.addInterceptors(*interceptors)
        return this
    }

    fun addFileOperator(operator: IFileOperator<T>): BaseRequest<T> {
        mFileOperator = operator
        return this
    }

    fun getFileOperator(): IFileOperator<*> {
        if (mFileOperator == null) {
            mFileOperator = DefaultFileOperator()
        }
        return mFileOperator!!
    }

    fun addPermissionOperator(operator: IPermissionOperator): BaseRequest<T> {
        mPermissionOperator = operator
        return this
    }

    protected fun getPermissionOperator(): IPermissionOperator {
        if (mPermissionOperator == null) {
            mPermissionOperator = DefaultPermissionOperator(context)
        }
        return mPermissionOperator!!
    }

    fun addMediaOperator(operator: IMediaOperator): BaseRequest<T> {
        mMediaOperator = operator
        return this
    }

    protected fun getMediaOperator(): IMediaOperator {
        if (mMediaOperator == null) {
            mMediaOperator = MultiTypeMediaOperator()
        }
        return mMediaOperator!!
    }

    fun asImage(): BaseRequest<T> {
        mMediaOperator = MediaOperatorFactory.createOperator(MediaConstant.MEDIA_TYPE_IMAGE)
        return this
    }

    fun asVideo(): BaseRequest<T> {
        mMediaOperator = MediaOperatorFactory.createOperator(MediaConstant.MEDIA_TYPE_VIDEO)
        return this
    }

    fun asAudio(): BaseRequest<T> {
        mMediaOperator = MediaOperatorFactory.createOperator(MediaConstant.MEDIA_TYPE_AUDIO)
        return this
    }

    fun asDownload(): BaseRequest<T> {
        mMediaOperator = MediaOperatorFactory.createOperator(MediaConstant.MEDIA_TYPE_DOWNLOAD)
        return this
    }

    // -------------------- Media props --------------------------

    fun setMediaDuration(duration: Long = 0): BaseRequest<T> {
        this.mDuration = duration
        return this
    }

    fun setMediaSize(width: Int = 0, height: Int = 0): BaseRequest<T> {
        this.mWidth = width
        this.mHeight = height
        return this
    }

    // -------------------- other --------------------------
    /**
     * 同步到公共相册
     */
    fun synSystemMedia(): BaseRequest<T> {
        this.isSynSystemMedia = true
        return this
    }

    fun start() {
        start(DefaultCallback())
    }

    abstract fun start(callback: Callback)

    abstract fun onSuccess()

}