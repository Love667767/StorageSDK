package com.elson.storage.operator.media

import android.net.Uri
import com.elson.storage.helper.MimeTypeHelper
import com.elson.storage.operator.media.factory.MediaOperatorFactory
import com.elson.storage.request.BaseRequest

/**
 * Author : liuqi
 * Date   : 2020/10/24
 * Desc   :
 */
class MultiTypeMediaOperator: IMediaOperator {

    override fun insertMedia(request: BaseRequest<*>) {
        MediaOperatorFactory.createOperator(request.mMediaType).insertMedia(request)
    }

    override fun insertMedia_Q(request: BaseRequest<*>): Uri? {
        return MediaOperatorFactory.createOperator(request.mMediaType).insertMedia_Q(request)
    }

}