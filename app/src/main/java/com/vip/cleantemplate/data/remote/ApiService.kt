package com.vip.cleantemplate.data.remote

import com.vip.cleantemplate.domain.model.Players
import com.vip.cleantemplate.domain.model.Teams
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("players")
    suspend fun getUsers(): Response<Players>

    @GET("players")
    suspend fun getPlayers(
        @Query("per_page") per_page: Int?,
        @Query("page") page: Int?,
    ): Players


    @GET("teams")
    suspend fun getTeams(): Response<Teams>

}