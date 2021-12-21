package io.github.thunderrole.bcompatible

import android.Manifest
import android.content.Intent
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
class PermissionFragment : Fragment {
    private val TAG = "PermissionFragment"

    internal val mPermissions = arrayListOf<String>()
    private var mCallback: Callback? = null
    private var mLife: LifecycleObserver? = null
    private var mBuilder:Builder? = null

    @JvmOverloads
    constructor(builder: Builder? = null){
        mBuilder = builder?: Builder()
    }

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

        if (mBuilder != null) {
            mPermissions.addAll(mBuilder!!.permissions)
            mCallback = mBuilder!!.callback
            mLife = mBuilder!!.life
        }

        requestSpecialPermission(mPermissions)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "onActivityResult: $requestCode")
        when (requestCode) {
            requestCode(Manifest.permission.MANAGE_EXTERNAL_STORAGE) -> {
            }
            requestCode(Manifest.permission.REQUEST_INSTALL_PACKAGES) -> {

            }
            requestCode(Manifest.permission.SYSTEM_ALERT_WINDOW) -> {

            }
            requestCode(Manifest.permission.WRITE_SETTINGS) -> {

            }
            requestCode(Manifest.permission.PACKAGE_USAGE_STATS) -> {

            }
            else -> {
            }
        }
    }

    private fun requestSpecialPermission(permissions: List<String>){
        var requested = false
        for (permission in permissions) {
            if (isSpecialPermission(permission)){
                if (isGrantedPermission(requireContext(),permission)){
                    continue
                }
                if (Manifest.permission.MANAGE_EXTERNAL_STORAGE == permission && !isHightAndroid11()){
                    continue
                }
                startActivityForResult(PermissionSettingPage.createSmartPermissionIntent(requireContext(),
                    arrayListOf(permission)), requestCode(permission))
                requested = true
            }
        }

        if (requested){
            return
        }

        if (isHightAndroid6()){
            requestDangerousPermission(permissions)
        }else{
            mCallback?.onGrantedPermission(mPermissions)
        }
    }

    private fun requestDangerousPermission(permissions: List<String>){
        val deniedResults = arrayListOf<String>()
        val grantedResults = arrayListOf<String>()
        for (permission in permissions) {
            context?.let {
                if (isGrantedPermission(it, permission)) {
                    grantedResults += permission
                } else if (!isSpecialPermission(permission)){
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

    class Builder {
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

            val lifeFragment = PermissionFragment(this)
            lifeFragment.retainInstance = true

            lifeFragment.attacthActivity(activity)

            return lifeFragment
        }
    }

}