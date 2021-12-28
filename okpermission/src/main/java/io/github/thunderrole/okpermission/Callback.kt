package io.github.thunderrole.okpermission

/**
 *  Function：
 *
 *
 * @date 2021/12/14
 */
interface Callback {
    fun onGrantedPermission(permissions:List<String>)

    fun onDeniedPermission(permissions: List<String>)

    fun onForeverDeniedPermission(permissions: List<String>)
}