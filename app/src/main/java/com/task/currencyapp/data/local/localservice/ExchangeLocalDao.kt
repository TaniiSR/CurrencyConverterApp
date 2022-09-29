package com.task.currencyapp.data.local.localservice

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.task.currencyapp.data.local.entities.CurrencyEntity
import com.task.currencyapp.data.local.entities.ExchangeRateEntity

@Dao
interface ExchangeLocalDao {
    @Query("SELECT * FROM currency")
    suspend fun getAllCurrencies(): List<CurrencyEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCurrencies(currencies: List<CurrencyEntity>)

    @Query("SELECT * FROM exchange_rate")
    suspend fun getAllRates(): List<ExchangeRateEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRates(exchangeRates: List<ExchangeRateEntity>)
}