package com.vip.cleantemplate.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.vip.cleantemplate.R


class PermissionUtility(
    var mContext: Context,
    var mPermissionRequested: Int,
    var mAppCompatActivity: AppCompatActivity
) {
    val contact_permission = 1
    val location_permission_setPin = 20
    val location_permission_checkPin = 21
    val storage_permission = 3
    val phone_call_permission = 4

    val packageName: String
        get() = mAppCompatActivity.packageName

    fun checkAppPermissions(): Boolean {
        when (mPermissionRequested) {
            contact_permission -> if (ContextCompat.checkSelfPermission(
                    mContext,
                    Manifest.permission.READ_CONTACTS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        mAppCompatActivity,
                        Manifest.permission.READ_CONTACTS
                    )
                ) {
                    showDialog(contact_permission)
                } else {
                    ActivityCompat.requestPermissions(
                        mAppCompatActivity,
                        arrayOf(Manifest.permission.READ_CONTACTS),
                        contact_permission
                    )
                    //                        showRetryDialog(contact_permission);
                }
                return false
            }
            location_permission_setPin -> if (ContextCompat.checkSelfPermission(
                    mContext,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        mAppCompatActivity,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                ) {
                    showDialog(location_permission_setPin)
                } else {
                    ActivityCompat.requestPermissions(
                        mAppCompatActivity,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        location_permission_setPin
                    )
                    //                        showRetryDialog(location_permission_setPin);
                }
                return false
            }
            location_permission_checkPin -> if (ContextCompat.checkSelfPermission(
                    mContext,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        mAppCompatActivity,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                ) {
                    showDialog(location_permission_checkPin)
                } else {
                    ActivityCompat.requestPermissions(
                        mAppCompatActivity,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        location_permission_checkPin
                    )
                    //                        showRetryDialog(location_permission_checkPin);
                }
                return false
            }
            storage_permission -> if (ContextCompat.checkSelfPermission(
                    mContext,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        mAppCompatActivity,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                ) {

                    ActivityCompat.requestPermissions(
                        mAppCompatActivity,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        storage_permission
                    )


                } else {
                    ActivityCompat.requestPermissions(
                        mAppCompatActivity,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        storage_permission
                    )
                    //                        showRetryDialog(storage_permission);
                }
                return false
            }
            phone_call_permission -> if (ContextCompat.checkSelfPermission(
                    mContext,
                    Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        mAppCompatActivity,
                        Manifest.permission.CALL_PHONE
                    )
                ) {
                    showDialog(phone_call_permission)
                } else {
                    ActivityCompat.requestPermissions(
                        mAppCompatActivity,
                        arrayOf(Manifest.permission.CALL_PHONE),
                        phone_call_permission
                    )
                    //                        showRetryDialog(phone_call_permission);
                }
                return false
            }
        }

        return true
    }

    fun showDialog(permission_requested: Int) {
        var message: String? = null
        when (permission_requested) {
            contact_permission -> message = mContext.resources.getString(R.string.title_contacts_permission_dialog)
            location_permission_setPin -> message =
                mContext.resources.getString(R.string.title_location_permission_dialog)
            location_permission_checkPin -> message =
                mContext.resources.getString(R.string.title_location_permission_dialog)
            phone_call_permission -> message = mContext.resources.getString(R.string.title_phone_call_permission_dialog)
            storage_permission -> message = mContext.resources.getString(R.string.title_storage_permission_dialog)
        }
        val builder: AlertDialog.Builder
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            builder = AlertDialog.Builder(mAppCompatActivity)
        } else {
            builder = AlertDialog.Builder(mAppCompatActivity)
        }
        builder.setMessage(message)
        builder.setCancelable(false)
        builder.setPositiveButton(
            "OK"
        ) { dialog, id ->
            when (permission_requested) {
                contact_permission -> ActivityCompat.requestPermissions(
                    mAppCompatActivity,
                    arrayOf(Manifest.permission.READ_CONTACTS),
                    contact_permission
                )
                location_permission_setPin -> ActivityCompat.requestPermissions(
                    mAppCompatActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    location_permission_setPin
                )
                location_permission_checkPin -> ActivityCompat.requestPermissions(
                    mAppCompatActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    location_permission_checkPin
                )
                phone_call_permission -> ActivityCompat.requestPermissions(
                    mAppCompatActivity,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    phone_call_permission
                )
                storage_permission -> ActivityCompat.requestPermissions(
                    mAppCompatActivity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    storage_permission
                )
            }
        }
        val permission_rationalle = builder.create()
        permission_rationalle.show()
    }

    fun showRetryDialog(permission_requested: Int) {
        var message: String? = null
        when (permission_requested) {
            contact_permission -> message = mContext.resources.getString(R.string.title_contacts_permission_dialog)
            location_permission_setPin -> message =
                mContext.resources.getString(R.string.title_location_permission_dialog)
            location_permission_checkPin -> message =
                mContext.resources.getString(R.string.title_location_permission_dialog)
            phone_call_permission -> message = mContext.resources.getString(R.string.title_phone_call_permission_dialog)
            storage_permission -> message = mContext.resources.getString(R.string.title_storage_permission_dialog)
        }
        val builder: AlertDialog.Builder
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            builder = AlertDialog.Builder(mAppCompatActivity)
        } else {
            builder = AlertDialog.Builder(mAppCompatActivity)
        }
        builder.setMessage(message)
        builder.setCancelable(true)
        builder.setNegativeButton(
            "Cancel"
        ) { dialog, id -> dialog.cancel() }
        builder.setPositiveButton(
            "Re-Enable"
        ) { dialog, id ->
            val myAppSettings = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:$packageName")
            )
            myAppSettings.addCategory(Intent.CATEGORY_DEFAULT)
            myAppSettings.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            mContext.startActivity(myAppSettings)
        }
        val permission_rationalle = builder.create()
        permission_rationalle.show()
    }


}
