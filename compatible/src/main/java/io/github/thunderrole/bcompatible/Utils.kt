package io.github.thunderrole.bcompatible

import android.Manifest
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import java.util.*

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
    for (i in 0..size){
        array[i] = 0
    }
    return array
}

fun isAndroid5() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT < Build.VERSION_CODES.M
fun isAndroid6() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.O
fun isAndroid8() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && Build.VERSION.SDK_INT < Build.VERSION_CODES.P
fun isAndroid9() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && Build.VERSION.SDK_INT < Build.VERSION_CODES.Q
fun isAndroid10() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && Build.VERSION.SDK_INT < Build.VERSION_CODES.R
fun isAndroid11() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.R

fun isGrantedPermission(context: Context,permission:String) = ContextCompat.checkSelfPermission(context,permission) == PackageManager.PERMISSION_GRANTED

fun isAllGrantedPermission(context: Context,permissions:List<String>):Boolean{
    for (permission in permissions) {
        if (!isGrantedPermission(context,permission)) {
            return false
        }
    }
    return true
}

fun isSpecialPermission(permission: String):Boolean {
    return Manifest.permission.MANAGE_EXTERNAL_STORAGE == permission
            || Manifest.permission.REQUEST_INSTALL_PACKAGES == permission
            || Manifest.permission.SYSTEM_ALERT_WINDOW == permission
            || Manifest.permission.WRITE_SETTINGS == permission
            || Manifest.permission.PACKAGE_USAGE_STATS == permission
}
