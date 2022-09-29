package com.task.currencyapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.task.currencyapp.domain.base.DataResponse
import com.task.currencyapp.domain.datadtos.Currency
import com.task.currencyapp.domain.datadtos.CurrencyDTO
import com.task.currencyapp.domain.datadtos.ExchangeRate
import com.task.currencyapp.domain.datadtos.ExchangeRateDTO
import com.task.currencyapp.domain.interfaces.ICurrencyDataRepo
import com.task.currencyapp.domain.interfaces.IRateDataRepo
import com.task.currencyapp.ui.main.adapter.RateListAdapter
import com.task.currencyapp.utils.base.BaseViewModel
import com.task.currencyapp.utils.base.sealed.UIEvent
import com.task.currencyapp.utils.extensions.roundToThreeDeci
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    private val rateRepo: IRateDataRepo,
    private val currencyRepo: ICurrencyDataRepo
) : BaseViewModel(), IMain {

    private val _supportedCurrencies: MutableLiveData<List<Currency>> = MutableLiveData()
    override val supportedCurrencies: LiveData<List<Currency>> = _supportedCurrencies

    private val _exchangeRates: MutableLiveData<ExchangeRateDTO> = MutableLiveData()
    override val exchangeRates: LiveData<ExchangeRateDTO> = _exchangeRates

    override var selectedCurrency: Currency? = null
    override val rateListAdapter: RateListAdapter = RateListAdapter(mutableListOf())

    private val _uiCurrencyState: MutableLiveData<UIEvent> = MutableLiveData()
    override val uiCurrencyState: LiveData<UIEvent> = _uiCurrencyState

    private val _uiRateState: MutableLiveData<UIEvent> = MutableLiveData()
    override val uiRateState: LiveData<UIEvent> = _uiRateState

    private fun fetchCurrenciesAndExchangeRates(
        responses: (
            DataResponse<CurrencyDTO>,
            DataResponse<ExchangeRateDTO>
        ) -> Unit
    ) {
        launch {
            val supportedCurrenciesResponseDeferred = launchAsync {
                currencyRepo.getAllCurrencies()
            }
            val exchangeRateResponseDeferred = launchAsync {
                rateRepo.getAllCurrenciesExchangeRates()
            }
            withContext(Dispatchers.Main) {
                responses(
                    supportedCurrenciesResponseDeferred.await(),
                    exchangeRateResponseDeferred.await()
                )
            }
        }
    }

    override fun dataRequestCurrenciesAndRates() {
        fetchCurrenciesAndExchangeRates { currenciesData, ratesData ->
            _uiRateState.value = UIEvent.Loading
            _uiCurrencyState.value = UIEvent.Loading
            handleCurrencyData(currenciesData)
            handleRatesData(ratesData)
        }
    }

    override fun getExchangeRateListForSelectedCurrency(
        list: List<ExchangeRate>?,
        amount: Double?,
        sourceCurrency: String?,
        baseCurrency: String
    ): List<ExchangeRate> {
        return if (isCurrencyAndAmountValid(amount, sourceCurrency)) {
            list?.map { rate ->
                ExchangeRate(
                    exchangeRate = convertExchangeRate(
                        source = list.find { exchangeRate -> exchangeRate.currencyCode == sourceCurrency },
                        destination = rate,
                        amount = amount,
                        isBaseSame = sourceCurrency == baseCurrency
                    ),
                    currencyCode = rate.currencyCode
                )
            } ?: emptyList()
        } else {
            emptyList()
        }
    }

    private fun convertExchangeRate(
        source: ExchangeRate?,
        destination: ExchangeRate,
        amount: Double?,
        isBaseSame: Boolean
    ): Double =
        if (isBaseSame)
            covertWithSameBase(
                amount = amount ?: 0.0,
                destinationRate = destination.exchangeRate
            )
        else
            covertWithNotSameBase(
                destinationRate = destination.exchangeRate,
                sourceRate = source?.exchangeRate ?: 0.0,
                amount = amount ?: 0.0
            )


    private fun covertWithNotSameBase(
        destinationRate: Double,
        sourceRate: Double,
        amount: Double
    ): Double =
        (destinationRate.div(sourceRate) * amount).roundToThreeDeci()


    private fun covertWithSameBase(
        destinationRate: Double,
        amount: Double
    ): Double = (destinationRate * amount).roundToThreeDeci()


    private fun handleRatesData(ratesData: DataResponse<ExchangeRateDTO>) {
        when (ratesData) {
            is DataResponse.Success -> {
                _exchangeRates.value = ratesData.data
                _uiRateState.value = UIEvent.Success
            }
            is DataResponse.Error -> {
                _uiRateState.value = UIEvent.Error(ratesData.error.message)
                _exchangeRates.value = null

            }
        }
    }

    private fun handleCurrencyData(currenciesData: DataResponse<CurrencyDTO>) {
        when (currenciesData) {
            is DataResponse.Success -> {
                _supportedCurrencies.value = currenciesData.data.currencies
                _uiCurrencyState.value = UIEvent.Success
            }
            is DataResponse.Error -> {
                _uiCurrencyState.value = UIEvent.Error(currenciesData.error.message)
                _supportedCurrencies.value = null

            }
        }
    }

    private fun isCurrencyAndAmountValid(amount: Double?, selectedCurrency: String?): Boolean =
        amount != null && !selectedCurrency.isNullOrBlank()

}