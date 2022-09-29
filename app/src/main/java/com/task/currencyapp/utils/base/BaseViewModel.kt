package com.task.currencyapp.utils.base

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.currencyapp.utils.base.interfaces.IBaseViewModel
import kotlinx.coroutines.*


abstract class BaseViewModel : ViewModel(), IBaseViewModel {
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override fun launch(dispatcher: CoroutineDispatcher, block: suspend () -> Unit): Job {
        return viewModelScope.launch(dispatcher) { block() }
    }

    fun <T> launchAsync(block: suspend () -> T): Deferred<T> =
        viewModelScope.async(Dispatchers.IO) {
            block()
        }

    override fun onClick(view: View) {
        clickEvent.setValue(view.id)
    }

}