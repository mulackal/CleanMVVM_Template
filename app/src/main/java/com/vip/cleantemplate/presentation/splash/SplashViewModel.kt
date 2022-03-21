package com.vip.cleantemplate.presentation.splash

import android.content.Context
import android.content.Intent
import android.os.Handler
import com.vip.cleantemplate.base.BaseViewModel
import com.vip.cleantemplate.data.preferences.SharedPreferenceUtils
import com.vip.cleantemplate.data.preferences.SharedPreferenceValue.IS_LOGGED
import com.vip.cleantemplate.presentation.main.MainActivity


class SplashViewModel(
    private val mContext: Context,
    private val preferences: SharedPreferenceUtils
) : BaseViewModel() {

    private val SPLASH_TIME_OUT = 3000
    private var handler: Handler? = null
    private var runnable: Runnable? = null

    init {
        /** write any initial code**/
    }


    fun splashTimeOut() {
        handler = Handler()
        runnable = Runnable { gotoNextScreen() }
        handler!!.postDelayed(runnable!!, SPLASH_TIME_OUT.toLong())
    }

    fun gotoNextScreen() {
        if (preferences.getBoolanValue(IS_LOGGED, false)) {
            gotoHomeScreen()
        } else {
            gotoLoginScreen()
        }
    }

    fun gotoLoginScreen() {
        Intent(mContext, MainActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            mContext.startActivity(it)
        }
    }

    fun gotoHomeScreen() {
        Intent(mContext, MainActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            mContext.startActivity(it)
        }
    }

    fun setRemoveHandler() {
        try {
            handler!!.removeCallbacks(runnable!!)
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }


}