package com.vip.cleantemplate.domain.usecase

import com.vip.cleantemplate.data.room.DatabaseHelper
import com.vip.cleantemplate.data.room.UserModel


class GetUserDBUseCase(private val databaseHelper: DatabaseHelper) {

    suspend fun insertUserData(data: UserModel) {
        return databaseHelper.insertAll(data)
    }

    suspend fun getUserData() :List<UserModel> {
        return databaseHelper.getAllData()
    }
}