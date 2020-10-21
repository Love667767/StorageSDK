package com.elson.storage.operator.media

import android.net.Uri
import com.elson.storage.request.BaseRequest

/**
 * Author : Elson
 * Date   : 2020/10/21
 * Desc   :
 */
interface IMediaOperator {

    fun insertMedia(request: BaseRequest): String
    fun insertMedia_Q(request: BaseRequest): Uri?
}