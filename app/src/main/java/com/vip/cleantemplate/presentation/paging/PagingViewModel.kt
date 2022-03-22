package com.vip.cleantemplate.presentation.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vip.cleantemplate.R
import com.vip.cleantemplate.base.BaseViewModel
import com.vip.cleantemplate.common.Resource
import com.vip.cleantemplate.domain.model.Player
import com.vip.cleantemplate.domain.model.Teams
import com.vip.cleantemplate.domain.usecase.GetPlayersUseCase
import com.vip.cleantemplate.domain.usecase.GetTeamsUseCase
import com.vip.cleantemplate.utils.NetworkHelper
import com.vip.cleantemplate.utils.Variables
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import retrofit2.Response

class PagingViewModel(
    private val playersUseCase: GetPlayersUseCase,
    private val teamsUseCase: GetTeamsUseCase,
) : BaseViewModel() {


    //Used to store flow data on parallel execution
    val players: MutableSharedFlow<PagingData<Player>> = MutableSharedFlow()

    // To get Loading state when parallel network calls
    private val _userData = MutableLiveData<Resource<Boolean>>()
    val userDataState: LiveData<Resource<Boolean>> get() = _userData

    init {
        parallelApiCall()
         }


    /**
     * We can use this method for parallel network call using coroutine
     * */
    private fun parallelApiCall() {

        if (Variables.isNetworkConnected) {

        viewModelScope.launch {

            _userData.postValue(Resource.loading(null))

            val usersApiCall = async { getPlayersPagingFlow() }
            val teamsApiCall = async { getAllTeams() }
            try {
                val response = awaitAll(usersApiCall, teamsApiCall)
                val userResponse = response[0] as Flow<PagingData<Player>>
                val teamsResponse = response[1] as Response<Teams>

                teamsResponse.let {
                    if (it.isSuccessful) {
                        _userData.postValue(Resource.success(teamsResponse.isSuccessful))
                    } else {
                        _userData.postValue(Resource.error(it.errorBody().toString(), null))
                    }
                }

                userResponse.let {
                    players.emitAll(it)
                }

            } catch (e: Exception) {
                _userData.postValue(Resource.error(e.message.toString(), null))
            }
        }
    }else
        setMessage(R.string.no_network)

    }

    //Returns Flow of paging data
    fun getPlayersPagingFlow() = playersUseCase.getPaginatedPlayersByFlow()
        .cachedIn(viewModelScope)

    /** Only used for parallel API call testing*/
    private suspend fun getAllTeams() = teamsUseCase.getAllTeams()

}