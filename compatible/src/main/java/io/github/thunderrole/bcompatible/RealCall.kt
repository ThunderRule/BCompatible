package io.github.thunderrole.bcompatible

import io.github.thunderrole.bcompatible.interceptor.Interceptor
import io.github.thunderrole.bcompatible.interceptor.RealInterceptorChain
import io.github.thunderrole.bcompatible.interceptor.permission.*

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

    override fun execute(): Response {
        return getResponseWithInterceptorChain()
    }

    override fun asyn(callback: Callback) {
        val response = getResponseWithInterceptorChain()

        callback.onSuccess(this,response)
    }

    internal fun getResponseWithInterceptorChain(): Response {
        val interceptors = mutableListOf<Interceptor>()
        interceptors += client.interceptors
        interceptors += HigherAndEqualL()
        interceptors += HigherAndEqualM()
        interceptors += HigherAndEqualO()
        interceptors += HigherAndEqualP()
        interceptors += HigherAndEqualQ()
        interceptors += HigherAndEqualR()

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