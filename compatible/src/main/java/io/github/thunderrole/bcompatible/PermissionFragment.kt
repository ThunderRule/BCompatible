package io.github.thunderrole.bcompatible

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import io.github.thunderrole.bcompatible.interceptor.Interceptor
import java.io.Serializable

/**
 *  功能描述：
 *
 *
 * @date 2021/12/14
 */
class PermissionFragment : Fragment() {
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

        @JvmStatic
        fun bindLife(activity: FragmentActivity?): PermissionFragment {
            val lifeFragment = PermissionFragment()
            lifeFragment.retainInstance = true

            lifeFragment.attacthActivity(activity)

            return lifeFragment
        }

    }

    fun addPermission(permission: String) = apply {
        mPermissions += permission
    }

    fun addPermissions(permissions: List<String>) = apply {
        mPermissions += permissions
    }

    fun callback(callback: Callback) {
        mCallback = callback
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val deniedResults = arrayListOf<String>()
        val grantedResults = arrayListOf<String>()

        for (permission in mPermissions) {
            context?.let {
                if (isGrantedPermission(it, permission)) {
                    grantedResults += permission
                } else {
                    deniedResults += permission
                }
            }
        }
        if (!deniedResults.isNullOrEmpty()) {
            requestPermissions(deniedResults.toTypedArray(), 111)
        }
        if (!grantedResults.isNullOrEmpty()) {
            mCallback?.onGrantedPermission(grantedResults)
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