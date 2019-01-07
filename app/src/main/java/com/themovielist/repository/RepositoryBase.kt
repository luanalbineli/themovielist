package com.themovielist.repository

import androidx.lifecycle.MutableLiveData
import com.themovielist.model.response.Result
import kotlinx.coroutines.*
import retrofit2.Retrofit
import timber.log.Timber

abstract class RepositoryBase<T> constructor(private val retrofit: Retrofit) {
    abstract val serviceInstanceType: Class<T>
    protected val apiInstance: T by lazy { retrofit.create(serviceInstanceType) }

    protected fun <T> executeAsyncRequest(viewModelJob: Job, asyncRequest: suspend CoroutineScope.() -> T): MutableLiveData<Result<T>> {
        val result = MutableLiveData<Result<T>>()
        result.value = Result.loading()
        CoroutineScope(Dispatchers.Default + viewModelJob).launch {
            try {
                val asyncRequestResult = asyncRequest()
                withContext(Dispatchers.Main) { result.value = Result.success(asyncRequestResult) }
            } catch (exception: Exception) {
                Timber.e(exception, "An error occurred while tried to execute the request")
                withContext(Dispatchers.Main) { result.value = Result.error(exception) }
            }
        }

        return result
    }
}