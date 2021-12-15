package io.github.thunderrole.bcompatible

/**
 *  功能描述：
 *
 *
 * @date 2021/12/14
 */
interface Callback {
    fun onRequestPermission(request: Request?)

    fun onGrantedPermission(permissions:List<String>)

    fun onDeniedPermission(permissions: List<String>)
}