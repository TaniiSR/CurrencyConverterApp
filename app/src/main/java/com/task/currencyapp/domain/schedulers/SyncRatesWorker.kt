package com.task.currencyapp.domain.schedulers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.task.currencyapp.data.local.entities.ExchangeRateEntity
import com.task.currencyapp.data.local.localservice.ExchangeRepoDbService
import com.task.currencyapp.data.remote.baseclient.ApiResponse
import com.task.currencyapp.data.remote.services.exchangerateservice.ExchangeRateApi
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SyncRatesWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val remoteRepo: ExchangeRateApi,
    private val localRepo: ExchangeRepoDbService,
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        return when (val response = remoteRepo.getLatestExchangeRates()) {
            is ApiResponse.Success -> {
                localRepo.insertExchangeRates(response.data.rates?.map { rate ->
                    ExchangeRateEntity(
                        currencyCode = rate.key,
                        exchangeRate = rate.value,
                        baseCurrency = response.data.base ?: "USD"
                    )
                } ?: listOf())
                Result.success()
            }
            is ApiResponse.Error -> {
                Result.failure()
            }
        }
    }
}