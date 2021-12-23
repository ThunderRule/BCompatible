package io.github.thunderrole.bcompatible

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class MainActivity : AppCompatActivity(){

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val compatible = BCompatible.Builder()
            .addPermission(Manifest.permission.CAMERA)
            .addInterceptor(DialogInterceptor()).build()


        findViewById<TextView>(R.id.aaa).setOnClickListener {
            compatible.start(this){

            }
        }
    }

}