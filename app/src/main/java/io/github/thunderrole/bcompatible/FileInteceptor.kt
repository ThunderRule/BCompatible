package io.github.thunderrole.bcompatible

import android.Manifest
import android.util.Log
import io.github.thunderrole.bcompatible.interceptor.Interceptor

/**
 *  功能描述：
 *
 *
 * @date 2021/12/15
 */
class FileInteceptor : Interceptor {
    private val TAG = "FileInteceptor"
    override suspend fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (request.permissions.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Log.d(TAG, "intercept: 为了能够让你选择其他二维码，我们必须需要请求读取你手机文件的权限，否则你将我发选择其他二维码进行继续操作。")
        }
        val proceed = chain.proceed(request)
        if (proceed.deniedResults.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Log.d(TAG, "intercept1: 为了能够让你选择其他二维码，我们必须需要请求读取你手机文件的权限，否则你将我发选择其他二维码进行继续操作。")
        }

        return proceed
    }
}