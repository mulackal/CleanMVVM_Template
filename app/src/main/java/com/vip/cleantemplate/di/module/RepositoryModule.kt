package com.vip.cleantemplate.di.module

import com.vip.cleantemplate.data.repository.MainRepositoryImpl
import com.vip.cleantemplate.data.repository.PagingRepositoryImpl
import com.vip.cleantemplate.domain.repository.MainRepository
import org.koin.dsl.module

val repoModule = module {
    single { MainRepositoryImpl(get()) }
    single { PagingRepositoryImpl(get()) }
}