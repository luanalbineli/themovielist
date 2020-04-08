package com.themovielist.ui.base

import androidx.lifecycle.LiveData
import com.themovielist.model.response.Result
import com.themovielist.model.view.MovieModel
import kotlinx.coroutines.CoroutineScope

interface IMovieActions {
    val showMovieDetail: LiveData<MovieModel>
    val toggleMovieFavorite: LiveData<Result<MovieModel>>
    val toggleMovieWatched: LiveData<Result<MovieModel>>

    fun showMovieDetail(movieModel: MovieModel)
    fun toggleMovieFavorite(movieModel: MovieModel)
    fun toggleMovieWatched(movieModel: MovieModel)
}