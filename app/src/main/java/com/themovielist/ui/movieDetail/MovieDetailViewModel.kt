package com.themovielist.ui.movieDetail

import androidx.lifecycle.*
import com.themovielist.di.module.ApiConfigurationFactory
import com.themovielist.model.response.MovieDetailResponseModel
import com.themovielist.model.response.Result
import com.themovielist.model.view.MovieModel
import com.themovielist.repository.movie.MovieRepository
import com.themovielist.repository.movie.MovieStore
import com.themovielist.ui.base.MovieViewModel
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(
    val apiConfigurationFactory: ApiConfigurationFactory,
    private val movieRepository: MovieRepository,
    movieStore: MovieStore
) : MovieViewModel(movieRepository, movieStore) {
    private val mMovie = MutableLiveData<MovieModel>()
    val movie: LiveData<MovieModel>
        get() = mMovie

    private val mMovieDetail = MediatorLiveData<Result<MovieDetailResponseModel>>()
    val movieDetail: LiveData<Result<MovieDetailResponseModel>>
        get() = mMovieDetail

    fun init(movieModel: MovieModel) {
        mMovie.postValue(movieModel)

        mMovieDetail.addSource(movieRepository.getMovieDetail(viewModelScope, movieModel.movieResponseModel.id)) {
            mMovieDetail.postValue(it)
        }
    }

    fun toggleMovieFavorite() {
        movie.value?.let { movieModel -> toggleMovieFavorite(movieModel) }
    }

    fun toggleMovieWatched() {
        movie.value?.let { movieModel -> toggleMovieWatched(movieModel) }
    }

    override fun onMovieChanged(movieModel: MovieModel) {
        mMovie.postValue(movieModel)
    }

}