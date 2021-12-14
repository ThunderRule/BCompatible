package io.github.thunderrole.bcompatible.interceptor.permission

import io.github.thunderrole.bcompatible.Response
import io.github.thunderrole.bcompatible.interceptor.Interceptor
import io.github.thunderrole.bcompatible.isAndroid6

/**
 *  功能描述：
 *
 *
 * @date 2021/12/14
 */
class HigherAndEqualM : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return if (isAndroid6()){
            request.body = "android6     >= 23   M"
            val builder = Response.Builder()
                .request(request)

            builder.build()
        }else{
            val proceed = chain.proceed(request)
            proceed
        }

    }
}