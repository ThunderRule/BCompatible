package io.github.thunderrole.okpermission

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.TextView
import io.github.thunderrole.okpermission.interceptor.GoBack

class MainActivity : AppCompatActivity(), View.OnClickListener, GoBack {

    private val TAG = "MainActivity"
    private var mText: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mText = findViewById<TextView>(R.id.aaa)
        val camera = findViewById<TextView>(R.id.bt_camera)
        val install = findViewById<TextView>(R.id.bt_install)
        val alert = findViewById<TextView>(R.id.bt_alert)
        val calendar = findViewById<TextView>(R.id.bt_calendar)
        val readwrite = findViewById<TextView>(R.id.bt_read_write)
        val record = findViewById<TextView>(R.id.bt_record)
        val phoneState = findViewById<TextView>(R.id.bt_phone_state)
        val writeSetting = findViewById<TextView>(R.id.bt_write_setting)
        val sms = findViewById<TextView>(R.id.bt_sms)
        val bluetooth = findViewById<TextView>(R.id.bt_bluetooth)
        val btPackage = findViewById<TextView>(R.id.bt_package)
        val btLocation = findViewById<TextView>(R.id.bt_location)
        val many = findViewById<TextView>(R.id.bt_many)
        val btDialog = findViewById<TextView>(R.id.bt_dialog)

        camera.setOnClickListener(this)
        install.setOnClickListener(this)
        alert.setOnClickListener(this)
        calendar.setOnClickListener(this)
        readwrite.setOnClickListener(this)
        record.setOnClickListener(this)
        phoneState.setOnClickListener(this)
        writeSetting.setOnClickListener(this)
        sms.setOnClickListener(this)
        bluetooth.setOnClickListener(this)
        btPackage.setOnClickListener(this)
        btLocation.setOnClickListener(this)
        many.setOnClickListener(this)
        btDialog.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        val okBuilder = OkPermission.Builder()
        when (v?.id) {
            R.id.bt_camera -> {
                okBuilder.addPermission(Manifest.permission.CAMERA)
            }
            R.id.bt_calendar -> {
                okBuilder.addPermission(Manifest.permission.READ_CALENDAR)
                    .addPermission(Manifest.permission.WRITE_CALENDAR)
            }
            R.id.bt_read_write -> {
                okBuilder.addPermission(Manifest.permission.MANAGE_EXTERNAL_STORAGE)
                    .addPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            R.id.bt_package -> {
                okBuilder.addPermission(Manifest.permission.PACKAGE_USAGE_STATS)
            }
            R.id.bt_record -> {
                okBuilder.addPermission(Manifest.permission.RECORD_AUDIO)
            }
            R.id.bt_phone_state -> {
                okBuilder.addPermission(Manifest.permission.READ_PHONE_STATE)
                    .addPermission(Manifest.permission.CALL_PHONE)
                    .addPermission(Manifest.permission.READ_CALL_LOG)
                    .addPermission(Manifest.permission.WRITE_CALL_LOG)
                    .addPermission(Manifest.permission.ADD_VOICEMAIL)
                    .addPermission(Manifest.permission.USE_SIP)
            }
            R.id.bt_install -> {
                okBuilder.addPermission(Manifest.permission.REQUEST_INSTALL_PACKAGES)
            }
            R.id.bt_alert -> {
                okBuilder.addPermission(Manifest.permission.SYSTEM_ALERT_WINDOW)
            }
            R.id.bt_write_setting -> {
                okBuilder.addPermission(Manifest.permission.WRITE_SETTINGS)
            }
            R.id.bt_sms -> {
                okBuilder.addPermission(Manifest.permission.SEND_SMS)
                    .addPermission(Manifest.permission.RECEIVE_SMS)
                    .addPermission(Manifest.permission.READ_SMS)
                    .addPermission(Manifest.permission.RECEIVE_WAP_PUSH)
                    .addPermission(Manifest.permission.RECEIVE_MMS)
            }
            R.id.bt_bluetooth -> {
                okBuilder.addPermission(Manifest.permission.BLUETOOTH_CONNECT)
            }
            R.id.bt_location -> {
                okBuilder.addPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                    .addPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            R.id.bt_many -> {
                okBuilder.addPermission(Manifest.permission.CAMERA)
                    .addPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .addPermission(Manifest.permission.REQUEST_INSTALL_PACKAGES)
            }
            R.id.bt_dialog -> {
                okBuilder.addPermission(Manifest.permission.CAMERA)
                    .addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .addInterceptor(DialogInterceptor())
            }
        }

        okBuilder.build().start(this,this)
    }

    override fun onPermissionBack(response: Response?) {
        response?.let {
            mText?.text = it.toString()
        }
    }

}