package com.task.currencyapp.data.remote.baseclient

import com.task.currencyapp.data.remote.baseclient.erros.ApiError
import com.task.currencyapp.data.remote.baseclient.erros.ServerError
import com.task.currencyapp.data.remote.baseclient.interfaces.IRepository
import com.task.currencyapp.data.remote.baseclient.sealed.NetworkErrors
import retrofit2.Response
import com.google.gson.stream.MalformedJsonException as MalformedJsonException1

abstract class BaseRepository : IRepository {
    val MALFORMED_JSON_EXCEPTION_CODE = 0

    private val defaultErrorMessage =
        "Sorry, that doesn't look right. We’re working on fixing it now. Please try again in sometime."
    private val defaultConnectionErrorMessage =
        "Looks like you're offline. Please reconnect and refresh to continue."
    private val defaultSessionMessage =
        "Your session has been expired"
    private val defaultMessage: String = "Something went wrong."
    private val accessDeniedErrorMsg: String = "You don't have access to this information."

    override suspend fun <T : BaseApiResponse> executeSafely(call: suspend () -> Response<T>): ApiResponse<T> {
        try {
            val response: Response<T> = call.invoke()
            if (response.isSuccessful) {
                return ApiResponse.Success(response.code(), response.body()!!)
            }

            // Check if this is not a server side error (4** or 5**) then return error instead of success
            return ApiResponse.Error(detectError(response))

        } catch (exception: MalformedJsonException1) {
            return ApiResponse.Error(
                ApiError(
                    MALFORMED_JSON_EXCEPTION_CODE,
                    exception.localizedMessage ?: ""
                )
            )
        } catch (exception: Exception) {
            return ApiResponse.Error(
                ApiError(
                    getDefaultCode(),
                    exception.localizedMessage ?: ""
                )
            )
        }
    }

    override suspend fun <T> executeSafelyRaw(call: suspend () -> Response<T>): Response<T>? {
        try {
            val response: Response<T> = call.invoke()
            if (response.isSuccessful) {
                return response
            }
            return response
        } catch (exception: MalformedJsonException1) {
            return null
        } catch (exception: Exception) {
            return null
        }
    }

    override fun <T> detectError(response: Response<T>): ApiError {
        return when (response.code()) {
            401 -> getApiError(mapError(NetworkErrors.SessionExpire, response.code()))
            403 -> getApiError(mapError(NetworkErrors.Forbidden, response.code()))
            404 -> getApiError(mapError(NetworkErrors.NotFound, response.code()))
            502 -> getApiError(mapError(NetworkErrors.BadGateway, response.code()))
            504 -> getApiError(mapError(NetworkErrors.NoInternet, response.code()))
            in 400..500 -> getApiError(mapError(NetworkErrors.InternalServerError, response.code()))
            -1009 -> getApiError(mapError(NetworkErrors.NoInternet, response.code()))
            -1001 -> getApiError(mapError(NetworkErrors.RequestTimedOut, response.code()))
            else -> {
                getApiError(mapError(NetworkErrors.UnknownError(), response.code()))
            }
        }
    }

    private fun getApiError(error: ServerError): ApiError {
        return ApiError(
            error.code ?: getDefaultCode(),
            error.message ?: defaultErrorMessage,
            error.actualCode
        )
    }

    private fun mapError(error: NetworkErrors, code: Int = 0): ServerError {
        return when (error) {

            is NetworkErrors.NoInternet, NetworkErrors.RequestTimedOut -> ServerError(
                code,
                defaultConnectionErrorMessage
            )

            is NetworkErrors.BadGateway -> ServerError(code, defaultErrorMessage)
            is NetworkErrors.NotFound -> ServerError(code, defaultErrorMessage)
            is NetworkErrors.Forbidden -> ServerError(code, accessDeniedErrorMsg)
            is NetworkErrors.InternalServerError -> ServerError(code, defaultMessage)
            is NetworkErrors.SessionExpire -> ServerError(code, defaultSessionMessage)
            is NetworkErrors.UnknownError -> ServerError(code, defaultErrorMessage)
            else -> ServerError(code, defaultErrorMessage)
        }
    }

    private fun getDefaultCode(): Int {
        return 0
    }

}
