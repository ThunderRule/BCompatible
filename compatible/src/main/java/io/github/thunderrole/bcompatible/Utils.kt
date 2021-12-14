package io.github.thunderrole.bcompatible

import android.os.Build
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