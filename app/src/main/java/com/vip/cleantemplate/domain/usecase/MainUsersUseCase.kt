package com.vip.cleantemplate.domain.usecase

import com.vip.cleantemplate.domain.model.Players
import com.vip.cleantemplate.domain.repository.MainRepository
import retrofit2.Response

class MainUsersUseCase(private val repository: MainRepository) {
    suspend fun getUsers(): Response<Players> {
        return repository.getUsers()
    }
}