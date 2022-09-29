package com.task.currencyapp.domain

import com.task.currencyapp.base.BaseTestCase
import com.task.currencyapp.data.local.entities.CurrencyEntity
import com.task.currencyapp.data.local.entities.ExchangeRateEntity
import com.task.currencyapp.data.local.localservice.ExchangeRepoDbService
import com.task.currencyapp.data.remote.baseclient.ApiResponse
import com.task.currencyapp.data.remote.responsedtos.CurrencyResponse
import com.task.currencyapp.data.remote.responsedtos.RateResponse
import com.task.currencyapp.data.remote.services.exchangerateservice.ExchangeRateApi
import com.task.currencyapp.domain.base.DataResponse
import com.task.currencyapp.domain.datadtos.Currency
import com.task.currencyapp.domain.datadtos.CurrencyDTO
import com.task.currencyapp.domain.datadtos.ExchangeRate
import com.task.currencyapp.domain.datadtos.ExchangeRateDTO
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DataRepositoryTest : BaseTestCase() {
    // Subject under test
    lateinit var dataRepo: DataRepository

    // Use a fake Service to be injected into the Repo
    lateinit var localSource: ExchangeRepoDbService
    lateinit var remoteSource: ExchangeRateApi

    @Before
    fun setUp() {
        localSource = mockk()
        remoteSource = mockk()
    }

    @Test
    fun `get currencies api success`() {
        //1- Mock calls
        runTest {
            val apiResponse = mockk<ApiResponse.Success<CurrencyResponse>> {
                every { data } returns mockk {
                    every { currencies } returns hashMapOf<String, String>().apply {
                        this["PKR"] = "Pakistan"
                    }
                }
                every { code } returns 200
            }
            val currenciesList =
                apiResponse.data.currencies?.map { Currency(it.key, it.value) } ?: listOf()
            coEvery {
                remoteSource.getCurrencies()
            } returns apiResponse
            coEvery {
                localSource.insertCurrencies(
                    currenciesList.map { currency ->
                        CurrencyEntity(
                            currencyCode = currency.currencyCode,
                            currencyName = currency.currencyName
                        )
                    }
                )
            } returns Unit

            coEvery {
                localSource.getCurrencies()
            } returns null

            //2-Call
            dataRepo = DataRepository(remoteSource, localSource)
            val actual: DataResponse<CurrencyDTO> = dataRepo.getAllCurrencies()
            //3-verify
            Assert.assertEquals(
                1,
                (actual as DataResponse.Success<CurrencyDTO>).data.currencies.size
            )
            coVerify { remoteSource.getCurrencies() }
            coVerify { localSource.getCurrencies() }
            coVerify {
                localSource.insertCurrencies(
                    currenciesList.map { currency ->
                        CurrencyEntity(
                            currencyCode = currency.currencyCode,
                            currencyName = currency.currencyName
                        )
                    }
                )
            }
        }
    }

    @Test
    fun `get currencies Local success`() {
        //1- Mock calls
        runTest {
            val response = arrayListOf(CurrencyEntity("PKR", "Pakistan"))
            coEvery {
                localSource.getCurrencies()
            } returns response

            //2-Call
            dataRepo = DataRepository(remoteSource, localSource)
            val actual: DataResponse<CurrencyDTO> = dataRepo.getAllCurrencies()
            //3-verify
            Assert.assertEquals(
                1,
                (actual as DataResponse.Success<CurrencyDTO>).data.currencies.size
            )
            coVerify { localSource.getCurrencies() }
        }
    }

    @Test
    fun `get currencies api Error`() {
        //1- Mock calls
        runTest {
            val apiResponse = mockk<ApiResponse.Error> {
                every { error } returns mockk {
                    every { message } returns "Error"
                    every { statusCode } returns 401
                    every { actualCode } returns "401"
                }
            }

            coEvery {
                remoteSource.getCurrencies()
            } returns apiResponse

            coEvery {
                localSource.getCurrencies()
            } returns null

            //2-Call
            dataRepo = DataRepository(remoteSource, localSource)
            val actual: DataResponse<CurrencyDTO> = dataRepo.getAllCurrencies()
            //3-verify
            Assert.assertEquals(
                401,
                (actual as DataResponse.Error).error.code
            )
            coVerify { remoteSource.getCurrencies() }
            coVerify { localSource.getCurrencies() }

        }
    }

    @Test
    fun `get rates api Error`() {
        //1- Mock calls
        runTest {
            val apiResponse = mockk<ApiResponse.Error> {
                every { error } returns mockk {
                    every { message } returns "Error"
                    every { statusCode } returns 401
                    every { actualCode } returns "401"
                }
            }

            coEvery {
                remoteSource.getLatestExchangeRates()
            } returns apiResponse

            coEvery {
                localSource.getExchangeRates()
            } returns null

            //2-Call
            dataRepo = DataRepository(remoteSource, localSource)
            val actual: DataResponse<ExchangeRateDTO> = dataRepo.getAllCurrenciesExchangeRates()
            //3-verify
            Assert.assertEquals(
                401,
                (actual as DataResponse.Error).error.code
            )
            coVerify { remoteSource.getLatestExchangeRates() }
            coVerify { localSource.getExchangeRates() }

        }
    }

    @Test
    fun `get rates Local success`() {
        //1- Mock calls
        runTest {
            val response = arrayListOf(ExchangeRateEntity("PKR", 0.00, "USD"))
            coEvery {
                localSource.getExchangeRates()
            } returns response

            //2-Call
            dataRepo = DataRepository(remoteSource, localSource)
            val actual: DataResponse<ExchangeRateDTO> = dataRepo.getAllCurrenciesExchangeRates()
            //3-verify
            Assert.assertEquals(
                1,
                (actual as DataResponse.Success<ExchangeRateDTO>).data.rateList.size
            )
            coVerify { localSource.getExchangeRates() }
        }
    }


    @Test
    fun `get rates api success`() {
        //1- Mock calls
        runTest {
            val apiResponse = mockk<ApiResponse.Success<RateResponse>> {
                every { data } returns mockk(relaxed = true)
                every { code } returns 200
            }
            coEvery {
                remoteSource.getLatestExchangeRates()
            } returns apiResponse

            val rateList =
                apiResponse.data.rates?.map { ExchangeRate(it.key, it.value) } ?: listOf()

            coEvery {
                localSource.insertExchangeRates(
                    rateList.map { exchangeRate ->
                        ExchangeRateEntity(
                            currencyCode = exchangeRate.currencyCode,
                            exchangeRate = exchangeRate.exchangeRate,
                            baseCurrency = apiResponse.data.base ?: "USD"
                        )
                    }
                )
            } returns Unit

            coEvery {
                localSource.getExchangeRates()
            } returns null

            //2-Call
            dataRepo = DataRepository(remoteSource, localSource)
            val actual: DataResponse<ExchangeRateDTO> = dataRepo.getAllCurrenciesExchangeRates()
            //3-verify
            Assert.assertEquals(
                true,
                (actual as DataResponse.Success<ExchangeRateDTO>).data.rateList.isEmpty()
            )
            coVerify { remoteSource.getLatestExchangeRates() }
            coVerify { localSource.getExchangeRates() }
            coVerify {
                localSource.insertExchangeRates(
                    rateList.map { exchangeRate ->
                        ExchangeRateEntity(
                            currencyCode = exchangeRate.currencyCode,
                            exchangeRate = exchangeRate.exchangeRate,
                            baseCurrency = apiResponse.data.base ?: "USD"
                        )
                    }
                )
            }
        }
    }


    @After
    fun cleanUp() {
        clearAllMocks()
    }
}