package io.github.thunderrole.bcompatible

/**
 *  功能描述：
 *
 *
 * @date 2021/12/14
 */
interface Callback {
    fun onSuccess(call: Call, response: Response)

    fun onFailure(call: Call, e: PermissionException)
}