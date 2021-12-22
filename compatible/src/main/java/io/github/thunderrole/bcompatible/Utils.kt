package io.github.thunderrole.bcompatible

import android.Manifest
import android.app.Activity
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
 *  Function：
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
