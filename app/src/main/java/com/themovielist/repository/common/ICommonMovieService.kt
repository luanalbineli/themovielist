package com.themovielist.repository.common

import com.themovielist.model.response.ConfigurationResponseModel
import com.themovielist.model.response.GenreListResponseModel
import io.reactivex.Single
import kotlinx.coroutines.experimental.Deferred
import retrofit2.Call
import retrofit2.http.GET

interface ICommonMovieService {
    @GET("genre/movie/list")
    fun getAllGenres(): Deferred<GenreListResponseModel>

    @GET("configuration")
    fun getConfiguration(): Deferred<ConfigurationResponseModel>
}