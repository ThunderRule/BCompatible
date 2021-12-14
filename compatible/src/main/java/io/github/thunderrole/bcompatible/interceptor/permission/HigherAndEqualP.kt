package io.github.thunderrole.bcompatible.interceptor.permission

import io.github.thunderrole.bcompatible.Response
import io.github.thunderrole.bcompatible.interceptor.Interceptor
import io.github.thunderrole.bcompatible.isAndroid9

/**
 *  功能描述：
 *
 *
 * @date 2021/12/14
 */
class HigherAndEqualP : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return if(isAndroid9()){
            request.body = "android9     >= 28   P"
            val builder = Response.Builder()
                .request(request)

            builder.build()
        }else{
            chain.proceed(request)
        }
    }
}