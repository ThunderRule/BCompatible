package io.github.thunderrole.bcompatible

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AppOpsManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.core.content.ContextCompat

/**
 *  Function：
 *
 *
 * @date 2021/12/22
 */
object PermissionUtils {
    /**
     * Special permission request code
     */
    private val requestCodeMap = hashMapOf<String, Int>().apply {
        put(Manifest.permission.MANAGE_EXTERNAL_STORAGE, 232)
        put(Manifest.permission.REQUEST_INSTALL_PACKAGES, 233)
        put(Manifest.permission.SYSTEM_ALERT_WINDOW, 234)
        put(Manifest.permission.WRITE_SETTINGS, 235)
        put(Manifest.permission.PACKAGE_USAGE_STATS, 236)
    }

    /**
     * Whether the current system version is higher than the specified version
     */
    fun isHightAndroid5() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
    fun isHightAndroid6() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    fun isHightAndroid8() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
    fun isHightAndroid9() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
    fun isHightAndroid10() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
    fun isHightAndroid11() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.R
    fun isHightAndroid12() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S


    /**
     * Whether to grant permission
     */
    fun isGrantedPermission(context: Context, permission: String) = if (!isHightAndroid6()){
        true
    }else{
        when (permission) {
            Manifest.permission.MANAGE_EXTERNAL_STORAGE -> isGrantedStoragePermission(context)
            Manifest.permission.REQUEST_INSTALL_PACKAGES -> isGrantedInstallPermission(context)
            Manifest.permission.SYSTEM_ALERT_WINDOW -> isGrantedWindowPermission(context)
            Manifest.permission.WRITE_SETTINGS -> isGrantedSettingPermission(context)
            Manifest.permission.PACKAGE_USAGE_STATS -> isGrantedPackagePermission(context)
            else -> ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    /**
     * Whether to grant MANAGE_EXTERNAL_STORAGE permission
     */
    fun isGrantedStoragePermission(context: Context): Boolean {
        if (isHightAndroid11()) {
            return Environment.isExternalStorageManager()
        }
        return isAllGrantedPermission(context, arrayListOf(Manifest.permission_group.STORAGE))
    }

    /**
     * Whether to grant intall unknow apk permission
     */
    fun isGrantedInstallPermission(context: Context) = if (isHightAndroid8()) {
        context.packageManager.canRequestPackageInstalls()
    } else {
        true
    }

    /**
     * Whether to grant SYSTEM_ALERT_WINDOW permission
     */
    fun isGrantedWindowPermission(context: Context) = if (isHightAndroid6()) {
        Settings.canDrawOverlays(context)
    } else {
        true
    }

    /**
     * Whether to grant WRITE_SETTINGS permission
     */
    fun isGrantedSettingPermission(context: Context) = if (isHightAndroid6()) {
        Settings.System.canWrite(context)
    } else {
        true
    }

    /**
     * Whether to grant PACKAGE_USAGE_STATS permission
     */
    fun isGrantedPackagePermission(context: Context) = if (isHightAndroid5()) {
        val appOps = context.getSystemService(Context.APP_OPS_SERVICE).let { it as AppOpsManager }
        val mode = if (isHightAndroid10()) {
            appOps.unsafeCheckOpNoThrow(
                AppOpsManager.OPSTR_GET_USAGE_STATS,
                context.applicationInfo.uid,
                context.packageName
            )
        } else {
            appOps.checkOpNoThrow(
                AppOpsManager.OPSTR_GET_USAGE_STATS,
                context.applicationInfo.uid,
                context.packageName
            )
        }
        mode == AppOpsManager.MODE_ALLOWED
    } else {
        true
    }

    fun isAllGrantedPermission(context: Context, permissions: List<String>) = permissions.find { !isGrantedPermission(context,it) } == null

    /**
     * Determine whether the permission is a special permission
     *
     * special permission need to go to the settings page
     */
    fun isSpecialPermission(permission: String): Boolean {
        return Manifest.permission.MANAGE_EXTERNAL_STORAGE == permission
                || Manifest.permission.REQUEST_INSTALL_PACKAGES == permission
                || Manifest.permission.SYSTEM_ALERT_WINDOW == permission
                || Manifest.permission.WRITE_SETTINGS == permission
                || Manifest.permission.PACKAGE_USAGE_STATS == permission
        //lack notification_server
    }


    fun isDeniedForever(activity: Activity, permissions: List<String>) = permissions.find { isDeniedForever(activity,it) } != null

    /**
     * Whether permission is permanently denied
     */
    fun isDeniedForever(activity: Activity, permission: String): Boolean {
        if (!isHightAndroid6()) {
            return false
        }

        if (isSpecialPermission(permission)) {
            return false
        }

        if (!isHightAndroid12()) {
            if (Manifest.permission.BLUETOOTH_SCAN == permission) {
                return !isGrantedPermission(
                    activity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) && !activity.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
            }

            if (Manifest.permission.BLUETOOTH_CONNECT == permission || Manifest.permission.BLUETOOTH_ADVERTISE == permission) {
                return false
            }
        }

        if (isHightAndroid10()) {
            if (Manifest.permission.ACCESS_BACKGROUND_LOCATION == permission
                && !isGrantedPermission(
                    activity,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                )
                && !isGrantedPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
            ){
                return !activity.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }

        if (!isHightAndroid10()){
            if (Manifest.permission.ACCESS_BACKGROUND_LOCATION == permission){
                return !isGrantedPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                        && !activity.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
            }

            if (Manifest.permission.ACTIVITY_RECOGNITION == permission){
                return !isGrantedPermission(activity, Manifest.permission.BODY_SENSORS)
                        && !activity.shouldShowRequestPermissionRationale(Manifest.permission.BODY_SENSORS)
            }

            if (Manifest.permission.ACCESS_MEDIA_LOCATION == permission){
                return false
            }
        }

        if (!isHightAndroid8()){
            if (Manifest.permission.ANSWER_PHONE_CALLS == permission){
                return false
            }

            if (Manifest.permission.READ_PHONE_NUMBERS == permission){
                return !isGrantedPermission(activity, Manifest.permission.READ_PHONE_STATE)
                        && !activity.shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE)
            }
        }

        return !isGrantedPermission(activity,permission) && !activity.shouldShowRequestPermissionRationale(permission)

    }


    fun requestCode(permission: String): Int = requestCodeMap[permission] ?: 1

    fun containsSpecial(permissions: List<String>): Boolean =
        permissions.any { isSpecialPermission(it) }

}