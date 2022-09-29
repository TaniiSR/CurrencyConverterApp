package com.task.currencyapp.domain


import com.task.currencyapp.data.local.entities.CurrencyEntity
import com.task.currencyapp.data.local.entities.ExchangeRateEntity
import com.task.currencyapp.data.local.localservice.ExchangeRepoDbService
import com.task.currencyapp.data.remote.baseclient.ApiResponse
import com.task.currencyapp.data.remote.responsedtos.CurrencyResponse
import com.task.currencyapp.data.remote.responsedtos.RateResponse
import com.task.currencyapp.data.remote.services.exchangerateservice.ExchangeRateApi
import com.task.currencyapp.domain.base.DataError
import com.task.currencyapp.domain.base.DataResponse
import com.task.currencyapp.domain.datadtos.Currency
import com.task.currencyapp.domain.datadtos.CurrencyDTO
import com.task.currencyapp.domain.datadtos.ExchangeRate
import com.task.currencyapp.domain.datadtos.ExchangeRateDTO
import com.task.currencyapp.domain.interfaces.ICurrencyDataRepo
import com.task.currencyapp.domain.interfaces.IRateDataRepo
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val remoteRepository: ExchangeRateApi,
    private val localRepository: ExchangeRepoDbService
) : IRateDataRepo, ICurrencyDataRepo {
    override suspend fun getAllCurrencies(): DataResponse<CurrencyDTO> {
        val currencies = localRepository.getCurrencies()
        return when {
            currencies?.isNotEmpty() == true -> {
                val currencyList = currencies.map { entity ->
                    Currency(
                        currencyCode = entity.currencyCode,
                        currencyName = entity.currencyName
                    )
                }
                DataResponse.Success(data = CurrencyDTO(currencyList))
            }
            else -> {
                when (val response: ApiResponse<CurrencyResponse> =
                    remoteRepository.getCurrencies()) {
                    is ApiResponse.Success -> {
                        val currenciesList =
                            response.data.currencies?.map { Currency(it.key, it.value) } ?: listOf()
                        localRepository.insertCurrencies(currenciesList.map { currency ->
                            CurrencyEntity(
                                currencyCode = currency.currencyCode,
                                currencyName = currency.currencyName
                            )
                        })
                        DataResponse.Success(CurrencyDTO(currenciesList))
                    }
                    is ApiResponse.Error -> {
                        DataResponse.Error(
                            DataError(
                                response.error.statusCode,
                                response.error.message
                            )
                        )
                    }
                }
            }
        }
    }

    override suspend fun getAllCurrenciesExchangeRates(): DataResponse<ExchangeRateDTO> {
        val rates = localRepository.getExchangeRates()
        return when {
            rates?.isNotEmpty() == true -> {
                val rateList = rates.map { entity ->
                    ExchangeRate(
                        currencyCode = entity.currencyCode,
                        exchangeRate = entity.exchangeRate
                    )
                }
                DataResponse.Success(
                    data = ExchangeRateDTO(
                        base = rates.first().baseCurrency,
                        rateList = rateList
                    )
                )
            }
            else -> {
                when (val response: ApiResponse<RateResponse> =
                    remoteRepository.getLatestExchangeRates()) {
                    is ApiResponse.Success -> {
                        val rateList =
                            response.data.rates?.map { ExchangeRate(it.key, it.value) } ?: listOf()
                        localRepository.insertExchangeRates(rateList.map { rate ->
                            ExchangeRateEntity(
                                currencyCode = rate.currencyCode,
                                exchangeRate = rate.exchangeRate,
                                baseCurrency = response.data.base ?: "USD"
                            )
                        })
                        DataResponse.Success(
                            ExchangeRateDTO(
                                base = response.data.base ?: "USD",
                                rateList = rateList
                            )
                        )
                    }
                    is ApiResponse.Error -> {
                        DataResponse.Error(
                            DataError(
                                response.error.statusCode,
                                response.error.message
                            )
                        )
                    }
                }
            }
        }
    }
}