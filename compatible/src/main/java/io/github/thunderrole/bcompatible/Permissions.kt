package io.github.thunderrole.bcompatible

import android.Manifest

/**
 *  功能描述：
 *
 *
 * @date 2021/12/14
 */
val NOMARL_PERMISSIONS = hashMapOf<String, String>().apply {
    put("", Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS)
    put("", Manifest.permission.ACCESS_NETWORK_STATE)
    put("", Manifest.permission.ACCESS_NOTIFICATION_POLICY)
    put("", Manifest.permission.ACCESS_WIFI_STATE)
    put("", Manifest.permission.BLUETOOTH)
    put("", Manifest.permission.BLUETOOTH_ADMIN)
    put("", Manifest.permission.BLUETOOTH_PRIVILEGED)
    put("", Manifest.permission.CHANGE_NETWORK_STATE)
    put("", Manifest.permission.CHANGE_WIFI_MULTICAST_STATE)
    put("", Manifest.permission.CHANGE_WIFI_STATE)
    put("", Manifest.permission.DISABLE_KEYGUARD)
    put("", Manifest.permission.EXPAND_STATUS_BAR)
    put("", Manifest.permission.GET_PACKAGE_SIZE)
    put("", Manifest.permission.INSTALL_SHORTCUT)
    put("", Manifest.permission.INTERNET)
    put("", Manifest.permission.KILL_BACKGROUND_PROCESSES)
    put("", Manifest.permission.MODIFY_AUDIO_SETTINGS)
    put("", Manifest.permission.NFC)
    put("", Manifest.permission.READ_SYNC_SETTINGS)
    put("", Manifest.permission.READ_SYNC_STATS)
    put("", Manifest.permission.RECEIVE_BOOT_COMPLETED)
    put("", Manifest.permission.REORDER_TASKS)
    put("", Manifest.permission.REQUEST_INSTALL_PACKAGES)
    put("", Manifest.permission.SET_ALARM)
    put("", Manifest.permission.SET_TIME_ZONE)
    put("", Manifest.permission.SET_WALLPAPER)
    put("", Manifest.permission.SET_WALLPAPER_HINTS)
    put("", Manifest.permission.TRANSMIT_IR)
    put("", Manifest.permission.UNINSTALL_SHORTCUT)
    put("", Manifest.permission.USE_FINGERPRINT)
    put("", Manifest.permission.VIBRATE)
    put("", Manifest.permission.WAKE_LOCK)
    put("", Manifest.permission.WRITE_SYNC_SETTINGS)
}

val DANGEROUS_PERMISSIONS = hashMapOf<String, HashMap<String, String>>().apply {
    put(Manifest.permission_group.CONTACTS, hashMapOf<String, String>().apply {
        put("", Manifest.permission.WRITE_CONTACTS)
        put("", Manifest.permission.GET_ACCOUNTS)
        put("", Manifest.permission.READ_CONTACTS)
    })
    put(Manifest.permission_group.PHONE, hashMapOf<String, String>().apply {
        put("", Manifest.permission.READ_CALL_LOG)
        put("", Manifest.permission.READ_PHONE_STATE)
        put("", Manifest.permission.CALL_PHONE)
        put("", Manifest.permission.WRITE_CALL_LOG)
        put("", Manifest.permission.USE_SIP)
        put("", Manifest.permission.PROCESS_OUTGOING_CALLS)
        put("", Manifest.permission.ADD_VOICEMAIL)
    })
    put(Manifest.permission_group.CALENDAR, hashMapOf<String, String>().apply {
        put("", Manifest.permission.READ_CALENDAR)
        put("", Manifest.permission.WRITE_CALENDAR)
    })
    put(Manifest.permission_group.CAMERA, hashMapOf<String, String>().apply {
        put("", Manifest.permission.CAMERA)
    })
    put(Manifest.permission_group.SENSORS, hashMapOf<String, String>().apply {
        put("", Manifest.permission.BODY_SENSORS)
    })
    put(Manifest.permission_group.LOCATION, hashMapOf<String, String>().apply {
        put("",Manifest.permission.ACCESS_FINE_LOCATION)
        put("",Manifest.permission.ACCESS_COARSE_LOCATION)
    })
    put(Manifest.permission_group.STORAGE, hashMapOf<String, String>().apply {
        put("", Manifest.permission.READ_EXTERNAL_STORAGE)
        put("", Manifest.permission.WRITE_EXTERNAL_STORAGE)
    })
    put(Manifest.permission_group.MICROPHONE, hashMapOf<String, String>().apply {
        put("", Manifest.permission.RECORD_AUDIO)
    })
    put(Manifest.permission_group.SMS, hashMapOf<String, String>().apply {
        put("", Manifest.permission.READ_SMS)
        put("", Manifest.permission.RECEIVE_WAP_PUSH)
        put("", Manifest.permission.RECEIVE_MMS)
        put("", Manifest.permission.RECEIVE_SMS)
        put("", Manifest.permission.SEND_SMS)
    })
}
