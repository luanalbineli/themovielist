package com.themovielist.model.view

import com.themovielist.model.GenreModel
import com.themovielist.model.MovieModel

data class MovieImageGenreViewModel(
        val genreList: List<GenreModel>?,
        val movieModel: MovieModel,
        val isFavorite: Boolean)