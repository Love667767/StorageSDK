package com.elson.storage

import android.content.Context
import com.elson.storage.helper.MediaHelper
import com.elson.storage.request.AndroidQRequest
import com.elson.storage.request.BaseRequest
import com.elson.storage.request.LegacyRequest

/**
 * Author : Elson
 * Date   : 2020/10/21
 * Desc   : 门面类
 */
object StorageFacade {

    fun init() {

    }

    @JvmStatic
    fun with(context: Context): BaseRequest {
        return if (MediaHelper.checkAndroid_Q()) {
            AndroidQRequest(context.applicationContext)
        } else {
            LegacyRequest(context.applicationContext)
        }
    }


}