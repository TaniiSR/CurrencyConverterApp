package com.task.currencyapp.data.remote.services.exchangerateservice

import com.task.currencyapp.data.remote.baseclient.ApiResponse
import com.task.currencyapp.data.remote.responsedtos.CurrencyResponse
import com.task.currencyapp.data.remote.responsedtos.RateResponse

interface ExchangeRateApi {
    suspend fun getCurrencies(): ApiResponse<CurrencyResponse>
    suspend fun getLatestExchangeRates(): ApiResponse<RateResponse>
}