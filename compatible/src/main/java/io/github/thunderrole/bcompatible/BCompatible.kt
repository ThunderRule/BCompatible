package io.github.thunderrole.bcompatible

import android.content.Context
import io.github.thunderrole.bcompatible.interceptor.*

/**
 *  Function：
 *
 *
 * @date 2021/12/23
 */
class BCompatible(builder:Builder) {
    private val mPermissions = builder.permissions
    private val mInterceptors = builder.interceptors

    fun start(context: Context?, callback: GoBack) {
        val request = Request.Builder()
            .setPermissions(mPermissions)
            .setContext(context)
            .build()

        callbackWithChain(request, callback)

    }

    private fun callbackWithChain(request: Request, callback: GoBack) {
        val interceptors = arrayListOf<Interceptor>()
        interceptors += mInterceptors
        interceptors += ParamsInterceptor()
        interceptors += PermissionInterceptor()

        val chain =
            InterceptorChain(interceptors = interceptors, index = 0, request = request)

        chain.proceed(request){
            callback.onPermissionBack(it)
            request.clear()
        }
    }

    class Builder{
        internal val permissions = arrayListOf<String>()
        internal val interceptors = arrayListOf<Interceptor>()

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

        fun build() = BCompatible(this)
    }

}