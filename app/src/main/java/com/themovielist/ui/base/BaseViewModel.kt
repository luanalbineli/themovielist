package com.themovielist.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job

abstract class BaseViewModel : ViewModel() {
    protected val viewModelJob by lazy { Job() }

    override fun onCleared() {
        viewModelJob.cancel()
    }
}