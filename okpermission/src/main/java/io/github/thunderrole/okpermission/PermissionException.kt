package io.github.thunderrole.okpermission

import kotlin.Exception

/**
 *  Function：
 *
 *
 * @date 2021/12/14
 */
class PermissionException : Exception {

    constructor():super(){

    }

    constructor(msg:String?):super(msg)

}