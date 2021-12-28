package io.github.thunderrole.okpermission

/**
 *  Function：
 *
 *
 * @date 2021/12/23
 */
class Response(builder: Builder) {

    @get:JvmName("status")
    internal val status = builder.status

    @get:JvmName("granteds")
    val granteds = builder.grantedPermissions

    @get:JvmName("denieds")
    val denieds = builder.deniedPermissions

    @get:JvmName("foreverDenieds")
    val foreverDenieds = builder.foreverDeniedPermissions

    @get:JvmName("resultMap")
    val resultMap = builder.resultMap

    fun setResultMap(map: Map<String, Boolean>) {
        this.resultMap += map
    }

    class Builder {
        internal val grantedPermissions = arrayListOf<String>()
        internal val deniedPermissions = arrayListOf<String>()
        internal val foreverDeniedPermissions = arrayListOf<String>()
        internal val resultMap = hashMapOf<String, Boolean>()
        internal var status = 0

        fun setGranteds(permissions: List<String>) = apply {
            grantedPermissions += permissions
        }

        fun setDenieds(permissions: List<String>) = apply {
            deniedPermissions += permissions
        }

        fun setForeverDenieds(permissions: List<String>) = apply {
            foreverDeniedPermissions += permissions
        }

        fun setResultMap(permission: String, isGranted: Boolean) = apply {
            resultMap[permission] = isGranted
        }

        fun setResultMap(map: Map<String, Boolean>) = apply {
            this.resultMap.putAll(map)
        }

        fun setStatus(size:Int) = apply {
            status += size
        }

        fun build() = Response(this)

    }

    override fun toString(): String {
        return "granteds = $granteds, \ndenieds = $denieds,\nforeverDenieds = $foreverDenieds," +
                "\nresultMap = $resultMap,\nstatus = $status"
    }
}