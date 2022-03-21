package com.vip.cleantemplate.domain.usecase

import com.vip.cleantemplate.domain.model.Teams
import com.vip.cleantemplate.domain.repository.MainRepository
import retrofit2.Response

/**
 * An interactor that calls the actual implementation of [EventListViewModel](which is injected by DI)
 * it handles the response that returns data &
 * contains a list of actions, event steps
 */
class GetTeamsUseCase(private val repository: MainRepository) {
    suspend fun getAllTeams(): Response<Teams> {
        return repository.getTeams()
    }
}