package io.github.thunderrole.bcompatible

/**
 *  功能描述：
 *
 *
 * @date 2021/12/13
 */
class Response(
    val status: Int,
    val request: Request?,
    val grantedResults: List<String>,
    val deniedResults: List<String>
) {

    fun status() = status

    fun requset() = request

    fun grantedResults() = grantedResults

    fun deniedResults() = deniedResults

    class Builder {
        internal var status = 0
        internal var request: Request? = null
        internal var grantedResults = mutableListOf<String>()
        internal var deniedResults = mutableListOf<String>()

        fun status(status: Int) = apply {
            this.status = status
        }

        fun request(request: Request?) = apply {
            this.request = request
        }

        fun grantedResult(grantResult: String) = apply {
            this.grantedResults += grantResult
        }

        fun grantedResults(grantResult: List<String>) = apply {
            this.grantedResults += grantResult
        }

        fun deniedResults(deniedResults: List<String>) = apply {
            this.deniedResults += deniedResults
        }

        fun build(): Response = Response(this.status,this.request, this.grantedResults, this.deniedResults)
    }

}