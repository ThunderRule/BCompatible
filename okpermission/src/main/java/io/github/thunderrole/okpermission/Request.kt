package io.github.thunderrole.okpermission

import android.content.Context
import androidx.fragment.app.FragmentActivity
import java.lang.ref.WeakReference

/**
 *  Function：
 *
 *
 * @date 2021/12/23
 */
class Request(builder: Builder) {
    private val permissions: ArrayList<String> = builder.permissions
    private var context:WeakReference<FragmentActivity?>? = builder.context
    private val isOrdered = builder.isOrdered

    fun getPermissions() = permissions

    fun changePermissions(permissions: List<String>){
        this.permissions.clear()
        this.permissions.addAll(permissions)
    }

    fun getContext() = context?.get()

    fun getIsOrdered() = isOrdered

    fun clear(){
        context?.clear()
        permissions.clear()
    }

    class Builder {
        internal var context:WeakReference<FragmentActivity?>? = null
        internal val permissions: ArrayList<String> = arrayListOf()
        internal var isOrdered = false

        fun setContext(context: FragmentActivity?) = apply {
            this.context = WeakReference(context)
        }

        fun setPermissions(permissions: List<String>) = apply {
            this.permissions.addAll(permissions)
        }

        fun setIsOrdered(isOrdered:Boolean) = apply {
            this.isOrdered = isOrdered
        }

        fun build() = Request(this)
    }

}