package io.github.thunderrole.bcompatible

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val compatible = Compatible.Builder()
            .build()

        val request = Request.Builder()
            .setPermission(Manifest.permission.CAMERA)
            .setContext(this)
            .build()

        findViewById<TextView>(R.id.aaa).setOnClickListener {
           val response = compatible.newCall(request).execute()
            Log.d(TAG, "onCreate: $response")
        }
    }
}