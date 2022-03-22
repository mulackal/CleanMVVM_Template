package com.vip.cleantemplate.presentation.splash

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.vip.cleantemplate.base.BaseActivity
import com.vip.cleantemplate.R
import com.vip.cleantemplate.presentation.main.MainActivity


import org.koin.android.viewmodel.ext.android.viewModel


class SplashActivity : BaseActivity() {

    private val splashViewModel : SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

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


    override fun onDestroy() {
        this.finish()
        super.onDestroy()
    }
}