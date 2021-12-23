package io.github.thunderrole.bcompatible.interceptor

import android.util.Log
import io.github.thunderrole.bcompatible.PermissionUtils
import io.github.thunderrole.bcompatible.Response

/**
 *  Function：
 *
 *
 * @date 2021/12/23
 */
class ParamsInterceptor : Interceptor {
    override fun interceptor(chain: Interceptor.Chain, callback: (response: Response) -> Unit) {
        val request = chain.request()

        chain.proceed(request){
            createMap(it)
            callback.invoke(it)
        }
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