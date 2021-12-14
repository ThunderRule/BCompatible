package io.github.thunderrole.bcompatible.interceptor.permission

import android.os.Build
import io.github.thunderrole.bcompatible.Response
import io.github.thunderrole.bcompatible.interceptor.Interceptor
import io.github.thunderrole.bcompatible.isAndroid5

/**
 *  功能描述：
 *
 *
 * @date 2021/12/14
 */
class HigherAndEqualL : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return if (isAndroid5()) {
            request.body = "android5     >= 21   L"
            val builder = Response.Builder()
                .request(request)

            for (permission in request.permissions) {
                builder.grantResult(0)
            }

            builder.build()
        } else {
            val proceed = chain.proceed(request)
            proceed
        }
    }
}