package com.themovielist.repository.movie

import com.themovielist.model.response.MovieDetailResponseModel
import retrofit2.http.GET
import retrofit2.http.Path

interface IMovieService {
    @GET("movie/{movieId}?append_to_response=reviews,trailers")
    suspend fun getMovieDetail(@Path("movieId") movieId: Int): MovieDetailResponseModel
}