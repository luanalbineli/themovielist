package com.themovielist.ui.base

import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.themovielist.model.response.Result
import com.themovielist.model.view.MovieModel
import com.themovielist.repository.movie.MovieRepository
import com.themovielist.repository.movie.MovieStore

open class MovieViewModel @ViewModelInject constructor(
    private val movieRepository: MovieRepository,
    private val movieStore: MovieStore
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

    private val mMovieChangedObserver = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            (sender as? ObservableField<MovieModel>)?.get()?.let { movieModel ->
                onMovieChanged(movieModel)
            }
        }

    }

    init {
        movieStore.addObserver(mMovieChangedObserver)
    }

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

    protected fun updateMovieList(
        movieList: List<MovieModel>,
        movieModel: MovieModel
    ): List<MovieModel>? {
        val index =
            movieList.indexOfFirst { it.movieResponseModel.id == movieModel.movieResponseModel.id }
        if (index == -1) {
            return null
        }

        val mutableMovieList = movieList.toMutableList()
        mutableMovieList[index] = movieModel
        return mutableMovieList
    }

    private fun updatedMovie(updatedMovieModel: MovieModel): LiveData<Result<MovieModel>> =
        movieRepository.updateMovie(viewModelScope, updatedMovieModel)

    override fun onCleared() {
        super.onCleared()
        movieStore.removeObserver(mMovieChangedObserver)
    }

    protected open fun onMovieChanged(movieModel: MovieModel) {}
}