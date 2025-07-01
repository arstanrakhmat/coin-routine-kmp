package org.example.project.di

import io.ktor.client.HttpClient
import org.example.project.coins.data.remote.impl.KtorCoinsRemoteDataSource
import org.example.project.coins.domain.GetCoinDetailUseCase
import org.example.project.coins.domain.GetCoinsListUseCase
import org.example.project.coins.domain.api.CoinsRemoteDataSource
import org.example.project.coins.presentation.CoinListViewModel
import org.example.project.core.network.HttpClientFactory
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module

fun initKoin(config: KoinAppDeclaration? = null) =
    startKoin {
        config?.invoke(this)
        modules(
            sharedModule,
            platformModule
        )
    }

expect val platformModule: Module

val sharedModule = module {
    //core
    single<HttpClient> { HttpClientFactory.create(get()) }

    //coins list
    viewModel { CoinListViewModel(get()) }
    singleOf(::GetCoinsListUseCase)
    singleOf(::KtorCoinsRemoteDataSource).bind<CoinsRemoteDataSource>()
    singleOf(::GetCoinDetailUseCase)
}