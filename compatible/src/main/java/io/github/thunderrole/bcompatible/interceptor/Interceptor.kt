package io.github.thunderrole.bcompatible.interceptor

import io.github.thunderrole.bcompatible.Request
import io.github.thunderrole.bcompatible.Response

/**
 *  Function：
 *
 *
 * @date 2021/12/23
 */
interface Interceptor {

    fun interceptor(chain: Chain, callback: (response: Response) -> Unit)

    interface Chain {

        fun request(): Request

        fun proceed(request: Request, callback: (response: Response) -> Unit)
    }
}