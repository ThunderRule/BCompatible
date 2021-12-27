package io.github.thunderrole.okpermission

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleObserver

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
    private var mBuilder: Builder? = null

    @JvmOverloads
    constructor(builder: Builder? = null) {
        mBuilder = builder ?: Builder()
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

        mBuilder?.let {
            mPermissions.addAll(it.permissions)
            mCallback = it.callback
            mLife = it.life
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
        val foreverDeniedPermissions = arrayListOf<String>()
        for (permission in permissions) {
            if (PermissionUtils.isGrantedPermission(context!!, permission)) {
                grantedPermissions += permission
            } else if (PermissionUtils.isDeniedForever(activity!!, permission)) {
                foreverDeniedPermissions += permission
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

        if (foreverDeniedPermissions.isNotEmpty()) {
            mCallback?.onForeverDeniedPermission(foreverDeniedPermissions)
        }

        detachActivity(activity)
        mLife?.let {
            lifecycle.removeObserver(it)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "onActivityResult: $requestCode")
        context?.let {
            val grantResults = arrayListOf<String>()
            val deniedResults = arrayListOf<String>()
            when (requestCode) {
                PermissionUtils.requestCode(Manifest.permission.MANAGE_EXTERNAL_STORAGE) -> {
                    if (PermissionUtils.isGrantedStoragePermission(it)) {
                        grantResults += Manifest.permission.MANAGE_EXTERNAL_STORAGE
                    } else {
                        deniedResults += Manifest.permission.MANAGE_EXTERNAL_STORAGE
                    }
                }
                PermissionUtils.requestCode(Manifest.permission.REQUEST_INSTALL_PACKAGES) -> {
                    if (PermissionUtils.isGrantedInstallPermission(it)) {
                        grantResults += Manifest.permission.REQUEST_INSTALL_PACKAGES
                    } else {
                        deniedResults += Manifest.permission.REQUEST_INSTALL_PACKAGES
                    }
                }
                PermissionUtils.requestCode(Manifest.permission.SYSTEM_ALERT_WINDOW) -> {
                    if (PermissionUtils.isGrantedWindowPermission(it)) {
                        grantResults += Manifest.permission.SYSTEM_ALERT_WINDOW
                    } else {
                        deniedResults += Manifest.permission.SYSTEM_ALERT_WINDOW
                    }
                }
                PermissionUtils.requestCode(Manifest.permission.WRITE_SETTINGS) -> {
                    if (PermissionUtils.isGrantedSettingPermission(it)) {
                        grantResults += Manifest.permission.WRITE_SETTINGS
                    } else {
                        deniedResults += Manifest.permission.WRITE_SETTINGS
                    }
                }
                PermissionUtils.requestCode(Manifest.permission.PACKAGE_USAGE_STATS) -> {
                    if (PermissionUtils.isGrantedPackagePermission(it)) {
                        grantResults += Manifest.permission.PACKAGE_USAGE_STATS
                    } else {
                        deniedResults += Manifest.permission.PACKAGE_USAGE_STATS
                    }
                }
                else -> {
                }
            }
            if (grantResults.isNotEmpty()) {
                mCallback?.onGrantedPermission(grantResults)
            }
            if (deniedResults.isNotEmpty()) {
                mCallback?.onDeniedPermission(deniedResults)
            }
            requestDangerousPermission()
        }
    }

    private fun requestSpecialPermission(permissions: List<String>) {
        var requested = false
        for (permission in permissions) {
            if (PermissionUtils.isSpecialPermission(permission)) {
                if (PermissionUtils.isGrantedPermission(requireContext(), permission)) {
                    continue
                }
                if (Manifest.permission.MANAGE_EXTERNAL_STORAGE == permission && !PermissionUtils.isHightAndroid11()) {
                    continue
                }
                startActivityForResult(
                    IntentCreater.createSmartPermissionIntent(
                        requireContext(),
                        arrayListOf(permission)
                    ), PermissionUtils.requestCode(permission)
                )
                requested = true
            }
        }

        if (requested) {
            return
        }

        requestDangerousPermission()
    }

    private fun requestDangerousPermission() {
        if (!PermissionUtils.isHightAndroid6()) {
            val filter = mPermissions.filter { PermissionUtils.isGrantedPermission(context!!, it) }
            mCallback?.onGrantedPermission(filter)
            return
        }
        val deniedResults = arrayListOf<String>()
        val grantedResults = arrayListOf<String>()
        for (permission in mPermissions) {
            context?.let {
                if (PermissionUtils.isGrantedPermission(it, permission)) {
                    grantedResults += permission
                } else if (!PermissionUtils.isSpecialPermission(permission)) {
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

    override fun onDestroy() {
        super.onDestroy()
        mCallback = null
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