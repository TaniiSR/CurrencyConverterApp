package com.task.currencyapp.utils.base.sealed

sealed class UIEvent {
    object Loading : UIEvent()
    object Success : UIEvent()
    class Message(val message: String = "") : UIEvent()
    class Error(val message: String = "") : UIEvent()
}