package io.github.thunderrole.bcompatible

/**
 *  功能描述：
 *
 *
 * @date 2021/12/13
 */
class Response(
    val request: Request?,
    val grantResults: List<Int>
) {

    fun requset() = request

    fun grantResults() = grantResults

    class Builder {
        internal var request: Request? = null
        internal var grantResults = mutableListOf<Int>()

        fun request(request: Request) = apply {
            this.request = request
        }

        fun grantResult(grantResult: Int) = apply {
            this.grantResults += grantResult
        }

        fun build(): Response = Response(this.request, this.grantResults)
    }

}