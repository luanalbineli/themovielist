package com.themovielist.repository.common

import com.themovielist.model.response.genre.GenreListResponseModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface ICommonMovieService {
    @GET("genre/movie/list")
    suspend fun getAllGenres(): GenreListResponseModel
}