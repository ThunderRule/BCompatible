package io.github.thunderrole.bcompatible

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AlertDialog.Builder(this)
            .setMessage("需要神马权限")
            .setNegativeButton("确定"){d,i ->

            }


        val permissionFragment = PermissionFragment.Builder()
            .addPermission(Manifest.permission.CAMERA)
            .addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .addPermission(Manifest.permission.REQUEST_INSTALL_PACKAGES)
            .addPermission(Manifest.permission.WRITE_SETTINGS)
            .setCallback(object : Callback {
                override fun onGrantedPermission(permissions: List<String>) {
                    Log.d(TAG, "onGrantedPermission: $permissions")
                }

                override fun onDeniedPermission(permissions: List<String>) {
                    Log.d(TAG, "onDeniedPermission: $permissions")
                }

                override fun onForeverDeniedPermission(permissions: List<String>) {
                    Log.d(TAG, "onForeverDeniedPermission: $permissions")
                }

            })

        findViewById<TextView>(R.id.aaa).setOnClickListener {
            permissionFragment.start(this)
        }
    }
}