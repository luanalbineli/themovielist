package com.themovielist.repository.base

import kotlinx.coroutines.*

abstract class Repository<T>(private val repositoryExecutor: RepositoryExecutor) {
    abstract val serviceInstanceType: Class<T>

    val serviceInstance: T by lazy { repositoryExecutor.getServiceInstance(serviceInstanceType) }

    protected fun <R> makeRequest(viewModelScope: CoroutineScope, call: suspend CoroutineScope.(serviceInstance: T) -> R)
            = repositoryExecutor.makeRequest(serviceInstanceType, viewModelScope, call)

}