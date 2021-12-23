package io.github.thunderrole.bcompatible

import android.app.AlertDialog
import io.github.thunderrole.bcompatible.interceptor.Interceptor

/**
 *  功能描述：
 *
 *
 * @date 2021/12/23
 */
class DialogInterceptor : Interceptor {
    override fun interceptor(chain: Interceptor.Chain, callback: (response: Response) -> Unit) {
        val request = chain.request()
        val permissions = request.getPermissions()
        request.getContext()?.let {
            if (!PermissionUtils.isAllGrantedPermission(it,permissions)) {
                AlertDialog.Builder(request.getContext())
                    .setMessage("我想用啊~~~")
                    .setNegativeButton(
                        "嗯？"
                    ) { _, _ ->
                        chain.proceed(request) {
                            if (it.foreverDenieds.isNotEmpty()) {
                                AlertDialog.Builder(request.getContext())
                                    .setMessage("被永久拒绝了，你不能用了")
                                    .setNegativeButton("哈") { aaa, _ ->
                                        callback.invoke(it)
                                        aaa.dismiss() }
                                    .create().show()
                            }
                        }
                    }.create().show()
            }
        }



    }

}