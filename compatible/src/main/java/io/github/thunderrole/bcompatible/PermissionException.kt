package io.github.thunderrole.bcompatible

import kotlin.Exception

/**
 *  功能描述：
 *
 *
 * @date 2021/12/14
 */
class PermissionException : Exception {

    constructor():super(){

    }

    constructor(msg:String?):super(msg)

}