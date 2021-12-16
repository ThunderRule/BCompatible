package io.github.thunderrole.bcompatible

/**
 *  功能描述：
 *
 *
 * @date 2021/12/13
 */
interface Call {
    fun request():Request

    fun execute():Response

    fun asyn(callback: Callback)

    interface Factory {
        fun newCall(request: Request): Call
    }
}