package com.task.currencyapp.domain.interfaces

import com.task.currencyapp.domain.base.DataResponse
import com.task.currencyapp.domain.datadtos.CurrencyDTO

interface ICurrencyDataRepo {
    suspend fun getAllCurrencies(): DataResponse<CurrencyDTO>
}