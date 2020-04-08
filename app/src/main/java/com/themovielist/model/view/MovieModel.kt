package com.themovielist.model.view

import android.os.Parcelable
import com.themovielist.model.entity.MovieEntityModel
import com.themovielist.model.response.MovieResponseModel
import com.themovielist.model.response.genre.GenreResponseModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieModel(val movieResponseModel: MovieResponseModel,
                      val isFavorite: Boolean,
                      val isWatched: Boolean,
                      val genreList: List<GenreResponseModel>): Parcelable {
    fun toEntity() = MovieEntityModel(
        id = movieResponseModel.id,
        title = movieResponseModel.title,
        backdropPath = movieResponseModel.backdropPath,
        genreIdList = movieResponseModel.genreIdList,
        overview = movieResponseModel.overview,
        posterPath = movieResponseModel.posterPath,
        releaseDate = movieResponseModel.releaseDate,
        voteAverage = movieResponseModel.voteAverage,
        isWatched = isWatched,
        isFavorite = isFavorite
    )
}