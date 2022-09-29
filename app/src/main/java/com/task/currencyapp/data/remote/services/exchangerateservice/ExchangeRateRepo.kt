package com.task.currencyapp.data.remote.services.exchangerateservice

import com.task.currencyapp.data.remote.baseclient.ApiResponse
import com.task.currencyapp.data.remote.baseclient.BaseRepository
import com.task.currencyapp.data.remote.baseclient.erros.ApiError
import com.task.currencyapp.data.remote.baseclient.remoteconfigs.KeyConfigs
import com.task.currencyapp.data.remote.responsedtos.CurrencyResponse
import com.task.currencyapp.data.remote.responsedtos.RateResponse
import javax.inject.Inject

class ExchangeRateRepo @Inject constructor(
    private val service: ExchangeRateRetroApi,
    private val keyConfigs: KeyConfigs
) : BaseRepository(),
    ExchangeRateApi {


    override suspend fun getCurrencies(): ApiResponse<CurrencyResponse> {
        val response = executeSafelyRaw(call = {
            service.fetchCurrencies()
        })
        return if (response?.isSuccessful == true) {
            ApiResponse.Success(response.code(), CurrencyResponse(currencies = response.body()))
        } else {
            ApiResponse.Error(
                error = ApiError(
                    statusCode = response?.code() ?: -1,
                    message = response?.message() ?: ""
                )
            )
        }
    }

    override suspend fun getLatestExchangeRates(): ApiResponse<RateResponse> {
        return executeSafely(call = {
            service.fetchLatestExchangeRates(
                appId = keyConfigs.appId
            )
        })
    }

    companion object {
        const val URL_GET_CURRENCIES = "api/currencies.json"
        const val URL_GET_RATES = "api/latest.json"
    }
}