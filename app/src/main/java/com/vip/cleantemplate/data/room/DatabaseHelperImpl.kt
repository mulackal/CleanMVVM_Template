package com.vip.cleantemplate.data.room


class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {

    override suspend fun getAllData(): List<UserModel> = appDatabase.userDao().getAllUser()
    override suspend fun insertAll(data: UserModel) = appDatabase.userDao().addUser(data)

}