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


        findViewById<TextView>(R.id.aaa).setOnClickListener {
           PermissionFragment.bindLife(this)
               .addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
               .addPermission(Manifest.permission.CAMERA)
               .callback(object : Callback{
                   override fun onRequestPermission(request: Request?) {

                   }

                   override fun onGrantedPermission(permissions: List<String>) {
                       for (permission in permissions) {
                           Log.d(TAG, "onGrantedPermission: $permission")
                       }
                   }

                   override fun onDeniedPermission(permissions: List<String>) {
                       for (permission in permissions) {
                           Log.d(TAG, "onDeniedPermission: $permission")
                       }
                   }

               })
        }
    }
}