package io.github.thunderrole.bcompatible.interceptor

import io.github.thunderrole.bcompatible.Call
import io.github.thunderrole.bcompatible.Request
import io.github.thunderrole.bcompatible.Response

/**
 *  功能描述：
 *
 *
 * @date 2021/12/13
 */
interface Interceptor {
    fun intercept(chain: Chain):Response

    interface Chain{
        fun request():Request
        fun proceed(request: Request):Response

        fun call():Call
    }
}