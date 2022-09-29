package com.task.currencyapp.ui.main

import com.task.currencyapp.base.BaseTestCase
import com.task.currencyapp.base.getOrAwaitValue
import com.task.currencyapp.domain.DataRepository
import com.task.currencyapp.domain.base.DataResponse
import com.task.currencyapp.domain.datadtos.CurrencyDTO
import com.task.currencyapp.domain.datadtos.ExchangeRate
import com.task.currencyapp.domain.datadtos.ExchangeRateDTO
import com.task.currencyapp.domain.interfaces.ICurrencyDataRepo
import com.task.currencyapp.domain.interfaces.IRateDataRepo
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MainVMTest : BaseTestCase() {
    // Subject under test
    lateinit var viewModel: MainVM

    // Use a fake UseCase to be injected into the viewModel
    lateinit var rateRepo: IRateDataRepo
    lateinit var currencyRepo: ICurrencyDataRepo

    @Before
    fun setUp() {
        rateRepo = mockk<DataRepository>()
        currencyRepo = mockk<DataRepository>()
    }

    @Test
    fun `get currencies and rates data success`() {
        //1- Mock calls
        runTest {
            val currencyResponse = mockk<DataResponse.Success<CurrencyDTO>> {
                every { data } returns mockk {
                    every { currencies } returns listOf(mockk(), mockk())
                }
            }

            val rateResponse = mockk<DataResponse.Success<ExchangeRateDTO>> {
                every { data } returns mockk {
                    every { base } returns "USD"
                    every { rateList } returns listOf(mockk(), mockk())
                }
            }
            coEvery {
                currencyRepo.getAllCurrencies()
            } returns currencyResponse
            coEvery {
                rateRepo.getAllCurrenciesExchangeRates()
            } returns rateResponse

            //2-Call
            viewModel = MainVM(
                currencyRepo = currencyRepo,
                rateRepo = rateRepo
            )
            viewModel.dataRequestCurrenciesAndRates()

            //3-verify
            Assert.assertEquals(
                true,
                viewModel.exchangeRates.getOrAwaitValue().rateList.isNotEmpty()
            )
            Assert.assertEquals(true, viewModel.supportedCurrencies.getOrAwaitValue().isNotEmpty())
            coVerify { currencyRepo.getAllCurrencies() }
            coVerify { rateRepo.getAllCurrenciesExchangeRates() }
        }
    }


    @Test
    fun `get currencies data success rates data error`() {
        //1- Mock calls
        runTest {
            val currencyResponse = mockk<DataResponse.Success<CurrencyDTO>> {
                every { data } returns mockk {
                    every { currencies } returns listOf(mockk(), mockk())
                }
            }

            val rateResponse = mockk<DataResponse.Error> {
                every { error } returns mockk {
                    every { message } returns "Error"
                    every { code } returns 401
                }
            }
            coEvery {
                currencyRepo.getAllCurrencies()
            } returns currencyResponse
            coEvery {
                rateRepo.getAllCurrenciesExchangeRates()
            } returns rateResponse

            //2-Call
            viewModel = MainVM(
                currencyRepo = currencyRepo,
                rateRepo = rateRepo
            )
            viewModel.dataRequestCurrenciesAndRates()
            //3-verify
            Assert.assertEquals(true, viewModel.exchangeRates.getOrAwaitValue() == null)
            Assert.assertEquals(true, viewModel.supportedCurrencies.getOrAwaitValue().isNotEmpty())
            coVerify { currencyRepo.getAllCurrencies() }
            coVerify { rateRepo.getAllCurrenciesExchangeRates() }
        }
    }

    @Test
    fun `get currencies data error and rates data success`() {
        //1- Mock calls
        runTest {
            val currencyResponse = mockk<DataResponse.Error> {
                every { error } returns mockk {
                    every { message } returns "Error"
                    every { code } returns 401
                }
            }

            val rateResponse = mockk<DataResponse.Success<ExchangeRateDTO>> {
                every { data } returns mockk {
                    every { base } returns "USD"
                    every { rateList } returns listOf(mockk(), mockk())
                }
            }
            coEvery {
                currencyRepo.getAllCurrencies()
            } returns currencyResponse
            coEvery {
                rateRepo.getAllCurrenciesExchangeRates()
            } returns rateResponse

            //2-Call
            viewModel = MainVM(
                currencyRepo = currencyRepo,
                rateRepo = rateRepo
            )
            viewModel.dataRequestCurrenciesAndRates()

            //3-verify
            Assert.assertEquals(
                true,
                viewModel.exchangeRates.getOrAwaitValue().rateList.isNotEmpty()
            )
            Assert.assertEquals(true, viewModel.supportedCurrencies.getOrAwaitValue() == null)
            coVerify { currencyRepo.getAllCurrencies() }
            coVerify { rateRepo.getAllCurrenciesExchangeRates() }
        }
    }

    @Test
    fun `get currencies and rates data error`() {
        //1- Mock calls
        runTest {
            val currencyResponse = mockk<DataResponse.Error> {
                every { error } returns mockk {
                    every { message } returns "Error"
                    every { code } returns 401
                }
            }

            val rateResponse = mockk<DataResponse.Error> {
                every { error } returns mockk {
                    every { message } returns "Error"
                    every { code } returns 401
                }
            }

            coEvery {
                currencyRepo.getAllCurrencies()
            } returns currencyResponse
            coEvery {
                rateRepo.getAllCurrenciesExchangeRates()
            } returns rateResponse

            //2-Call
            viewModel = MainVM(
                currencyRepo = currencyRepo,
                rateRepo = rateRepo
            )
            viewModel.dataRequestCurrenciesAndRates()

            //3-verify
            Assert.assertEquals(
                true,
                viewModel.exchangeRates.getOrAwaitValue() == null
            )
            Assert.assertEquals(true, viewModel.supportedCurrencies.getOrAwaitValue() == null)
            coVerify { currencyRepo.getAllCurrencies() }
            coVerify { rateRepo.getAllCurrenciesExchangeRates() }
        }
    }

    @Test
    fun `get converted rate USD to Destination Currency success`() {
        val listOfActualRates = listOf<ExchangeRate>(
            ExchangeRate("PKR", 220.0),
            ExchangeRate("AED", 3.67),
            ExchangeRate("USD", 1.0),
        )
        viewModel = MainVM(mockk(), mockk())
        val list = viewModel.getExchangeRateListForSelectedCurrency(
            list = listOfActualRates,
            amount = 2.0,
            sourceCurrency = "USD",
            baseCurrency = "USD"
        )
        Assert.assertEquals(440.0, list.find { rate -> rate.currencyCode == "PKR" }?.exchangeRate)
        Assert.assertEquals(7.34, list.find { rate -> rate.currencyCode == "AED" }?.exchangeRate)
    }

    @Test
    fun `get converted rate PKR to Destination Currency With Base currency USD success`() {
        val listOfActualRates = listOf<ExchangeRate>(
            ExchangeRate("PKR", 220.0),
            ExchangeRate("AED", 3.67),
            ExchangeRate("USD", 1.0),
        )
        viewModel = MainVM(mockk(), mockk())
        val list = viewModel.getExchangeRateListForSelectedCurrency(
            list = listOfActualRates,
            amount = 2.0,
            sourceCurrency = "PKR",
            baseCurrency = "USD"
        )
        Assert.assertEquals(0.009, list.find { rate -> rate.currencyCode == "USD" }?.exchangeRate)
        Assert.assertEquals(0.033, list.find { rate -> rate.currencyCode == "AED" }?.exchangeRate)
    }

    @Test
    fun `get converted rate AED to Destination Currency With Base currency USD success`() {
        val listOfActualRates = listOf<ExchangeRate>(
            ExchangeRate("PKR", 220.0),
            ExchangeRate("AED", 3.67),
            ExchangeRate("USD", 1.0),
        )
        viewModel = MainVM(mockk(), mockk())
        val list = viewModel.getExchangeRateListForSelectedCurrency(
            list = listOfActualRates,
            amount = 2.0,
            sourceCurrency = "AED",
            baseCurrency = "USD"
        )
        Assert.assertEquals(0.545, list.find { rate -> rate.currencyCode == "USD" }?.exchangeRate)
        Assert.assertEquals(119.891, list.find { rate -> rate.currencyCode == "PKR" }?.exchangeRate)
    }

    /*
input : "aaaabbcccab...."
output : "a4b2c3a1b1...."
*/

    fun compress(text: String): String {
        var prev: Char? = null
        var count = 0
        var result = ""
        text.forEachIndexed { index, char ->
            if (prev != null && prev != char) {
                result = result + prev + count
                count = 1
                prev = char
            } else {
                prev = char
                count++
            }

            if (index == text.length-1)
                result = result + prev + count

        }
        return result
    }

     @Test
     fun testCompress() {
         val input = "aaaabbcccab"
         Assert.assertEquals("a4b2c3a1b1", compress(input))
//    https://codeshare.io/9O3AE4
     }

    @After
    fun cleanUp() {
        clearAllMocks()
    }
}