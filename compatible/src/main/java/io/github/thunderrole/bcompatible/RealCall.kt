package io.github.thunderrole.bcompatible

import io.github.thunderrole.bcompatible.interceptor.Interceptor
import io.github.thunderrole.bcompatible.interceptor.PermissionInterceptor
import io.github.thunderrole.bcompatible.interceptor.RealInterceptorChain

/**
 *  功能描述：
 *
 *
 * @date 2021/12/13
 */
class RealCall constructor(
    val client: Compatible,
    val originalRequest: Request
) : Call {

    override fun request() = originalRequest

    override fun asyn(callback: Callback) {
        val response = getResponseWithInterceptorChain()

    }

    override fun execute() = getResponseWithInterceptorChain()

    internal fun getResponseWithInterceptorChain(): Response {
        val interceptors = mutableListOf<Interceptor>()
        interceptors += client.interceptors
        interceptors += PermissionInterceptor()

        val chain = RealInterceptorChain(
            index = 0,
            call = this,
            interceptors = interceptors,
            request = originalRequest
        )
        val response = chain.proceed(originalRequest)
        return response
    }
}