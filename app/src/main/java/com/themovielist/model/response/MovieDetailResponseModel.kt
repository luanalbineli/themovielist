package com.themovielist.model.response

import com.google.gson.annotations.SerializedName

data class MovieDetailResponseModel constructor(
        @SerializedName("reviews")
        val reviews: PaginatedArrayResponseModel<MovieReviewModel>,

        @SerializedName("trailers")
        val trailers: MovieDetailTrailerResponseModel,

        @SerializedName("runtime")
        val runtime: Int
)

data class MovieDetailTrailerResponseModel constructor(
        @SerializedName("youtube")
        val trailerList: List<MovieTrailerModel>)

data class MovieTrailerModel constructor(@SerializedName("size") val size: String,
                                         @SerializedName("source") val source: String,
                                         @SerializedName("name") val name: String,
                                         @SerializedName("type") val type: String)

data class MovieReviewModel constructor(@SerializedName("author") val author: String,
                                        @SerializedName("content") val content: String)