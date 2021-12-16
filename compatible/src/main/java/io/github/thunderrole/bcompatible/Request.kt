package io.github.thunderrole.bcompatible

import android.content.Context
import android.os.Build
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 *  功能描述：
 *
 *
 * @date 2021/12/13
 */
class Request internal constructor(
    val context: Context?,
    val type: String?,
    var body: String?,
    val permissions: ArrayList<String>
) {

    class Builder {
        internal var context: Context? = null
        internal var type: String? = null
        internal var permissions: ArrayList<String> = arrayListOf()
        internal var body: String? = null

        fun setContext(context: Context?) = apply {
            this.context = context
        }

        fun setType(type: String?) = apply {
            this.type = type
        }

        fun setPermission(permission:String) = apply {
            this.permissions.add(permission)
        }

        fun setPermissions(permissions: List<String>) = apply {
            this.permissions.addAll(permissions)
        }

        fun setBody(body: String?) = apply {
            this.body = body
        }

        fun build(): Request {
            return Request(this.context, this.type, this.body, this.permissions)
        }
    }

}