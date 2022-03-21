package com.vip.cleantemplate.di.module

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vip.cleantemplate.BuildConfig
import com.vip.cleantemplate.data.remote.ApiService
import com.vip.cleantemplate.data.repository.MainRepositoryImpl
import com.vip.cleantemplate.data.repository.PagingRepositoryImpl
import com.vip.cleantemplate.domain.repository.MainRepository
import com.vip.cleantemplate.domain.repository.PagingRepository
import com.vip.cleantemplate.utils.NetworkHelper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val appModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get(), BuildConfig.BASE_URL) }
    single { provideApiService(get()) }

    single { provideMainRepo(get()) }
    single { providePagingRepo(get()) }
    single { provideNetworkHelper(androidContext()) }
    // Provide Gson
    single<Gson> { GsonBuilder().create() }

}

private fun provideNetworkHelper(context: Context) = NetworkHelper(context)

private fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

    OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
} else {
    OkHttpClient
        .Builder()
        .build()
}


private fun provideRetrofit(
    okHttpClient: OkHttpClient,
    BASE_URL: String
): Retrofit =
    Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

private fun provideApiService(retrofit: Retrofit): ApiService =
    retrofit.create(ApiService::class.java)

private fun provideMainRepo(apiService: ApiService): MainRepository =
    MainRepositoryImpl(apiService)

private fun providePagingRepo(apiService: ApiService): PagingRepository =
    PagingRepositoryImpl(apiService)