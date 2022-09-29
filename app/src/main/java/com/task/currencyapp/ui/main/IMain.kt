package com.task.currencyapp.ui.main

import androidx.lifecycle.LiveData
import com.task.currencyapp.domain.datadtos.Currency
import com.task.currencyapp.domain.datadtos.ExchangeRate
import com.task.currencyapp.domain.datadtos.ExchangeRateDTO
import com.task.currencyapp.ui.main.adapter.RateListAdapter
import com.task.currencyapp.utils.base.interfaces.IBaseViewModel
import com.task.currencyapp.utils.base.sealed.UIEvent

interface IMain : IBaseViewModel {
    val supportedCurrencies: LiveData<List<Currency>>
    val exchangeRates: LiveData<ExchangeRateDTO>
    var selectedCurrency: Currency?
    val rateListAdapter: RateListAdapter
    val uiCurrencyState: LiveData<UIEvent>
    val uiRateState: LiveData<UIEvent>
    fun dataRequestCurrenciesAndRates()
    fun getExchangeRateListForSelectedCurrency(
        list: List<ExchangeRate>?,
        amount: Double?,
        sourceCurrency: String?,
        baseCurrency: String
    ): List<ExchangeRate>
}