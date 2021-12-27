package io.github.thunderrole.okpermission

import java.util.*

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
