package io.github.thunderrole.okpermission

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings

/**
 *  Function：
 *
 *
 * @date 2021/12/21
 */
object IntentCreater {
    internal fun createSmartPermissionIntent(context: Context, permissions: List<String>): Intent {
        if (permissions.isNullOrEmpty() && PermissionUtils.containsSpecial(permissions)) {
            return createApplicationIntent(context)
        }

        if (PermissionUtils.isHightAndroid11() && permissions.contains(Manifest.permission.MANAGE_EXTERNAL_STORAGE)) {
            return createStorageIntent(context)
        }

        if (permissions.size == 1){
            val permission = permissions[0]
            return when (permission) {
                Manifest.permission.MANAGE_EXTERNAL_STORAGE -> createStorageIntent(context)
                Manifest.permission.REQUEST_INSTALL_PACKAGES -> createInstallIntent(context)
                Manifest.permission.SYSTEM_ALERT_WINDOW -> createWindowIntent(context)
                Manifest.permission.WRITE_SETTINGS -> createSettingIntent(context)
                //TODO lack notification
                Manifest.permission.PACKAGE_USAGE_STATS -> createPackageIntent(context)
                else -> createApplicationIntent(context)
            }
        }

        return createApplicationIntent(context)
    }

    /**
     * More than the android8,get permission to install unknown apps
     */
    internal fun createInstallIntent(context: Context):Intent{
        var intent:Intent? = null
        if (PermissionUtils.isHightAndroid8()){
            intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES)
            intent.data = getPackageNameUri(context)
        }
        if (intent == null || !isExistActivity(context, intent)){
            intent = createApplicationIntent(context)
        }
        return intent
    }

    /**
     * More than the android6,get permission to overlay
     */
    internal fun createWindowIntent(context: Context):Intent{
        var intent:Intent? = null
        if (PermissionUtils.isHightAndroid6()){
            intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            intent.data = getPackageNameUri(context)
        }
        if (intent == null || !isExistActivity(context,intent)){
            intent = createApplicationIntent(context)
        }
        return intent
    }

    /**
     * More than the android8,get permission to Notification
     */
    internal fun createNotificationIntent(context: Context):Intent{
        var intent:Intent? = null
        if (PermissionUtils.isHightAndroid8()){
            intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
            intent.putExtra(Settings.EXTRA_APP_PACKAGE,context.packageName)
        }
        if (intent == null || !isExistActivity(context,intent)){
            intent = createApplicationIntent(context)
        }
        return intent
    }

    /**
     * More than the android6,get permission to system setting
     */
    internal fun createSettingIntent(context: Context):Intent{
        var intent:Intent? = null
        if (PermissionUtils.isHightAndroid6()){
            intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
            intent.data = getPackageNameUri(context)
        }
        if (intent == null || !isExistActivity(context,intent)){
            intent = createApplicationIntent(context)
        }
        return intent
    }

    /**
     * More than the android11,get access permission to all files
     */
    internal fun createStorageIntent(context: Context): Intent {
        var intent: Intent? = null
        if (PermissionUtils.isHightAndroid11()) {
            intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
            intent.data = getPackageNameUri(context)
        }
        if (intent == null || !isExistActivity(context, intent)){
            intent = createApplicationIntent(context)
        }
        return intent
    }

    /**
     * More than android5,get permission to read package
     */
    internal fun createPackageIntent(context: Context):Intent{
        var intent:Intent? = null
        if (PermissionUtils.isHightAndroid5()){
            intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            if (PermissionUtils.isHightAndroid10()){
                intent.data = getPackageNameUri(context)
            }
        }
        if (intent == null || !isExistActivity(context,intent)){
            intent = createApplicationIntent(context)
        }
        return intent
    }

    internal fun createApplicationIntent(context: Context): Intent {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = getPackageNameUri(context)
        return intent
    }

    private fun isExistActivity(context: Context,intent: Intent) =
        context.packageManager.queryIntentActivities(intent,PackageManager.MATCH_DEFAULT_ONLY).isNotEmpty()

    private fun getPackageNameUri(context: Context) = Uri.parse("package:${context.packageName}")
}