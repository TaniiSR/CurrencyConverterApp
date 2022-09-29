package com.task.currencyapp.data.remote.baseclient

import com.task.currencyapp.data.remote.baseclient.erros.ApiError


sealed class ApiResponse<out T : BaseApiResponse> {
    data class Success<out T : BaseApiResponse>(val code: Int, val data: T) : ApiResponse<T>()
    data class Error(val error: ApiError) : ApiResponse<Nothing>()
}