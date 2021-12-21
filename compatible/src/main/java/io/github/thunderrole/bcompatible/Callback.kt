package io.github.thunderrole.bcompatible

import java.io.Serializable

/**
 *  功能描述：
 *
 *
 * @date 2021/12/14
 */
interface Callback:Serializable {
    fun onGrantedPermission(permissions:List<String>)

    fun onDeniedPermission(permissions: List<String>)
}