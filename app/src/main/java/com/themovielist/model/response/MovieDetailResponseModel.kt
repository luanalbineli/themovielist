package com.themovielist.model.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class MovieDetailResponseModel constructor(
        @SerializedName("id")
        var id: Int,

        @SerializedName("poster_path")
        val posterPath: String?,

        @SerializedName("overview")
        val overview: String,

        @SerializedName("title")
        val title: String,

        @SerializedName("vote_average")
        val voteAverage: Double,

        @SerializedName("release_date")
        val releaseDate: Date?,

        @SerializedName("backdrop_path")
        val backdropPath: String?,

        @SerializedName("genre_ids")
        val genreIdList: IntArray,
        @SerializedName("reviews")
        val reviews: PaginatedArrayResponseModel<MovieReviewModel>,

        @SerializedName("trailers")
        val trailers: MovieDetailTrailerResponseModel,

        @SerializedName("runtime")
        val runtime: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MovieDetailResponseModel

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}

data class MovieDetailTrailerResponseModel constructor(
        @SerializedName("youtube")
        val trailerList: List<MovieTrailerModel>)

data class MovieTrailerModel constructor(@SerializedName("size") val size: String,
                                         @SerializedName("source") val source: String,
                                         @SerializedName("name") val name: String,
                                         @SerializedName("type") val type: String)

data class MovieReviewModel constructor(@SerializedName("author") val author: String,
                                        @SerializedName("content") val content: String)