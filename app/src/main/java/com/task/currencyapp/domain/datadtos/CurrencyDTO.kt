package com.task.currencyapp.domain.datadtos

import com.task.currencyapp.domain.base.BaseDataResponse

data class CurrencyDTO(
    val currencies: List<Currency>
) : BaseDataResponse()
