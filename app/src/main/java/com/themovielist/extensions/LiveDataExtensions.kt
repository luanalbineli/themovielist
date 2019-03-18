package com.themovielist.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.safeNullObserve(owner: LifecycleOwner, observer: (value: T) -> Unit) {
    this.observe(owner, Observer { value -> value?.let { observer(value) } })
}