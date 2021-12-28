package io.github.thunderrole.okpermission.interceptor

import io.github.thunderrole.okpermission.Request

/**
 *  Function：
 *
 *
 * @date 2021/12/23
 */
class InterceptorChain(
    private val interceptors: List<Interceptor>,
    private val index:Int,
    internal val request: Request
):Interceptor.Chain {

    private var calls:Int = 0

    internal fun copy(index: Int = this.index) = InterceptorChain(interceptors,index,request)

    override fun request() = request

    override fun proceed(request: Request, callback: GoBack) {
        check(index < interceptors.size)

        calls++

        val next = copy(index = index+1)
        val interceptor = interceptors[index]

        interceptor.interceptor(next,callback)
    }


}