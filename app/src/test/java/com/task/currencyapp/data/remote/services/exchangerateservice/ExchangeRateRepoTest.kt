package com.task.currencyapp.data.remote.services.exchangerateservice

import com.task.currencyapp.base.BaseTestCase
import com.task.currencyapp.data.remote.baseclient.ApiResponse
import com.task.currencyapp.data.remote.baseclient.remoteconfigs.KeyConfigs
import com.task.currencyapp.data.remote.responsedtos.CurrencyResponse
import com.task.currencyapp.data.remote.responsedtos.RateResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class ExchangeRateRepoTest : BaseTestCase() {
    // Subject under test
    lateinit var exchangeRateRepo: ExchangeRateRepo

    // Use a fake Service to be injected into the Repo
    lateinit var service: ExchangeRateRetroApi
    lateinit var keyConfig: KeyConfigs

    @Before
    fun setUp() {
        service = mockk()
        keyConfig = KeyConfigs("")
    }


    @Test
    fun `get currencies api success`() {
        //1- Mock calls
        runTest {
            val response = mockk<Response<Map<String, String>>> {
                every { isSuccessful } returns true
                every { code() } returns 200
                every { body() } returns mockk()
            }
            coEvery {
                service.fetchCurrencies()
            } returns response

            //2-Call
            exchangeRateRepo = ExchangeRateRepo(service, keyConfig)
            val actual:  ApiResponse<CurrencyResponse> = exchangeRateRepo.getCurrencies()
            //3-verify
            Assert.assertEquals(200, (actual as ApiResponse.Success).code )

        }
    }

    @Test
    fun `get currencies api error`() {
        //1- Mock calls
        runTest {
            val response = mockk<Response<Map<String, String>>> {
                every { isSuccessful } returns false
                every { code() } returns 401
                every { message() } returns ""
            }
            coEvery {
                service.fetchCurrencies()
            } returns response

            //2-Call
            exchangeRateRepo = ExchangeRateRepo(service, keyConfig)
            val actual: ApiResponse<CurrencyResponse> = exchangeRateRepo.getCurrencies()
            //3-verify
            Assert.assertEquals(401, (actual as ApiResponse.Error).error.statusCode)

        }
    }

    @Test
    fun `get rates api success`() {
        //1- Mock calls
        runTest {
            val response = mockk<Response<RateResponse>> {
                every { isSuccessful } returns true
                every { body() } returns mockk()
                every { code() } returns 200
            }
            coEvery {
                service.fetchLatestExchangeRates(keyConfig.appId)
            } returns response

            //2-Call
            exchangeRateRepo = ExchangeRateRepo(service, keyConfig)
            val actual: ApiResponse<RateResponse> = exchangeRateRepo.getLatestExchangeRates()
            //3-verify
            coVerify {
                service.fetchLatestExchangeRates(keyConfig.appId)
            }
            Assert.assertEquals(200, (actual as ApiResponse.Success).code)

        }
    }

    @Test
    fun `get rates api Error`() {
        //1- Mock calls
        runTest {
            val response = mockk<Response<RateResponse>> {
                every { isSuccessful } returns false
                every { code() } returns 401
            }
            coEvery {
                service.fetchLatestExchangeRates(keyConfig.appId)
            } returns response

            //2-Call
            exchangeRateRepo = ExchangeRateRepo(service, keyConfig)
            val actual: ApiResponse<RateResponse> = exchangeRateRepo.getLatestExchangeRates()
            //3-verify
            coVerify {
                service.fetchLatestExchangeRates(keyConfig.appId)
            }
            Assert.assertEquals(401, (actual as ApiResponse.Error).error.statusCode)

        }
    }
}