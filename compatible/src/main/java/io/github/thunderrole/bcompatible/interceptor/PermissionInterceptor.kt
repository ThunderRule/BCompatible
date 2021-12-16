package io.github.thunderrole.bcompatible.interceptor

import androidx.fragment.app.FragmentActivity
import io.github.thunderrole.bcompatible.Callback
import io.github.thunderrole.bcompatible.PermissionFragment
import io.github.thunderrole.bcompatible.Request
import io.github.thunderrole.bcompatible.Response
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 *  功能描述：
 *
 *
 * @date 2021/12/16
 */
class PermissionInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val context = request.context
        if (context is FragmentActivity) {
            var def = GlobalScope.async {
                suspendCoroutine<Response> {
                    PermissionFragment.bindLife(context)
                        .addPermissions(request.permissions)
                        .callback(object : Callback {
                            override fun onRequestPermission(request: Request?) {

                            }

                            override fun onGrantedPermission(permissions: List<String>) {
                                it.resume(Response.Builder()
                                    .grantedResults(permissions)
                                    .build())
                            }

                            override fun onDeniedPermission(permissions: List<String>) {
                                it.resume(Response.Builder()
                                    .deniedResults(permissions)
                                    .build())
                            }

                        })
                }
            }

            return def.getCompleted()
        }else{
            return Response.Builder()
                .build()
        }
    }
}