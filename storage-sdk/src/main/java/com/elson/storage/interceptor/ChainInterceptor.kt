package com.elson.storage.interceptor

import com.elson.storage.request.BaseRequest

/**
 * Author : liuqi
 * Date   : 2020/10/24
 * Desc   :
 */
class ChainInterceptor: Interceptor.Chain {

    private val mInterceptors = arrayListOf<Interceptor>()

    fun addInterceptor(interceptor: Interceptor) {
        mInterceptors.add(interceptor)
    }

    fun addInterceptors(vararg interceptors: Interceptor) {
        mInterceptors.addAll(interceptors)
    }

    override fun process(request: BaseRequest<*>) {
        if (mInterceptors.isEmpty()) {
            request.onSuccess()
            return
        }
        // 将拦截器进行串联
        mInterceptors.forEachIndexed { index, interceptor ->
            if (index == mInterceptors.size - 1) {
                interceptor.setNextInterceptor(null)
            } else {
                interceptor.setNextInterceptor(mInterceptors[index + 1])
            }
        }
        mInterceptors.first().interceptor(request)
    }

}