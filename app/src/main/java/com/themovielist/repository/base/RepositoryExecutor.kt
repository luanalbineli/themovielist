package com.themovielist.repository.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import retrofit2.Retrofit
import javax.inject.Inject
import com.themovielist.model.response.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RepositoryExecutor @Inject constructor(
        private val retrofit: Retrofit
) {
    fun <R, T> makeRequest(clazz: Class<T>,
                           viewModelScope: CoroutineScope,
                           asyncRequest: suspend CoroutineScope.(serviceInstance: T) -> R): LiveData<Result<R>> {
        val result = MutableLiveData<Result<R>>()
        result.value = Result.loading()

        // TODO: Handle internet connection
        /*val isConnected = networkStatus.haveActiveConnection()
        Timber.d("Is connected: $isConnected")
        if (!isConnected) {
            result.value = Result.error(RequestErrorModel(NoInternetConnectionException()))
            return result
        }*/

        viewModelScope.launch {
            try {
                var asyncRequestResult: R? = null
                withContext(Dispatchers.Default) {
                    val apiServiceInstance = retrofit.create(clazz)
                    asyncRequestResult = asyncRequest(apiServiceInstance)
                }
                result.value = Result.success(asyncRequestResult)
            } catch (exception: Exception) {
                result.value = Result.error(exception)
            }
        }

        return result
    }

    fun <T> getServiceInstance(clazz: Class<T>) = retrofit.create(clazz)
}