package com.task.currencyapp.data.remote.baseclient.interfaces


import com.task.currencyapp.data.remote.baseclient.ApiResponse
import com.task.currencyapp.data.remote.baseclient.BaseApiResponse
import com.task.currencyapp.data.remote.baseclient.erros.ApiError
import retrofit2.Response

internal interface IRepository {
    suspend fun <T : BaseApiResponse> executeSafely(call: suspend () -> Response<T>): ApiResponse<T>
    suspend fun <T> executeSafelyRaw(call: suspend () -> Response<T>): Response<T>?
    fun <T > detectError(response: Response<T>): ApiError
}