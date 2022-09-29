package com.task.currencyapp.data.remote.baseclient.erros

import com.google.gson.annotations.SerializedName

data class ApiError(
    @SerializedName("code") var statusCode: Int,
    @SerializedName("message") var message: String = "",
    @SerializedName("actualCode") var actualCode: String = "-1"
)