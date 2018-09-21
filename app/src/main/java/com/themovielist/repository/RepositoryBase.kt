package com.themovielist.repository

import androidx.lifecycle.MutableLiveData
import com.themovielist.model.response.Result
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import retrofit2.Retrofit

abstract class RepositoryBase<T> constructor(private val retrofit: Retrofit) {
    abstract val serviceInstanceType: Class<T>
    protected val apiInstance: T by lazy { retrofit.create(serviceInstanceType) }

    protected fun <T> executeAsyncRequest(parentDisposableJob: Job, asyncRequest: suspend CoroutineScope.() -> T): MutableLiveData<Result<T>> {
        val result = MutableLiveData<Result<T>>()
        result.value = Result.loading()
        launch(parent = parentDisposableJob, context = IO) {
            try {
                withContext(UI) { result.value = Result.success(asyncRequest()) }
            } catch (exception: Exception) {
                withContext(UI) { result.value = Result.error(exception) }
            }
        }

        return result
    }
}