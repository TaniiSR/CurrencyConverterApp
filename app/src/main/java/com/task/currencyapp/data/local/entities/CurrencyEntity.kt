package com.task.currencyapp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency")
data class CurrencyEntity(
    @PrimaryKey
    val currencyCode: String,
    @ColumnInfo(name = "currencyName")
    val currencyName: String
)