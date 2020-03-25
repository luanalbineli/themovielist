package com.themovielist.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class MovieResponseModel constructor(@SerializedName("id")
                                          val id: Int,

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
                                          val genreIdList: IntArray): Parcelable {

    override fun equals(other: Any?): Boolean {
        if (other == null || !MovieResponseModel::class.java.isAssignableFrom(other.javaClass)) {
            return false
        }

        return id == (other as MovieResponseModel).id
    }

    override fun hashCode(): Int {
        return id
    }
}
