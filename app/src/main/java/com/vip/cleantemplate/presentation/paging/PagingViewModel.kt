package com.vip.cleantemplate.presentation.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vip.cleantemplate.base.BaseViewModel
import com.vip.cleantemplate.common.Resource
import com.vip.cleantemplate.domain.model.Player
import com.vip.cleantemplate.domain.model.Teams
import com.vip.cleantemplate.domain.usecase.GetPlayersUseCase
import com.vip.cleantemplate.domain.usecase.GetTeamsUseCase
import com.vip.cleantemplate.utils.NetworkHelper
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

    //Stores the teams data from API
    private val _teams = MutableLiveData<Resource<Teams>>()
    val teams: LiveData<Resource<Teams>> get() = _teams

    //Used to store flow data on parallel execution
    val players: MutableSharedFlow<PagingData<Player>> = MutableSharedFlow()

    // To get Loading state when parallel network calls
    private val _loading = MutableLiveData<Resource<Boolean>>()
    val loadingState: LiveData<Resource<Boolean>> get() = _loading

    init {
        parallelApiCall()
    }
    //using flow
    // This function is not observe when screen
    /*  fun getPagingDataFlow(): Flow<PagingData<Player>> {
          return repository.getPaginatedUsersFlow()
              .cachedIn(viewModelScope)
      }*/

    //This is for observing livedata
    // val pagingData = repository.getPaginatedUsers().cachedIn(viewModelScope)

    //Returns Flow of paging data
    fun getPlayersPagingFlow() = playersUseCase.getPaginatedPlayersByFlow()
        .cachedIn(viewModelScope)

    /** Only used for parallel API call*/
    private suspend fun getAllTeams() = teamsUseCase.getAllTeams()

    /**
     * We can use this method for parallel network call using coroutine
     * */
    private fun parallelApiCall() {
        viewModelScope.launch {
            _loading.postValue(Resource.loading(null))
            val usersApiCall = async { getPlayersPagingFlow() }
            val teamsApiCall = async { getAllTeams() }
            try {
                val response = awaitAll(usersApiCall, teamsApiCall)
                val userResponse = response[0] as Flow<PagingData<Player>>
                val teamsResponse = response[1] as Response<Teams>
                teamsResponse.let {
                    if (it.isSuccessful) {
                        _loading.postValue(Resource.success(true))
                    } else {
                        _loading.postValue(Resource.error(it.errorBody().toString(), null))
                    }
                }
                userResponse.let {
                    players.emitAll(it)
                }
            } catch (e: Exception) {
                _loading.postValue(Resource.error(e.message.toString(), null))
            }
        }
    }

}