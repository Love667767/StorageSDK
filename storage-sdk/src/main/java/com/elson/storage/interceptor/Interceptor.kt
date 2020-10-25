package com.elson.storage.interceptor

import com.elson.storage.request.BaseRequest

/**
 * Author : liuqi
 * Date   : 2020/10/24
 * Desc   :
 */
abstract class Interceptor {

    private var mInterceptor: Interceptor? = null

    // 关联下一个拦截器
    fun setNextInterceptor(interceptor: Interceptor?) {
        mInterceptor = interceptor
    }

    abstract fun interceptor(request: BaseRequest<*>)

    interface Chain {
        fun process(request: BaseRequest<*>)
    }

}