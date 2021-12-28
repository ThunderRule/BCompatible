package io.github.thunderrole.okpermission.interceptor

import android.app.Activity
import io.github.thunderrole.okpermission.PermissionUtils
import io.github.thunderrole.okpermission.Response

/**
 *  Function：
 *
 *
 * @date 2021/12/23
 */
class ParamsInterceptor : Interceptor {
    override fun interceptor(chain: Interceptor.Chain, callback: GoBack) {
        val request = chain.request()
        val permissions = request.getPermissions()

        request.getContext()?.let { context ->
            val granteds = permissions.filter { PermissionUtils.isGrantedPermission(context,it) }
            if (granteds.size == permissions.size) {
                val response = Response.Builder()
                    .setStatus(permissions.size)
                    .setGranteds(granteds)
                    .build()
                createMap(response)
                callback.onPermissionBack(response)
                return
            }

            chain.proceed(request) {
                createMap(it)
                callback.onPermissionBack(it)
            }
        }

    }

    private fun createMap(response: Response) {
        val map = hashMapOf<String, Boolean>()
        for (granted in response.granteds) {
            map[granted] = true
        }
        for (denied in response.denieds) {
            map[denied] = false
        }
        for (denied in response.foreverDenieds) {
            map[denied] = false
        }

        response.setResultMap(map)
    }
}