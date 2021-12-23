package io.github.thunderrole.bcompatible.interceptor

import androidx.fragment.app.FragmentActivity
import io.github.thunderrole.bcompatible.Callback
import io.github.thunderrole.bcompatible.PermissionFragment
import io.github.thunderrole.bcompatible.Request
import io.github.thunderrole.bcompatible.Response

/**
 *  Function：
 *
 *
 * @date 2021/12/23
 */
class PermissionInterceptor : Interceptor {

    override fun interceptor(chain: Interceptor.Chain, callback: (response: Response) -> Unit) {
        val request = chain.request()
        val builder = Response.Builder()
        PermissionFragment.Builder()
            .addPermissions(request.getPermissions())
            .setCallback(object : Callback {
                override fun onGrantedPermission(permissions: List<String>) {
                    builder.setGranteds(permissions)
                        .setStatus(permissions.size)
                    comeback(request,builder,callback)
                }

                override fun onDeniedPermission(permissions: List<String>) {
                    builder.setDenieds(permissions)
                        .setStatus(permissions.size)
                    comeback(request,builder,callback)
                }

                override fun onForeverDeniedPermission(permissions: List<String>) {
                    builder.setForeverDenieds(permissions)
                        .setStatus(permissions.size)
                    comeback(request,builder,callback)
                }

            }).start(request.getContext().let { it as FragmentActivity })
    }

    private fun comeback(request: Request,builder:Response.Builder,callback: (response: Response) -> Unit){
        if (builder.status == request.getPermissions().size){
            callback.invoke(builder.build())
        }
    }
}