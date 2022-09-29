package com.task.currencyapp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exchange_rate")
data class ExchangeRateEntity(
    @PrimaryKey
    @ColumnInfo(name = "currencyCode")
    val currencyCode: String,
    @ColumnInfo(name = "exchangeRate")
    val exchangeRate: Double,
    @ColumnInfo(name = "baseCurrency")
    val baseCurrency: String
)