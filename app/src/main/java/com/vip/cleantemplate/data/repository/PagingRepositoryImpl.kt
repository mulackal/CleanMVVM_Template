package com.vip.cleantemplate.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.vip.cleantemplate.data.datasource.PlayersPagingSource
import com.vip.cleantemplate.data.remote.ApiService
import com.vip.cleantemplate.domain.model.Player
import com.vip.cleantemplate.domain.repository.PagingRepository
import kotlinx.coroutines.flow.Flow


class PagingRepositoryImpl(
    private val apiHelper: ApiService
) : PagingRepository {


    // paging data using Livedata
    override fun getPaginatedPlayersLiveData() =
        Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PlayersPagingSource(apiHelper) }
        ).liveData

    //Paging data using flow
    override fun getPaginatedPlayersFlow(): Flow<PagingData<Player>> =
        Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { PlayersPagingSource(apiHelper) }
        ).flow

    companion object {
        const val NETWORK_PAGE_SIZE = 6
    }

}