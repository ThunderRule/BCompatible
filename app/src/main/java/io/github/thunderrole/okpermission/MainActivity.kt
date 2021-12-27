package io.github.thunderrole.okpermission

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val TAG = "MainActivity"
    private var mText:TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val compatible = OkPermission.Builder()
            .addPermission(Manifest.permission.REQUEST_INSTALL_PACKAGES)
            .addPermission(Manifest.permission.BLUETOOTH_SCAN)
            .addPermission(Manifest.permission.BLUETOOTH_CONNECT)
            .addPermission(Manifest.permission.BLUETOOTH_ADVERTISE)
            .addPermission(Manifest.permission.MANAGE_EXTERNAL_STORAGE)
            .addPermission(Manifest.permission.SYSTEM_ALERT_WINDOW)
            .addPermission(Manifest.permission.WRITE_SETTINGS)
            .addPermission(Manifest.permission.PACKAGE_USAGE_STATS)
            .addInterceptor(DialogInterceptor())
            .addInterceptor(PhotoInterceptor())
            .build()


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
        val many = findViewById<TextView>(R.id.bt_many)

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
        many.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        val okBuilder = OkPermission.Builder()
        when (v?.id) {
            R.id.bt_camera -> {
                okBuilder.addPermission(Manifest.permission.CAMERA)
            }
            R.id.bt_install -> {
                okBuilder.addPermission(Manifest.permission.REQUEST_INSTALL_PACKAGES)
            }
            R.id.bt_alert -> {
                okBuilder.addPermission(Manifest.permission.SYSTEM_ALERT_WINDOW)
            }
            R.id.bt_calendar -> {
                okBuilder.addPermission(Manifest.permission.READ_CALENDAR)
                okBuilder.addPermission(Manifest.permission.WRITE_CALENDAR)
            }
            R.id.bt_read_write -> {
                okBuilder.addPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                okBuilder.addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            R.id.bt_record -> {
                okBuilder.addPermission(Manifest.permission.RECORD_AUDIO)
            }
            R.id.bt_phone_state -> {
                okBuilder.addPermission(Manifest.permission.READ_PHONE_STATE)
            }
            R.id.bt_write_setting -> {
                okBuilder.addPermission(Manifest.permission.WRITE_SETTINGS)
            }
            R.id.bt_sms -> {
                okBuilder.addPermission(Manifest.permission.SEND_SMS)
                okBuilder.addPermission(Manifest.permission.RECEIVE_SMS)
            }
            R.id.bt_bluetooth -> {
                okBuilder.addPermission(Manifest.permission.BLUETOOTH_CONNECT)
            }
            R.id.bt_many -> {
                okBuilder.addPermission(Manifest.permission.CAMERA)
                okBuilder.addPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                okBuilder.addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                okBuilder.addPermission(Manifest.permission.REQUEST_INSTALL_PACKAGES)
            }
        }

        okBuilder.build().start(this){
            mText?.text = it.toString()
        }
    }

}