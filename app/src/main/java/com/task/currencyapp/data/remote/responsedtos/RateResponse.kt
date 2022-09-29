package com.task.currencyapp.data.remote.responsedtos

import com.google.gson.annotations.SerializedName
import com.task.currencyapp.data.remote.baseclient.BaseApiResponse

data class RateResponse(
    @SerializedName("base")
    val base: String? = null,
    @SerializedName("rates")
    val rates: Map<String, Double>? = null
) : BaseApiResponse()
