package com.themovielist.repository.movie

import com.themovielist.model.response.MovieDetailResponseModel
import com.themovielist.model.response.MovieResponseModel
import com.themovielist.model.response.PaginatedArrayResponseModel
import com.themovielist.model.response.credit.MovieCreditsResponseModel
import retrofit2.http.GET
import retrofit2.http.Path

interface IMovieService {
    @GET("movie/{movieId}?append_to_response=reviews,trailers")
    suspend fun getMovieDetail(@Path("movieId") movieId: Int): MovieDetailResponseModel

    @GET("movie/{movieId}/recommendations")
    suspend fun getMovieRecommendationList(@Path("movieId") movieId: Int): PaginatedArrayResponseModel<MovieResponseModel>

    @GET("movie/{movieId}/credits")
    suspend fun getMovieCredits(@Path("movieId") movieId: Int): MovieCreditsResponseModel
}