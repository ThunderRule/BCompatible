package io.github.thunderrole.bcompatible

import android.content.Context
import android.os.Build
import androidx.fragment.app.Fragment

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
    val skipLast: Boolean,
    val permissions: List<String>
) {

    class Builder {
        internal var context: Context? = null
        internal var type: String? = null
        internal var permissions: List<String> = mutableListOf()
        internal var body: String? = null
        internal var skipLast: Boolean = false

        fun context(context: Context?){
            this.context = context
        }

        fun context(fragment: Fragment?){
            context(fragment?.context)
        }

        fun type(type: String?) = apply {
            this.type = type
        }

        fun permission(permission: String) = apply {
            this.permissions += permission
        }

        fun body(body: String?) = apply {
            this.body = body
        }

        fun skip(skipLast: Boolean) = apply {
            this.skipLast = skipLast
        }

        fun build(): Request {
            return Request(this.context, this.type, this.body, this.skipLast, this.permissions)
        }
    }

}