package com.task.currencyapp.data.local.localservice

import com.task.currencyapp.data.local.entities.CurrencyEntity
import com.task.currencyapp.data.local.entities.ExchangeRateEntity


interface ExchangeRepoDbService {
    suspend fun getCurrencies(): List<CurrencyEntity>?
    suspend fun insertCurrencies(currencies: List<CurrencyEntity>)
    suspend fun getExchangeRates(): List<ExchangeRateEntity>?
    suspend fun insertExchangeRates(rates: List<ExchangeRateEntity>)
}