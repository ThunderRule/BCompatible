package io.github.thunderrole.bcompatible.interceptor.permission

import io.github.thunderrole.bcompatible.Response
import io.github.thunderrole.bcompatible.interceptor.Interceptor
import io.github.thunderrole.bcompatible.isAndroid10

/**
 *  功能描述：
 *
 *
 * @date 2021/12/14
 */
class HigherAndEqualQ : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return if(isAndroid10()){
            request.body = "android10     >= 29   Q"
            val builder = Response.Builder()
                .request(request)

            builder.build()
        }else{
            chain.proceed(request)
        }
    }
}