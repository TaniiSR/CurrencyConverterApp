package com.task.currencyapp.data.local.localservice

import com.task.currencyapp.base.BaseTestCase
import com.task.currencyapp.data.local.entities.CurrencyEntity
import com.task.currencyapp.data.local.entities.ExchangeRateEntity
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
@ExperimentalCoroutinesApi
class ExchangeRepositoryLocalTest : BaseTestCase() {
    // Subject under test
    lateinit var exchangeRepositoryLocal: ExchangeRepoLocal

    // Use a fake Service to be injected into the Repo
    lateinit var localDao: ExchangeLocalDao

    @Before
    fun setUp() {
        localDao = mockk()
    }

    @Test
    fun `get currencies success`() {
        //1- Mock calls
        runTest {
            val response = mockk<ArrayList<CurrencyEntity>>()

            coEvery {
                localDao.getAllCurrencies()
            } returns response

            //2-Call
            exchangeRepositoryLocal = ExchangeRepoLocal(localDao)
            val actual: List<CurrencyEntity>? = exchangeRepositoryLocal.getCurrencies()
            //3-verify
            Assert.assertEquals(true, actual != null)

        }
    }

    @Test
    fun `get currencies Error`() {
        //1- Mock calls
        runTest {
            val response = null

            coEvery {
                localDao.getAllCurrencies()
            } returns response

            //2-Call
            exchangeRepositoryLocal = ExchangeRepoLocal(localDao)
            val actual: List<CurrencyEntity>? = exchangeRepositoryLocal.getCurrencies()
            //3-verify
            Assert.assertEquals(true, actual == null)

        }
    }

    @Test
    fun `get rate Error`() {
        //1- Mock calls
        runTest {
            val response = null
            coEvery {
                localDao.getAllRates()
            } returns response

            //2-Call
            exchangeRepositoryLocal = ExchangeRepoLocal(localDao)
            val actual: List<ExchangeRateEntity>? = exchangeRepositoryLocal.getExchangeRates()
            //3-verify
            Assert.assertEquals(true, actual == null)

        }
    }


    @Test
    fun `get rates success`() {
        //1- Mock calls
        runTest {
            val response = mockk<ArrayList<ExchangeRateEntity>>()

            coEvery {
                localDao.getAllRates()
            } returns response

            //2-Call
            exchangeRepositoryLocal = ExchangeRepoLocal(localDao)
            val actual: List<ExchangeRateEntity>? = exchangeRepositoryLocal.getExchangeRates()
            //3-verify
            Assert.assertEquals(true, actual != null)

        }
    }


    @After
    fun cleanUp() {
        clearAllMocks()
    }
}
