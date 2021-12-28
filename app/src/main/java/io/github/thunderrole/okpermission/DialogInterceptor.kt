package io.github.thunderrole.okpermission

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import io.github.thunderrole.okpermission.interceptor.GoBack
import io.github.thunderrole.okpermission.interceptor.Interceptor

/**
 *  功能描述：
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
                    .setMessage("为了能够为你提供更好的服务，我们需要以下权限：\n1、相机权限\n2、存储权限")
                    .setNegativeButton("去授权") { aaa, _ ->
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
                    .setMessage("因为你已完全关闭某些权限，抱歉你将无法完全使用我们的服务")
                    .setNegativeButton("确定") { aaa, _ ->
                        callback.onPermissionBack(it)
                        aaa.dismiss()
                    }
                    .setNeutralButton("去设置"){ bbb,_->
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