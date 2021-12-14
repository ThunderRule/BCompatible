package io.github.thunderrole.bcompatible.interceptor.permission

import io.github.thunderrole.bcompatible.Response
import io.github.thunderrole.bcompatible.interceptor.Interceptor
import io.github.thunderrole.bcompatible.isAndroid8

/**
 *  功能描述：
 *
 *
 * @date 2021/12/14
 */
class HigherAndEqualO:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return if (isAndroid8()){
            request.body = "android8     >= 26   O"
            val builder = Response.Builder()
                .request(request)

            builder.build()
        }else{
            chain.proceed(request)
        }
    }
}