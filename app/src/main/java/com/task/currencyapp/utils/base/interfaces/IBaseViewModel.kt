package com.task.currencyapp.utils.base.interfaces

import com.task.currencyapp.utils.base.SingleClickEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

interface IBaseViewModel {
    val clickEvent: SingleClickEvent
    fun onClick(view: android.view.View)
    fun launch(dispatcher: CoroutineDispatcher = Dispatchers.IO, block: suspend () -> Unit): Job
}