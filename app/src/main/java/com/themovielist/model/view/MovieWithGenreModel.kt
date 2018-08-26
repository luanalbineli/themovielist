package com.themovielist.model.view

import android.os.Parcel
import android.os.Parcelable
import com.themovielist.model.GenreModel
import com.themovielist.model.MovieModel

class MovieWithGenreModel constructor(var movieModel: MovieModel, @Transient var genreList: List<GenreModel>? = null): Parcelable {
    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    constructor(parcel: Parcel) : this(
            parcel.readParcelable(MovieModel::class.java.classLoader),
            parcel.createTypedArrayList(GenreModel))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(movieModel, flags)
        parcel.writeTypedList(genreList)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<MovieWithGenreModel> {
        override fun createFromParcel(parcel: Parcel): MovieWithGenreModel =
                MovieWithGenreModel(parcel)

        override fun newArray(size: Int): Array<MovieWithGenreModel?> = arrayOfNulls(size)
    }

    fun concatenatedGenres(): String {
        return genreList?.let {
            return if (it.isEmpty()) {
                ""
            } else {
                it.map { it.name }.reduce { a, b -> "$a, $b" }
            }
        } ?: ""
    }
}