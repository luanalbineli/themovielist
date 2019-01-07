package com.themovielist.repository.home

import com.themovielist.model.MovieModel
import com.themovielist.model.response.PaginatedArrayResponseModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface IHomeService {
    @GET("movie/top_rated")
    fun getTopRatedList(@Query("page") pageNumber: Int?): Deferred<PaginatedArrayResponseModel<MovieModel>>

    @GET("movie/popular")
    fun getPopularList(@Query("page") pageNumber: Int?): Deferred<PaginatedArrayResponseModel<MovieModel>>
}