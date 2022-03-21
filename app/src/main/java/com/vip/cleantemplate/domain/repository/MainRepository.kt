package com.vip.cleantemplate.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.vip.cleantemplate.domain.model.*
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface MainRepository {
    suspend fun getUsers() : Response<Players>
    suspend fun getTeams() : Response<Teams>
}