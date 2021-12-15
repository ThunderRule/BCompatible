package io.github.thunderrole.bcompatible

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import io.github.thunderrole.bcompatible.interceptor.Interceptor
import io.github.thunderrole.bcompatible.interceptor.PermissionInterceptor
import io.github.thunderrole.bcompatible.interceptor.RealInterceptorChain

/**
 *  功能描述：
 *
 *
 * @date 2021/12/14
 */
class Compatible : Fragment() {
    internal val interceptors: MutableList<Interceptor> = mutableListOf()
    internal val mPermissions = arrayListOf<String>()
    private var mCallback: Callback? = null

    private fun attacthActivity(activity: FragmentActivity?) {
        activity?.supportFragmentManager?.beginTransaction()?.add(this, this.toString())
            ?.commitAllowingStateLoss()
    }

    private fun detachActivity(activity: FragmentActivity?) {
        activity?.supportFragmentManager?.beginTransaction()?.remove(this)
            ?.commitAllowingStateLoss()
    }

    companion object {
        val KEY_REQUEST_TYPE = "key_request_type"
        val KEY_REQUEST_BODY = "key_request_body"
        val KEY_REQUEST_PERMISSION = "key_request_permission"

        @JvmStatic
        fun bindLife(activity: FragmentActivity?): Compatible {
            val lifeFragment = Compatible()
            lifeFragment.retainInstance = true

            lifeFragment.attacthActivity(activity)

            return lifeFragment
        }

    }

    fun addPermission(permission: String) = apply {
        mPermissions += permission
    }

    fun addInterceptor(interceptor: Interceptor) = apply {
        interceptors += interceptor
    }

    fun callback(callback: Callback) {
        mCallback = callback
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val deniedResults = arrayListOf<String>()
        val grantedResult = arrayListOf<String>()
        for (permission in mPermissions) {
            context?.let {
                if (isGrantedPermission(it, permission)) {
                    grantedResult += permission
                } else {
                    deniedResults += permission
                }
            }
        }
        if (!deniedResults.isNullOrEmpty()) {
            requestPermissions(deniedResults.toTypedArray(), 111)
        }
        if (!grantedResult.isNullOrEmpty()) {
            mCallback?.onGrantedPermission(grantedResult)
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if (permissions.isNullOrEmpty()
            || grantResults == null || grantResults.isEmpty()
        ) {
            return
        }

        val grantedPermissions = arrayListOf<String>()
        val deniedPermissions = arrayListOf<String>()
        for (permission in permissions) {
            if (isGrantedPermission(context!!, permission)) {
                grantedPermissions += permission
            } else {
                deniedPermissions += permission
            }
        }

        if (grantedPermissions.isNotEmpty()) {
            mCallback?.onGrantedPermission(grantedPermissions)
        }

        if (deniedPermissions.isNotEmpty()) {
            mCallback?.onDeniedPermission(deniedPermissions)
        }


        detachActivity(activity)
    }


}