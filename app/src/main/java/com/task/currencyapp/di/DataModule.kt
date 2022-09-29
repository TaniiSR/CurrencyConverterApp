package com.task.currencyapp.di


import com.task.currencyapp.data.local.localservice.ExchangeRepoDbService
import com.task.currencyapp.data.local.localservice.ExchangeRepoLocal
import com.task.currencyapp.data.remote.services.exchangerateservice.ExchangeRateApi
import com.task.currencyapp.data.remote.services.exchangerateservice.ExchangeRateRepo
import com.task.currencyapp.domain.DataRepository
import com.task.currencyapp.domain.interfaces.ICurrencyDataRepo
import com.task.currencyapp.domain.interfaces.IRateDataRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun provideExchangeRemoteRepository(exchangeRepositoryRemote: ExchangeRateRepo): ExchangeRateApi

    @Binds
    @Singleton
    abstract fun provideExchangeLocalRepository(exchangeRepoLocal: ExchangeRepoLocal): ExchangeRepoDbService

    @Binds
    @Singleton
    abstract fun provideRateRepository(dataRepository: DataRepository): IRateDataRepo

    @Binds
    @Singleton
    abstract fun provideCurrencyRepository(dataRepository: DataRepository): ICurrencyDataRepo
}