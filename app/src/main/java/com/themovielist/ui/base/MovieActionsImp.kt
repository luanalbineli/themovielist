package com.themovielist.ui.base

import androidx.lifecycle.*
import com.themovielist.model.response.Result
import com.themovielist.model.view.MovieModel
import com.themovielist.repository.movie.MovieRepository

abstract class MovieViewModel
constructor(
    private val movieRepository: MovieRepository
) : IMovieActions, ViewModel() {
    private val mShowMovieDetail = MutableLiveData<MovieModel>()
    override val showMovieDetail: LiveData<MovieModel>
        get() = mShowMovieDetail

    private val mToggleMovieFavorite = MediatorLiveData<Result<MovieModel>>()
    override val toggleMovieFavorite: LiveData<Result<MovieModel>>
        get() = mToggleMovieFavorite

    private val mToggleMovieWatched = MediatorLiveData<Result<MovieModel>>()
    override val toggleMovieWatched: LiveData<Result<MovieModel>>
        get() = mToggleMovieWatched

    override fun showMovieDetail(movieModel: MovieModel) {
        mShowMovieDetail.value = movieModel
    }

    override fun toggleMovieFavorite(movieModel: MovieModel) {
        val updatedMovieModel = movieModel.copy(isFavorite = movieModel.isFavorite.not())
        mToggleMovieFavorite.addSource(updatedMovie(updatedMovieModel)) { result ->
            mToggleMovieFavorite.value = result
        }
    }

    override fun toggleMovieWatched(movieModel: MovieModel) {
        val updatedMovieModel = movieModel.copy(isWatched = movieModel.isWatched.not())
        mToggleMovieWatched.addSource(updatedMovie(updatedMovieModel)) { result ->
            mToggleMovieWatched.value = result
        }
    }

    private fun updatedMovie(updatedMovieModel: MovieModel): LiveData<Result<MovieModel>>
        = movieRepository.updateMovie(viewModelScope, updatedMovieModel)
}