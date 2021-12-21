package io.github.thunderrole.bcompatible

import android.Manifest
import android.app.AppOpsManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.core.content.ContextCompat
import java.util.*
import kotlin.collections.HashMap

/**
 *  功能描述：
 *
 *
 * @date 2021/12/13
 */

fun <T> List<T>.toImmutableList(): List<T> {
    return Collections.unmodifiableList(toMutableList())
}

fun List<String>.toArray(): Array<Int?> {
    val array = arrayOfNulls<Int?>(size)
    for (i in 0..size) {
        array[i] = 0
    }
    return array
}

fun isHightAndroid5() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
fun isHightAndroid6() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
fun isHightAndroid8() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
fun isHightAndroid9() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
fun isHightAndroid10() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
fun isHightAndroid11() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.R

fun isGrantedPermission(context: Context, permission: String) = when (permission) {
    Manifest.permission.MANAGE_EXTERNAL_STORAGE -> isGrantedStoragePermission(context)
    Manifest.permission.MANAGE_EXTERNAL_STORAGE -> isGrantedStoragePermission(context)
    else -> ContextCompat.checkSelfPermission(
        context,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}

fun isGrantedStoragePermission(context: Context): Boolean {
    if (isHightAndroid11()) {
        return Environment.isExternalStorageManager()
    }
    return isAllGrantedPermission(context, arrayListOf(Manifest.permission_group.STORAGE))
}

fun isGrantedInstallPermission(context: Context) = if (isHightAndroid8()) {
    context.packageManager.canRequestPackageInstalls()
} else {
    true
}

fun isGrantedWindowPermission(context: Context) = if (isHightAndroid6()){
    Settings.canDrawOverlays(context)
}else{
    true
}

fun isGrantedSettingPermission(context: Context) = if (isHightAndroid6()){
    Settings.System.canWrite(context)
}else{
    true
}

fun isGrantedPackagePermission(context: Context) = if (isHightAndroid5()){
    val appOps = context.getSystemService(Context.APP_OPS_SERVICE).let { it as AppOpsManager }
    val mode = if (isHightAndroid10()){
        appOps.unsafeCheckOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,context.applicationInfo.uid,context.packageName)
    }else{
        appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,context.applicationInfo.uid,context.packageName)
    }
    mode == AppOpsManager.MODE_ALLOWED
}else{
    true
}

fun isAllGrantedPermission(context: Context, permissions: List<String>): Boolean {
    for (permission in permissions) {
        if (!isGrantedPermission(context, permission)) {
            return false
        }
    }
    return true
}

fun isSpecialPermission(permission: String): Boolean {
    return Manifest.permission.MANAGE_EXTERNAL_STORAGE == permission
            || Manifest.permission.REQUEST_INSTALL_PACKAGES == permission
            || Manifest.permission.SYSTEM_ALERT_WINDOW == permission
            || Manifest.permission.WRITE_SETTINGS == permission
            || Manifest.permission.PACKAGE_USAGE_STATS == permission
    //还缺个notification_server
}

val requestCodeMap = hashMapOf<String, Int>().apply {
    put(Manifest.permission.MANAGE_EXTERNAL_STORAGE, 232)
    put(Manifest.permission.REQUEST_INSTALL_PACKAGES, 233)
    put(Manifest.permission.SYSTEM_ALERT_WINDOW, 234)
    put(Manifest.permission.WRITE_SETTINGS, 235)
    put(Manifest.permission.PACKAGE_USAGE_STATS, 236)
}

fun requestCode(permission: String): Int = requestCodeMap[permission] ?: 1

fun containsSpecial(permissions: List<String>): Boolean =
    permissions.filter { isSpecialPermission(it) }.isNotEmpty()
