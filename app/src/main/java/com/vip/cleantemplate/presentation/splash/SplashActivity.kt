package com.vip.cleantemplate.presentation.splash

import android.os.Bundle
import com.vip.cleantemplate.base.BaseActivity
import com.vip.cleantemplate.R


import org.koin.android.viewmodel.ext.android.viewModel


class SplashActivity : BaseActivity() {

    private val splashViewModel : SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splashViewModel!!.splashTimeOut()

    }


    override fun onDestroy() {
        splashViewModel!!.setRemoveHandler()
        this.finish()
        super.onDestroy()
    }
}