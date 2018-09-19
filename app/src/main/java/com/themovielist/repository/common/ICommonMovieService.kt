package com.themovielist.repository.common

import com.themovielist.model.response.GenreListResponseModel
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET

interface ICommonMovieService {
    @GET("genre/movie/list")
    fun getAllGenres(): Deferred<GenreListResponseModel>
}