package com.task.currencyapp.domain.base

data class DataError(
    val code: Int,
    val message: String = "",
)