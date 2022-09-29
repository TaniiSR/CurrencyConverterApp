package com.task.currencyapp.utils.base

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicInteger

class SingleClickEvent : MutableLiveData<Int>() {
    companion object {
        private val TAG = "SingleClickEvent"
    }

    private val defaultValue: Int = -1
    private val debounceDelay: Long = 600L
    private val mPending = AtomicInteger(defaultValue)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in Int>) {
        if (hasActiveObservers()) {
            Log.w(TAG, "Multiple observers registered but only one will be notified of changes.")
        }

        // Observe the internal MutableLiveData
        super.observe(owner, Observer { t ->
            if (mPending.get() != defaultValue) {
                observer.onChanged(t)
                Handler(Looper.getMainLooper()).postDelayed(
                    { mPending.set(defaultValue) },
                    debounceDelay
                )
            }
        })
    }

    override fun postValue(value: Int) {
        if (mPending.get() == defaultValue) {
            super.postValue(value)
        }
    }

    @MainThread
    override fun setValue(t: Int) {
        if (mPending.get() == defaultValue) {
            mPending.set(t)
            super.setValue(t)
        }
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        setValue(0)
    }

}
