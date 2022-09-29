package com.task.currencyapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.task.currencyapp.data.local.converter.DataConverter
import com.task.currencyapp.data.local.entities.CurrencyEntity
import com.task.currencyapp.data.local.entities.ExchangeRateEntity
import com.task.currencyapp.data.local.localservice.ExchangeLocalDao

@Database(
    entities = [CurrencyEntity::class, ExchangeRateEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DataConverter::class)
abstract class ExchangeAppDB : RoomDatabase() {
    abstract fun exchangeLocalDao(): ExchangeLocalDao
}