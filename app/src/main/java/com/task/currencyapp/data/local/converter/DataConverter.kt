package com.task.currencyapp.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.task.currencyapp.data.local.entities.CurrencyEntity
import com.task.currencyapp.data.local.entities.ExchangeRateEntity

class DataConverter {
    @TypeConverter
    fun fromRateList(rates: List<ExchangeRateEntity?>?): String? {
        if (rates == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<ExchangeRateEntity?>?>() {}.type
        return gson.toJson(rates, type)
    }

    @TypeConverter
    fun toRateList(rates: String?): List<ExchangeRateEntity?>? {
        if (rates == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<ExchangeRateEntity?>?>() {}.type
        return gson.fromJson(rates, type)
    }

    @TypeConverter
    fun fromCurrencyList(currencies: List<CurrencyEntity>?): String? {
        if (currencies == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<CurrencyEntity>?>() {}.type
        return gson.toJson(currencies, type)
    }

    @TypeConverter
    fun toCurrencyList(currencies: String?): List<CurrencyEntity>? {
        if (currencies == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<CurrencyEntity>?>() {}.type
        return gson.fromJson(currencies, type)
    }
}