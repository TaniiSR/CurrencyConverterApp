package com.task.currencyapp.data.remote.services.exchangerateservice

import com.task.currencyapp.data.remote.responsedtos.RateResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRateRetroApi {

    @GET(ExchangeRateRepo.URL_GET_CURRENCIES)
    suspend fun fetchCurrencies(): Response<Map<String, String>>

    @GET(ExchangeRateRepo.URL_GET_RATES)
    suspend fun fetchLatestExchangeRates(
        @Query("app_id") appId: String
    ): Response<RateResponse>
}