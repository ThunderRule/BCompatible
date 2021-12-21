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

        val permissionFragment = PermissionFragment.Builder()
            .addPermission(Manifest.permission.CAMERA)
            .addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .addPermission(Manifest.permission.REQUEST_INSTALL_PACKAGES)
            .setCallback(object : Callback {
                override fun onGrantedPermission(permissions: List<String>) {
                    Log.d(TAG, "onGrantedPermission: $permissions")
                }

                override fun onDeniedPermission(permissions: List<String>) {
                    Log.d(TAG, "onDeniedPermission: $permissions")
                }

            })

        findViewById<TextView>(R.id.aaa).setOnClickListener {
            permissionFragment.start(this)
        }
    }
}