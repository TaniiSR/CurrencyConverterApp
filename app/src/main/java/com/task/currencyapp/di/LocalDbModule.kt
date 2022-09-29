package com.task.currencyapp.di

import android.content.Context
import androidx.room.Room
import com.task.currencyapp.data.local.db.ExchangeAppDB
import com.task.currencyapp.data.local.localservice.ExchangeLocalDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDbModule {
    @Provides
    fun provideExchangeDao(appDatabase: ExchangeAppDB): ExchangeLocalDao {
        return appDatabase.exchangeLocalDao()
    }

    @Provides
    @Singleton
    fun provideAppDB(@ApplicationContext appContext: Context): ExchangeAppDB {
        return Room.databaseBuilder(
            appContext,
            ExchangeAppDB::class.java,
            "ExchangeAppDB"
        ).build()
    }

}