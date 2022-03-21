package com.vip.cleantemplate.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*


abstract class BaseViewModel : ViewModel() {

    fun launch(blockScope: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(block = blockScope)

    fun async(blockScope: suspend CoroutineScope.() -> Unit) =
        viewModelScope.async(block = blockScope)


    open val isProgressLoading = MutableLiveData<Boolean>()
    open fun setIsProgressBarVisible(isLoad: Boolean) {
        isProgressLoading.value = isLoad }

    open var showMessage = MutableLiveData<Any>()
    open fun setMessage(message: Any) {
        showMessage.value = message }


    var job: CompletableJob? = null
    fun cancelJobs(){
        if(job!=null)
            job?.cancel()
    }

}
