package com.vip.cleantemplate.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userTable")
data class UserModel(
    @PrimaryKey(autoGenerate = true)
    var userID: Int = 0,
    @ColumnInfo(name = "fullName")
    val userName: String,
    @ColumnInfo(name = "Designation")
    val userDesignation: String
)