package io.github.thunderrole.okpermission

import java.io.Serializable

/**
 *  Function：
 *
 *
 * @date 2021/12/14
 */
interface Callback:Serializable {
    fun onGrantedPermission(permissions:List<String>)

    fun onDeniedPermission(permissions: List<String>)

    fun onForeverDeniedPermission(permissions: List<String>)
}