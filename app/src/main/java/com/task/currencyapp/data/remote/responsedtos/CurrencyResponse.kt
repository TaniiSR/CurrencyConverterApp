package com.task.currencyapp.data.remote.responsedtos

import com.google.gson.annotations.SerializedName
import com.task.currencyapp.data.remote.baseclient.BaseApiResponse

data class CurrencyResponse(
    @SerializedName("currencies")
    val currencies: Map<String, String>? = null
): BaseApiResponse()
