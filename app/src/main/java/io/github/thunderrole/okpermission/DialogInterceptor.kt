package io.github.thunderrole.okpermission

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import io.github.thunderrole.okpermission.interceptor.GoBack
import io.github.thunderrole.okpermission.interceptor.Interceptor

/**
 *  Function：
 *
 *
 * @date 2021/12/23
 */
class DialogInterceptor : Interceptor {
    override fun interceptor(chain: Interceptor.Chain, callback: GoBack) {
        val request = chain.request()
        val permissions = request.getPermissions()
        request.getContext()?.let { context ->
            if (!PermissionUtils.isAllGrantedPermission(context, permissions)) {
                AlertDialog.Builder(request.getContext())
                    .setMessage("In order to be able to provide you with better services, we need the following permissions:\n" +
                            "1. Camera permissions\n" +
                            "2. Storage permissions")
                    .setNegativeButton("To authorize") { aaa, _ ->
                        showDeniedDialog(chain, callback, context)
                        aaa.dismiss()
                    }
                    .create().show()
            }else{
                chain.proceed(request, callback)
            }
        }
    }

    private fun showDeniedDialog(chain: Interceptor.Chain,callback: GoBack,context: Context){
        val request = chain.request()
        chain.proceed(request) {
            if (it.foreverDenieds.isNotEmpty()) {
                AlertDialog.Builder(request.getContext())
                    .setMessage("Because you have completely turned off some permissions, sorry you will not be able to fully use our services")
                    .setNegativeButton("Ok") { aaa, _ ->
                        callback.onPermissionBack(it)
                        aaa.dismiss()
                    }
                    .setNeutralButton("Go to set"){ bbb,_->
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        intent.data = Uri.parse("package:${context.packageName}")
                        context.startActivity(intent)
                        bbb.dismiss()
                    }
                    .create().show()
            }else{
                callback.onPermissionBack(it)
            }
        }

    }

}