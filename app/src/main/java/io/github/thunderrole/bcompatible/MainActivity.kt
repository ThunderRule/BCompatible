package io.github.thunderrole.bcompatible

import android.Manifest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val permissionHelper = Compatible.Builder()
            .build()

        val request = Request.Builder()
            .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .body("这是个啥？")
            .build()

        permissionHelper.newCall(request).asyn(object : Callback{
            override fun onSuccess(call: Call, response: Response) {
                val request1 = response.request
                Log.d(TAG, "onSuccess: ${request1?.body}")
            }

            override fun onFailure(call: Call, e: PermissionException) {

            }

        })
    }
}