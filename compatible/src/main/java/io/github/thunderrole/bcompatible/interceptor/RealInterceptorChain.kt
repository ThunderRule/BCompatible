package io.github.thunderrole.bcompatible.interceptor

import io.github.thunderrole.bcompatible.Request
import io.github.thunderrole.bcompatible.Response

/**
 *  功能描述：
 *
 *
 * @date 2021/12/13
 */
class RealInterceptorChain(
    private val index:Int,
    private val interceptors: List<Interceptor>,
    internal val request: Request
) : Interceptor.Chain {

    private var calls: Int = 0

    internal fun copy(
        index: Int = this.index,
        request: Request = this.request
    ) = RealInterceptorChain(index, interceptors, request)

    override fun request(): Request = request

    override fun proceed(request: Request): Response {
        check(index< interceptors.size)
        calls++
        val next = copy(index = index+1,request=request)
        val interceptor = interceptors[index]
        val response = interceptor.intercept(next)

        return response
    }
}