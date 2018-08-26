package com.themovielist.repository

import retrofit2.Retrofit

abstract class RepositoryBase<T> constructor(private val retrofit: Retrofit) {
    abstract val serviceInstanceType: Class<T>
    protected val mApiInstance by lazy { retrofit.create(serviceInstanceType) }
}