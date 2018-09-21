package com.themovielist.model.response

import com.google.gson.annotations.SerializedName
import com.themovielist.model.MovieModel
import java.util.*

class MovieDetailResponseModel(id: Int = 0, posterPath: String, overview: String,
                               title: String, voteAverage: Double, releaseDate: Date? = null,
                               backdropPath: String, genreIdList: IntArray) :
        MovieModel(id, posterPath, overview, title, voteAverage, releaseDate, backdropPath, genreIdList) {

    @SerializedName("reviews")
    lateinit var reviewsResponseModel: PaginatedArrayResponseModel<MovieReviewModel>

    @SerializedName("trailers")
    lateinit var trailerResponseModel: MovieDetailTrailerResponseModel

    @SerializedName("runtime")
    var runtime = 0
}

data class MovieDetailTrailerResponseModel constructor(
        @SerializedName("youtube")
        val trailerList: List<MovieTrailerModel>)

data class MovieTrailerModel constructor(@SerializedName("size") val size: String,
                                         @SerializedName("source") val source: String,
                                         @SerializedName("name") val name: String)

data class MovieReviewModel constructor(@SerializedName("author") val author: String,
                                        @SerializedName("content") val content: String)