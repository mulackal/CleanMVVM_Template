package com.vip.cleantemplate.data.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Query("select * from userTable")
    fun getAllUser(): List<UserModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(addUser: UserModel)

    @Query("UPDATE userTable SET fullName =:updateName, Designation=:updateDesignation where userID=:id")
    fun updateUser(id: Int, updateName: String, updateDesignation: String)

    @Delete
    fun deleteUser(deleteUser: UserModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserList(posters: List<UserModel>)

    @Query("SELECT * FROM userTable WHERE userID = :id_")
    fun getUser(id_: Int): UserModel

    @Query("delete from userTable where userID = :id")
    fun deleteById(id: Int)

}