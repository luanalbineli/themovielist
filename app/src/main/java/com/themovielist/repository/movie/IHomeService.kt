package com.themovielist.repository.movie

import com.themovielist.model.MovieModel
import com.themovielist.model.response.PaginatedArrayResponseModel
import io.reactivex.Single
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface IMovieService {
    @GET("movie/top_rated")
    fun getTopRatedList(@Query("page") pageNumber: Int?): Deferred<PaginatedArrayResponseModel<MovieModel>>

    @GET("movie/popular")
    fun getPopularList(@Query("page") pageNumber: Int?): Deferred<PaginatedArrayResponseModel<MovieModel>>
}