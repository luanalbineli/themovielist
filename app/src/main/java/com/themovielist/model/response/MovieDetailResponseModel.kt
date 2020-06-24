package com.themovielist.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class MovieDetailResponseModel constructor(
    @SerializedName("reviews")
        val reviews: PaginatedArrayResponseModel<MovieReviewResponseModel>,

    @SerializedName("trailers")
        val trailers: MovieDetailTrailerResponseModel,

    @SerializedName("runtime")
        val runtime: Int
)

data class MovieDetailTrailerResponseModel constructor(
        @SerializedName("youtube")
        val trailerList: List<MovieTrailerResponseModel>)

@Parcelize
data class MovieTrailerResponseModel constructor(@SerializedName("size") val size: String?,
                                                 @SerializedName("source") val source: String,
                                                 @SerializedName("name") val name: String,
                                                 @SerializedName("type") val type: String): Parcelable

data class MovieReviewResponseModel constructor(@SerializedName("author") val author: String,
                                                @SerializedName("content") val content: String)