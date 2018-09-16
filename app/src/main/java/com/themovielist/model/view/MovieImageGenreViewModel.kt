package com.themovielist.model.view

import android.os.Parcelable
import com.themovielist.model.GenreModel
import com.themovielist.model.MovieModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieImageGenreViewModel(
        val genreList: List<GenreModel>?,
        val movieModel: MovieModel,
        var isFavorite: Boolean) : Parcelable