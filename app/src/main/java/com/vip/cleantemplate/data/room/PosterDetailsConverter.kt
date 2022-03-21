
package com.vip.cleantemplate.data.room

import androidx.room.TypeConverter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class IntegerListConverter {

  @TypeConverter
  fun fromString(value: String): List<UserModel>? {
    val listType = object : TypeToken<List<UserModel>>() {}.type
    return Gson().fromJson<List<UserModel>>(value, listType)
  }

  @TypeConverter
  fun fromList(list: List<UserModel>): String {
    val gson = Gson()
    return gson.toJson(list)
  }
}
