package io.github.thunderrole.bcompatible.interceptor

import io.github.thunderrole.bcompatible.Request

/**
 *  Function：
 *
 *
 * @date 2021/12/23
 */
interface Interceptor {

    fun interceptor(chain: Chain, callback: GoBack)

    interface Chain {

        fun request(): Request

        fun proceed(request: Request, callback: GoBack)
    }
}