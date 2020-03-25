package com.themovielist.repository.movie

import com.themovielist.model.response.MovieResponseModel
import com.themovielist.model.response.MovieDetailResponseModel
import com.themovielist.model.response.PaginatedArrayResponseModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IMovieService {
    @GET("movie/top_rated")
    suspend fun getTopRatedList(@Query("page") pageNumber: Int?): PaginatedArrayResponseModel<MovieResponseModel>

    @GET("movie/popular")
    suspend fun getPopularList(@Query("page") pageNumber: Int?): PaginatedArrayResponseModel<MovieResponseModel>

    @GET("movie/{movieId}?append_to_response=reviews,trailers")
    suspend fun getMovieDetail(@Path("movieId") movieId: Int): MovieDetailResponseModel
}