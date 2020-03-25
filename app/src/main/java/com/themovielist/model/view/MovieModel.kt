package com.themovielist.model.view

import android.os.Parcelable
import com.themovielist.model.response.MovieResponseModel
import com.themovielist.model.response.genre.GenreResponseModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieModel(val movieResponseModel: MovieResponseModel,
                      val isFavorite: Boolean,
                      val isWatched: Boolean,
                      val genreList: List<GenreResponseModel>): Parcelable