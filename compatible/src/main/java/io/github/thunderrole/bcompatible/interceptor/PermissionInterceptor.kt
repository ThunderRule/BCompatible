package io.github.thunderrole.bcompatible.interceptor

import io.github.thunderrole.bcompatible.*

/**
 *  功能描述：
 *
 *
 * @date 2021/12/15
 */
class PermissionInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        return when {
            isAndroid5() -> {
                request.body = "API21 Android5 H"
                Response.Builder()
                    .request(request)
                    .grantedResults(request.permissions)
                    .build()
            }
            isAndroid6() -> {
                request.body = "API23 Android6 M"
                Response.Builder()
                    .request(request)
                    .build()
            }
            isAndroid9() -> {
                request.body = "API28 Android9 P"
                request.fragment?.run {
                    val requestPermissions = arrayListOf<String>()
                    val grantedPermission = arrayListOf<String>()
                    for (permission in request.permissions) {
                        if (isGrantedPermission(this.context!!, permission)) {
                            grantedPermission.add(permission)
                        } else {
                            requestPermissions.add(permission)
                        }
                    }
                    return Response.Builder()
                        .request(request)
                        .grantedResults(grantedPermission)
                        .deniedResults(requestPermissions)
                        .build()
                }
                Response.Builder()
                    .status(-1)
                    .build()
            }
            else -> {
                val response = chain.proceed(request)

                response
            }
        }
    }

}