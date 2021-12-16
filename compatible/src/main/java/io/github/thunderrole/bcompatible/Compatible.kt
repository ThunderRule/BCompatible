package io.github.thunderrole.bcompatible

import android.content.Context
import androidx.fragment.app.Fragment
import io.github.thunderrole.bcompatible.interceptor.Interceptor

/**
 *  功能描述：
 *
 *
 * @date 2021/12/13
 */
class Compatible internal constructor(builder:Builder):Call.Factory{

    @get:JvmName("interceptors") val interceptors:List<Interceptor> = builder.interceptors.toImmutableList()



    override fun newCall(request: Request): Call = RealCall(this,request)

    class Builder constructor() {
        internal val interceptors: MutableList<Interceptor> = mutableListOf()

        fun addInterceptor(interceptor: Interceptor) = apply {
            interceptors += interceptor
        }

        fun build():Compatible{
            return Compatible(this)
        }
    }

}