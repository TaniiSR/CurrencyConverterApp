package com.task.currencyapp.data.remote.baseclient

import android.os.Environment
import com.task.currencyapp.BuildConfig
import okhttp3.Cache
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class  RetroNetwork @Inject constructor() {
    private val timeoutRead = 60L   //In seconds
    private val diskCacheSize = (10 * 1024 * 1024).toLong()
    private val timeoutConnect = 60L   //In seconds
    private val contentType = "Content-Type"
    private val contentTypeValue = "application/json"

    private val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
    private var retrofit: Retrofit

    private val headerInterceptor = Interceptor { chain ->
        val original = chain.request()
        val url: HttpUrl = original.url.newBuilder()
            .build()

        val request = original.newBuilder()
            .header(contentType, contentTypeValue)
            .method(original.method, original.body)
            .url(url)
            .build()

        chain.proceed(request)
    }
    private val logger: HttpLoggingInterceptor
        get() {
            val loggingInterceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                loggingInterceptor.apply { level = HttpLoggingInterceptor.Level.BODY }
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
            return loggingInterceptor
        }

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val okHttpClient: OkHttpClient
        get() {
            okHttpBuilder.addInterceptor(headerInterceptor)
            okHttpBuilder.addInterceptor(logger)
            okHttpBuilder.connectTimeout(timeoutConnect, TimeUnit.SECONDS)
            okHttpBuilder.readTimeout(timeoutRead, TimeUnit.SECONDS)
            okHttpBuilder.writeTimeout(timeoutRead, TimeUnit.SECONDS)
            okHttpBuilder.retryOnConnectionFailure(true)
            okHttpBuilder.cache(getDiskCache())
            return okHttpBuilder.build()
        }

    private fun getDiskCache(): Cache {
        val cacheDir = File(Environment.getDataDirectory(), "cache")
        return Cache(cacheDir, diskCacheSize)
    }

    fun <S> createService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }

}
