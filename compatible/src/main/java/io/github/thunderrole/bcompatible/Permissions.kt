package io.github.thunderrole.bcompatible

import android.Manifest

/**
 *  功能描述：
 *
 *
 * @date 2021/12/14
 */
val NOMARL_PERMISSIONS = arrayListOf<String>().apply {
    add(Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS)
    add(Manifest.permission.ACCESS_NETWORK_STATE)
    add(Manifest.permission.ACCESS_NOTIFICATION_POLICY)
    add(Manifest.permission.ACCESS_WIFI_STATE)
    add(Manifest.permission.BLUETOOTH)
    add(Manifest.permission.BLUETOOTH_ADMIN)
    add(Manifest.permission.BLUETOOTH_PRIVILEGED)
    add(Manifest.permission.CHANGE_NETWORK_STATE)
    add(Manifest.permission.CHANGE_WIFI_MULTICAST_STATE)
    add(Manifest.permission.CHANGE_WIFI_STATE)
    add(Manifest.permission.DISABLE_KEYGUARD)
    add(Manifest.permission.EXPAND_STATUS_BAR)
    add(Manifest.permission.GET_PACKAGE_SIZE)
    add(Manifest.permission.INSTALL_SHORTCUT)
    add(Manifest.permission.INTERNET)
    add(Manifest.permission.KILL_BACKGROUND_PROCESSES)
    add(Manifest.permission.MODIFY_AUDIO_SETTINGS)
    add(Manifest.permission.NFC)
    add(Manifest.permission.READ_SYNC_SETTINGS)
    add(Manifest.permission.READ_SYNC_STATS)
    add(Manifest.permission.RECEIVE_BOOT_COMPLETED)
    add(Manifest.permission.REORDER_TASKS)
    add(Manifest.permission.REQUEST_INSTALL_PACKAGES)
    add(Manifest.permission.SET_ALARM)
    add(Manifest.permission.SET_TIME_ZONE)
    add(Manifest.permission.SET_WALLPAPER)
    add(Manifest.permission.SET_WALLPAPER_HINTS)
    add(Manifest.permission.TRANSMIT_IR)
    add(Manifest.permission.UNINSTALL_SHORTCUT)
    add(Manifest.permission.USE_FINGERPRINT)
    add(Manifest.permission.VIBRATE)
    add(Manifest.permission.WAKE_LOCK)
    add(Manifest.permission.WRITE_SYNC_SETTINGS)
}

val DANGEROUS_PERMISSIONS = hashMapOf<String, ArrayList<String>>().apply {
    put(Manifest.permission_group.CONTACTS, arrayListOf<String>().apply {
        add(Manifest.permission.WRITE_CONTACTS)
        add(Manifest.permission.GET_ACCOUNTS)
        add(Manifest.permission.READ_CONTACTS)
    })
    put(Manifest.permission_group.PHONE, arrayListOf<String>().apply {
        add(Manifest.permission.READ_CALL_LOG)
        add(Manifest.permission.READ_PHONE_STATE)
        add(Manifest.permission.CALL_PHONE)
        add(Manifest.permission.WRITE_CALL_LOG)
        add(Manifest.permission.USE_SIP)
        add(Manifest.permission.PROCESS_OUTGOING_CALLS)
        add(Manifest.permission.ADD_VOICEMAIL)
    })
    put(Manifest.permission_group.CALENDAR, arrayListOf<String>().apply {
        add(Manifest.permission.READ_CALENDAR)
        add(Manifest.permission.WRITE_CALENDAR)
    })
    put(Manifest.permission_group.CAMERA, arrayListOf<String>().apply {
        add(Manifest.permission.CAMERA)
    })
    put(Manifest.permission_group.SENSORS, arrayListOf<String>().apply {
        add(Manifest.permission.BODY_SENSORS)
    })
    put(Manifest.permission_group.LOCATION, arrayListOf<String>().apply {
        add(Manifest.permission.ACCESS_FINE_LOCATION)
        add(Manifest.permission.ACCESS_COARSE_LOCATION)
    })
    put(Manifest.permission_group.STORAGE, arrayListOf<String>().apply {
        add(Manifest.permission.READ_EXTERNAL_STORAGE)
        add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    })
    put(Manifest.permission_group.MICROPHONE, arrayListOf<String>().apply {
        add(Manifest.permission.RECORD_AUDIO)
    })
    put(Manifest.permission_group.SMS, arrayListOf<String>().apply {
        add(Manifest.permission.READ_SMS)
        add(Manifest.permission.RECEIVE_WAP_PUSH)
        add(Manifest.permission.RECEIVE_MMS)
        add(Manifest.permission.RECEIVE_SMS)
        add(Manifest.permission.SEND_SMS)
    })
}
