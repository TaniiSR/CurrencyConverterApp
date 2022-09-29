package com.task.currencyapp.domain.interfaces

import com.task.currencyapp.domain.base.DataResponse
import com.task.currencyapp.domain.datadtos.ExchangeRateDTO

interface IRateDataRepo {
    suspend fun getAllCurrenciesExchangeRates(): DataResponse<ExchangeRateDTO>
}