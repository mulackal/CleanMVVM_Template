package com.vip.cleantemplate.presentation.splash

import android.content.Context
import android.content.IntentSender
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vip.cleantemplate.base.BaseViewModel
import com.vip.cleantemplate.data.preferences.SharedPreferenceUtils
import com.vip.cleantemplate.data.preferences.SharedPreferenceValue.IS_LOGGED
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashViewModel(
    private val mContext: Context,
    private val preferences: SharedPreferenceUtils
) : BaseViewModel() {

    val splashPager: LiveData<SplashState>
        get() = splashPageListner
    private val splashPageListner = MutableLiveData<SplashState>()

    init {
       // moveToNextScreen()
    }

    fun moveToNextScreen(){
        viewModelScope.launch {
            delay(2000)
            gotoNextScreen()
        }
    }


    private fun gotoNextScreen() {
        if (preferences.getBoolanValue(IS_LOGGED, false)) {
            splashPageListner.postValue(SplashState.MainActivity)
        } else {
            splashPageListner.postValue(SplashState.PagingActivity)
        }
    }

}

    sealed class SplashState {
        object MainActivity : SplashState()
        object PagingActivity : SplashState()
    }


