package com.themovielist.model.view

import com.themovielist.model.response.MovieDetailResponseModel
import com.themovielist.model.response.MovieResponseModel
import com.themovielist.model.response.PaginatedArrayResponseModel

data class MovieDetailModel(
    val movieDetailResponseModel: MovieDetailResponseModel,
    val movieRecommendationList: List<MovieModel>
)