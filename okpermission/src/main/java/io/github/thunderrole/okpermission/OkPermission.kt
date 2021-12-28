package io.github.thunderrole.okpermission

import android.content.Context
import androidx.fragment.app.FragmentActivity
import io.github.thunderrole.okpermission.interceptor.*

/**
 *  Function：
 *
 *
 * @date 2021/12/23
 */
class OkPermission(builder:Builder) {
    private val mPermissions = builder.permissions
    private val mInterceptors = builder.interceptors
    private val mIsOrdered = builder.isOrdered

    fun start(context: FragmentActivity?, callback: GoBack? = null) {
        val request = Request.Builder()
            .setPermissions(mPermissions)
            .setIsOrdered(mIsOrdered)
            .setContext(context)
            .build()

        callbackWithChain(request, callback)

    }

    private fun callbackWithChain(request: Request, callback: GoBack? = null) {
        val interceptors = arrayListOf<Interceptor>()
        interceptors += mInterceptors
        interceptors += ParamsInterceptor()
        interceptors += PermissionInterceptor()

        val chain =
            InterceptorChain(interceptors = interceptors, index = 0, request = request)

        chain.proceed(request){
            callback?.onPermissionBack(it)
            request.clear()
        }
    }

    class Builder{
        internal val permissions = arrayListOf<String>()
        internal val interceptors = arrayListOf<Interceptor>()
        internal var isOrdered = false

        fun addPermissions(permissions:List<String>) = apply {
            this.permissions += permissions
        }

        fun addPermission(permission: String) = apply {
            this.permissions += permission
        }

        fun addInterceptors(interceptors: List<Interceptor>) = apply {
            this.interceptors += interceptors
        }

        fun addInterceptor(interceptor: Interceptor) = apply {
            this.interceptors += interceptor
        }

        fun setIsOrdered(isOrdered:Boolean) = apply {
            this.isOrdered = isOrdered
        }

        fun build() = OkPermission(this)
    }

}