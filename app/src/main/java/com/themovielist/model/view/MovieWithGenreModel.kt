package com.themovielist.model.view

import com.themovielist.model.GenreModel
import com.themovielist.model.MovieModel

data class MovieWithGenreModel constructor(var movieModel: MovieModel, @Transient var genreList: List<GenreModel>? = null)