package com.themovielist.domain

import androidx.lifecycle.MediatorLiveData
import com.themovielist.model.response.Result

abstract class MediatorUseCase<in P, R> {
    protected val result = MediatorLiveData<Result<R>>()
    fun observe(): MediatorLiveData<Result<R>> {
        return result
    }

    abstract fun execute(params: P)
}