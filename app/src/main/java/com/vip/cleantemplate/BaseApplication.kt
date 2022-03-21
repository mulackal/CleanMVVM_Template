package com.vip.cleantemplate

import android.app.Activity
import android.app.Application
import android.os.Build
import android.os.Bundle
import com.vip.cleantemplate.di.module.*
import com.vip.cleantemplate.utils.NetworkMonitor
import com.vip.cleantemplate.utils.customviews.FontsOverride
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class BaseApplication : Application(), Application.ActivityLifecycleCallbacks {

    var mCurrencyActivity: Activity? = null

    override fun onCreate() {
        super.onCreate()

        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/NunitoSans-Regular.ttf")

        startKoin {
            androidContext(this@BaseApplication)
            androidLogger(Level.ERROR)
            modules(
                listOf(
                    appModule,
                    repoModule,
                    sessionModule,
                    viewModelModule,
                    persistenceModule,
                    useCaseModule
                )
            )
        }

        registerActivityLifecycleCallbacks(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NetworkMonitor(this).startNetworkCallback()
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        NetworkMonitor(this).stopNetworkCallback()
    }


    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

    override fun onActivityStarted(activity: Activity) {}

    override fun onActivityResumed(activity: Activity) {  mCurrencyActivity = activity }

    override fun onActivityPaused(activity: Activity) {  mCurrencyActivity = activity }

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {}
}
