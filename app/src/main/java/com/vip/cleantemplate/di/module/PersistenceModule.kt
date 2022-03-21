
package com.vip.cleantemplate.di.module

import android.app.Application
import androidx.room.Room

import com.vip.cleantemplate.data.room.UserDao
import com.vip.cleantemplate.R
import com.vip.cleantemplate.data.room.AppDatabase
import com.vip.cleantemplate.data.room.DatabaseHelper
import com.vip.cleantemplate.data.room.DatabaseHelperImpl


import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val persistenceModule = module {

  fun provideDataBase(application: Application): AppDatabase {
    return Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        application.getString(R.string.database)
      )
      .allowMainThreadQueries()
      .fallbackToDestructiveMigration()
      .build()
  }

    fun provideDataBaseHelperRepository(
        appDatabase: AppDatabase): DatabaseHelper {
        return DatabaseHelperImpl(appDatabase)
    }


  single { provideDataBase(androidApplication()) }
  single { provideDataBaseHelperRepository(get()) }

}
