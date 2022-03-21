package com.vip.cleantemplate.domain.usecase

import androidx.paging.PagingData
import com.vip.cleantemplate.domain.model.Player
import com.vip.cleantemplate.domain.repository.MainRepository
import com.vip.cleantemplate.domain.repository.PagingRepository
import kotlinx.coroutines.flow.Flow

class GetPlayersUseCase(private val repository: PagingRepository) {

    fun getPaginatedPlayersByFlow(): Flow<PagingData<Player>> {
        return repository.getPaginatedPlayersFlow()
    }
}