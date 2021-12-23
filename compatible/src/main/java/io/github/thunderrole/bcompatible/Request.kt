package io.github.thunderrole.bcompatible

import android.content.Context
import java.lang.ref.WeakReference

/**
 *  Function：
 *
 *
 * @date 2021/12/23
 */
class Request(builder: Builder) {
    private val permissions: ArrayList<String> = builder.permissions
    private var context:WeakReference<Context?>? = builder.context

    fun getPermissions() = permissions

    fun getContext() = context?.get()


    fun clear(){
        context?.clear()
        permissions.clear()
    }

    class Builder {
        internal var context:WeakReference<Context?>? = null
        internal val permissions: ArrayList<String> = arrayListOf()

        fun setContext(context: Context?) = apply {
            this.context = WeakReference(context)
        }

        fun setPermissions(permissions: List<String>) = apply {
            this.permissions.addAll(permissions)
        }


        fun build() = Request(this)
    }

}