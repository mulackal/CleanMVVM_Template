package com.vip.cleantemplate.data.room

import com.vip.cleantemplate.domain.model.Players

interface DatabaseHelper {
    suspend fun getAllData(): List<UserModel>
    suspend fun insertAll(data: UserModel)
}