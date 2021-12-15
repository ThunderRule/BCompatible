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
    val type: String?,
    var body: String?,
    val permissions: ArrayList<String>
) {
    internal var fragment: Fragment? = null


    internal fun setFragment(fragment: Fragment?) = apply {
        this.fragment = fragment
    }

    class Builder {
        internal var type: String? = null
        internal var permissions: ArrayList<String> = arrayListOf()
        internal var body: String? = null


        fun type(type: String?) = apply {
            this.type = type
        }

        fun permission(permission: String) = apply {
            this.permissions += permission
        }

        internal fun permissions(permissions: List<String>) = apply {
            this.permissions.addAll(permissions)
        }

        fun body(body: String?) = apply {
            this.body = body
        }

        fun build(): Request {
            return Request(this.type, this.body, this.permissions)
        }
    }

}