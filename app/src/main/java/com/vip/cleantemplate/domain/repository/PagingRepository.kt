package com.vip.cleantemplate.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.vip.cleantemplate.domain.model.*
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface PagingRepository {
    fun getPaginatedPlayersLiveData() : LiveData<PagingData<Player>>
    fun getPaginatedPlayersFlow() : Flow<PagingData<Player>>
}