package com.vip.cleantemplate.di.module

import com.vip.cleantemplate.domain.usecase.GetPlayersUseCase
import com.vip.cleantemplate.domain.usecase.GetTeamsUseCase
import com.vip.cleantemplate.domain.usecase.GetUserDBUseCase
import com.vip.cleantemplate.domain.usecase.MainUsersUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetPlayersUseCase(get()) }
    single { GetTeamsUseCase(get()) }
    single { MainUsersUseCase(get()) }
    single { GetUserDBUseCase(get()) }
}

