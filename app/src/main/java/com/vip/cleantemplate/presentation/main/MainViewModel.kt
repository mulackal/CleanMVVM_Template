package com.vip.cleantemplate.presentation.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vip.cleantemplate.R
import com.vip.cleantemplate.base.BaseViewModel
import com.vip.cleantemplate.common.Resource
import com.vip.cleantemplate.data.preferences.SharedPreferenceUtils
import com.vip.cleantemplate.data.preferences.SharedPreferenceValue
import com.vip.cleantemplate.data.room.UserModel
import com.vip.cleantemplate.domain.exception.ApiError
import com.vip.cleantemplate.domain.exception.traceErrorException
import com.vip.cleantemplate.domain.model.Player
import com.vip.cleantemplate.domain.usecase.GetUserDBUseCase
import com.vip.cleantemplate.domain.usecase.MainUsersUseCase
import com.vip.cleantemplate.utils.NetworkHelper
import com.vip.cleantemplate.utils.Variables
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class MainViewModel(
    private val usersUseCase: MainUsersUseCase,
    private val dbUserUserase: GetUserDBUseCase,
    private val preferences: SharedPreferenceUtils
) : BaseViewModel() {

    private val _users = MutableLiveData<Resource<List<Player>>>()
    val users: LiveData<Resource<List<Player>>> get() = _users

    init {
        //for testing shared preferance working
        preferences.setValue(SharedPreferenceValue.USER_NAME, "USER - VIPIN")

        fetchUsers()

        /** it just for test room db integration**/
        insertandListSampleDb()
    }

    fun fetchUsers() {
        if (Variables.isNetworkConnected) {
        launch {
            _users.postValue(Resource.loading(null))
            usersUseCase.getUsers().let {
                if (it.isSuccessful) {
                    _users.postValue(Resource.success(it.body()?.data))
                } else _users.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
        }else
            setMessage(R.string.no_network)
    }

    fun  insertandListSampleDb() = launch {

        try {

            // Insert data
              var userModel = UserModel(0,"Vipin","Leader")
              dbUserUserase.insertUserData(userModel)

            // Fetch data
               var updatedList :List<UserModel> =  dbUserUserase.getUserData()
                Log.e("ROOMDB","###### ${updatedList[0]!!.userName} ----- ${updatedList.size} ####")

            /** this is used for backgroud thread to main thread data showing**/
            withContext(Dispatchers.Main) {
                if(updatedList.isNotEmpty())
                setMessage(R.string.room_data_added)
            }
        }
        catch (exception : Exception) {
            /** easy Api exception handling**/
            val error: ApiError = traceErrorException(exception)
            setMessage(error?.getErrorMessage())
            Log.d("RoomException", "$exception") }

    }
}