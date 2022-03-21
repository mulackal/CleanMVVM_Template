package com.vip.cleantemplate.di.module

import com.vip.cleantemplate.presentation.main.MainViewModel
import com.vip.cleantemplate.presentation.paging.PagingViewModel
import com.vip.cleantemplate.presentation.splash.SplashViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel

import org.koin.dsl.module

val viewModelModule = module {

    viewModel { MainViewModel(get(),get(),get()) }
    viewModel { SplashViewModel(androidContext(),get()) }
    viewModel { PagingViewModel(get(),get()) }

}