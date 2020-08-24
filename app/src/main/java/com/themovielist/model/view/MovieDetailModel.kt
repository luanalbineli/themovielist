package com.themovielist.model.view

import com.themovielist.model.response.MovieDetailResponseModel
import com.themovielist.model.response.credit.MovieCreditsResponseModel

data class MovieDetailModel(
    val movieDetailResponseModel: MovieDetailResponseModel,
    val movieRecommendationList: List<MovieModel>,
    val movieCreditResponseModel: MovieCreditsResponseModel
)