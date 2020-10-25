package com.elson.storage.operator.media

import android.net.Uri
import com.elson.storage.request.BaseRequest

/**
 * Author : liuqi
 * Date   : 2020/10/24
 * Desc   :
 */
class MediaEmptyOperator: IMediaOperator {

    override fun insertMedia(request: BaseRequest<*>) {
        // do nothing
    }

    override fun insertMedia_Q(request: BaseRequest<*>): Uri? {
        // do nothing
        return null
    }
}