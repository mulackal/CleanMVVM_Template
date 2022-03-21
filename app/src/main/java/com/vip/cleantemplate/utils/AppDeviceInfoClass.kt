package com.vip.cleantemplate.utils

import android.content.Context
import android.content.pm.PackageManager
import android.provider.Settings
import android.util.Log

import org.json.JSONException
import org.json.JSONObject


/**
 * Created by vipin on 7/3/17.
 */

object AppDeviceInfoClass {


    fun getDeviceInfo(context: Context): String {

        var currentVersion: String? = null
        try {
            currentVersion = context.packageManager.getPackageInfo(context.packageName, 0).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        Log.e("mobile info : ", "" + android.os.Build.MODEL
                + " brand = " + android.os.Build.BRAND +
                " OS version = " + android.os.Build.VERSION.RELEASE +
                " SDK version = " + android.os.Build.VERSION.SDK_INT +
                "version aPP : " + currentVersion)

        val device_model = android.os.Build.MODEL
        val device_brand = android.os.Build.BRAND
        val device_version = android.os.Build.VERSION.RELEASE
        val app_version = currentVersion
        val device_id = Settings.Secure.getString(context.contentResolver,
                Settings.Secure.ANDROID_ID)

        val json = JSONObject()

        try {
            json.put("device_name", device_brand)
            json.put("device_ver", device_version)
            json.put("app_ver", app_version)
            json.put("push_token", device_model)
            json.put("device_id", device_id)
            json.put("platform", "android")

        } catch (e: JSONException) {
            e.printStackTrace()
        }


        return json.toString()
    }

}