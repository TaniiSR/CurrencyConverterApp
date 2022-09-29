package com.task.currencyapp.domain.datadtos

import com.task.currencyapp.domain.base.BaseDataResponse

data class ExchangeRateDTO(
    val base: String,
    val rateList: List<ExchangeRate>
) : BaseDataResponse()
