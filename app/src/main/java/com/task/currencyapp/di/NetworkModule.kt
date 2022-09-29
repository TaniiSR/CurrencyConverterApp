package com.task.currencyapp.di


import com.task.currencyapp.BuildConfig
import com.task.currencyapp.data.remote.baseclient.RetroNetwork
import com.task.currencyapp.data.remote.baseclient.remoteconfigs.KeyConfigs
import com.task.currencyapp.data.remote.services.exchangerateservice.ExchangeRateRetroApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    fun providesGitRepoRetroService(): ExchangeRateRetroApi =
        RetroNetwork().createService(ExchangeRateRetroApi::class.java)

    @Provides
    fun providesKeyConfigs(): KeyConfigs =
        KeyConfigs(
            appId = BuildConfig.API_KEY
        )
}