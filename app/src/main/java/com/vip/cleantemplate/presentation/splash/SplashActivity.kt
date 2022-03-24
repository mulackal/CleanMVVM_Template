package com.vip.cleantemplate.presentation.splash

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.ActivityResult
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.vip.cleantemplate.base.BaseActivity
import com.vip.cleantemplate.R
import com.vip.cleantemplate.common.Constants.UPDATE_REQUEST_CODE
import com.vip.cleantemplate.presentation.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*


import org.koin.android.viewmodel.ext.android.viewModel


class SplashActivity : BaseActivity() {

    private val splashViewModel : SplashViewModel by viewModel()
    var listener : InstallStateUpdatedListener? = null
    private var appUpdateManager : AppUpdateManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        /** looking for new update availability**/
        checkForUpdate()

        splashViewModel.splashPager.observe(this, Observer {
            when (it) {
                is SplashState.MainActivity -> {
                    gotoLoginScreen()
                }
                is SplashState.PagingActivity -> {
                    gotoHomeScreen()
                }
            }
        })
    }

    fun gotoLoginScreen() {
        Intent(this@SplashActivity, MainActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(it)
            finish()
        }
    }

    fun gotoHomeScreen() {
        Intent(this@SplashActivity, MainActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(it)
            finish()
        }
    }


    private fun checkForUpdate() {

        // Create a listener to track request state updates.
         listener = InstallStateUpdatedListener { state ->
            // (Optional) Provide a download progress bar.
            if (state.installStatus() == InstallStatus.DOWNLOADING) {
                val bytesDownloaded = state.bytesDownloaded()
                val totalBytesToDownload = state.totalBytesToDownload()
                // Show update progress bar.

                // After the update is downloaded, show a notification
                // and request user confirmation to restart the app.
                popupSnackbarForCompleteUpdate()
            }
            // Log state or install the update.
        }


        // Creates instance of the manager.
         appUpdateManager = AppUpdateManagerFactory.create(this)

        // Before starting an update, register a listener for updates.
        listener?.let{ appUpdateManager?.registerListener(it) }

        // Returns an intent object that you use to check for an update.
        val appUpdateInfoTask = appUpdateManager?.appUpdateInfo

        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask?.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE // For a flexible update, use AppUpdateType.FLEXIBLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                // Request the update.
                try {
                    appUpdateManager?.startUpdateFlowForResult( // Pass the intent that is returned by 'getAppUpdateInfo()'.
                        appUpdateInfo,  // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                        AppUpdateType.IMMEDIATE,  // The current activity making the update request.
                        this,  // Include a request code to later monitor this update request.
                        UPDATE_REQUEST_CODE
                    )
                } catch (e: IntentSender.SendIntentException) {
                    e.printStackTrace()
                }
            } else {
                splashViewModel.moveToNextScreen()
            }
        }
        appUpdateInfoTask?.addOnFailureListener { list ->
            list.fillInStackTrace()
            debugLogE("Reason for fail-", list.toString())
            splashViewModel.moveToNextScreen()
        }
    }

    // Displays the snackbar notification and call to action.
    fun popupSnackbarForCompleteUpdate() {
        Snackbar.make(
            splash_page,
            "An update has just been downloaded.",
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction("RESTART APP") { appUpdateManager?.completeUpdate() }
            setActionTextColor(resources.getColor(R.color.colorAccent))
            show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UPDATE_REQUEST_CODE) {
            when {
                resultCode != RESULT_OK -> {
                    debugLogE("AppUpdate", "Update flow failed! Result code: $resultCode")
                    // If the update is cancelled or fails,
                    // you can request to start the update again.
                }
                resultCode == RESULT_CANCELED -> {
                    // If the update is cancelled ,
                }
                resultCode == ActivityResult.RESULT_IN_APP_UPDATE_FAILED -> {
                    // If the update is  failed,
                }
                resultCode == RESULT_OK -> {
                    splashViewModel.moveToNextScreen()
                }
            }
        }
    }

    override fun onDestroy() {
        this.finish()
        // When status updates are no longer needed, unregister the listener.
        listener?.let { appUpdateManager?.unregisterListener(it)}
        super.onDestroy()
    }
}