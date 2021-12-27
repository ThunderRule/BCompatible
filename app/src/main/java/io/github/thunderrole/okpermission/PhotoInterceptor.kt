package io.github.thunderrole.okpermission

import android.Manifest
import android.content.Intent
import android.provider.MediaStore
import io.github.thunderrole.okpermission.interceptor.GoBack
import io.github.thunderrole.okpermission.interceptor.Interceptor

/**
 *  功能描述：
 *
 *
 * @date 2021/12/27
 */
class PhotoInterceptor : Interceptor {
    override fun interceptor(chain: Interceptor.Chain, callback: GoBack) {
        val request = chain.request()
        chain.proceed(request){
            if (it.resultMap.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == true){
                val intent = if (PermissionUtils.isHightAndroid11()) {
                    Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                }else{
                    val intent = Intent(Intent.ACTION_GET_CONTENT)
                    intent.setType("image/*")
                    Intent.createChooser(intent,null)
                }
                request.getContext()?.startActivity(intent)
            }else{
                callback.onPermissionBack(it)
            }
        }
    }
}