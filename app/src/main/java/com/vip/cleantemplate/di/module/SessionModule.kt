package com.vip.cleantemplate.di.module

import com.vip.cleantemplate.data.preferences.SharedPreferenceUtils

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val sessionModule = module {

        single { SharedPreferenceUtils(androidApplication()) }
}