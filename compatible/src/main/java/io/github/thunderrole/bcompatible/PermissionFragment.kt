package io.github.thunderrole.bcompatible

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleObserver
import java.io.Serializable

/**
 *  功能描述：
 *
 *
 * @date 2021/12/14
 */
class PermissionFragment : Fragment() {
    private val TAG = "PermissionFragment"

    internal val mPermissions = arrayListOf<String>()
    private var mCallback: Callback? = null
    private var mLife: LifecycleObserver? = null

    private fun attacthActivity(activity: FragmentActivity?) {
        activity?.supportFragmentManager?.beginTransaction()?.add(this, this.toString())
            ?.commitAllowingStateLoss()
    }

    private fun detachActivity(activity: FragmentActivity?) {
        activity?.supportFragmentManager?.beginTransaction()?.remove(this)
            ?.commitAllowingStateLoss()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
        var builder = arguments?.getSerializable("builder")?.let {
            it as Builder
        }

        if (builder != null) {
            mPermissions.addAll(builder.permissions)
            mCallback = builder.callback
            mLife = builder.life
        }



        val deniedResults = arrayListOf<String>()
        val grantedResults = arrayListOf<String>()

        if (isAndroid5()){
            mCallback?.onGrantedPermission(mPermissions)
        }else{
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

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Log.d(TAG, "onRequestPermissionsResult: ")

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
        mLife?.let {
            lifecycle.removeObserver(it)
        }
    }

    class Builder : Serializable {
        internal val permissions = arrayListOf<String>()
        internal var callback: Callback? = null
        internal var life: LifecycleObserver? = null

        fun addPermission(permission: String) = apply {
            permissions += permission
        }

        fun addPermissions(permissions: List<String>) = apply {
            this.permissions += permissions
        }

        fun setCallback(callback: Callback) = apply {
            this.callback = callback
        }

        fun setLifecycle(life: LifecycleObserver) = apply {
            this.life = life
        }

        fun start(activity: FragmentActivity?): PermissionFragment? {

            val lifeFragment = PermissionFragment()
            lifeFragment.retainInstance = true

            val bundle = Bundle()
            bundle.putSerializable("builder", this)
            lifeFragment.arguments = bundle
            lifeFragment.attacthActivity(activity)

            return lifeFragment
        }
    }

}