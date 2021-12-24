package io.github.thunderrole.bcompatible.interceptor

import io.github.thunderrole.bcompatible.Response

/**
 *  Function：
 *
 *
 * @date 2021/12/23
 */
class ParamsInterceptor : Interceptor {
    override fun interceptor(chain: Interceptor.Chain, callback: GoBack) {
        val request = chain.request()

        chain.proceed(request,
            GoBack {
                createMap(it)
                callback.onPermissionBack(it)
            })
    }

    private fun createMap(response: Response){
        val map = hashMapOf<String,Boolean>()
        for (granted in response.granteds) {
            map[granted] = true
        }
        for (denied in response.denieds) {
            map[denied] = false
        }
        response.setResultMap(map)
    }
}