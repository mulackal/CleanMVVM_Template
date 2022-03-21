package com.vip.cleantemplate.data.repository

import com.vip.cleantemplate.data.remote.ApiService
import com.vip.cleantemplate.domain.model.Teams
import com.vip.cleantemplate.domain.repository.MainRepository
import retrofit2.Response

class MainRepositoryImpl(
    private val apiHelper: ApiService
) : MainRepository {

    override suspend fun getUsers() = apiHelper.getUsers()
    override suspend fun getTeams(): Response<Teams> = apiHelper.getTeams()


}