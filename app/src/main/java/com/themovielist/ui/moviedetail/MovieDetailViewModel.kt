package com.themovielist.ui.moviedetail

import androidx.lifecycle.*
import com.themovielist.model.MovieModel
import com.themovielist.model.response.MovieDetailResponseModel
import com.themovielist.model.response.MovieReviewModel
import com.themovielist.model.response.MovieTrailerModel
import com.themovielist.model.response.Result
import com.themovielist.model.view.MovieImageGenreViewModel
import com.themovielist.repository.home.HomeRepository
import com.themovielist.repository.movie.MovieRepository
import com.themovielist.ui.common.MovieCommonAction
import com.themovielist.util.ApiUtil
import kotlinx.coroutines.experimental.Job
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(
        movieRepository: MovieRepository,
        private val movieCommonAction: MovieCommonAction
): ViewModel(), MovieCommonAction by movieCommonAction {
    private val parentDisposableJob = Job()

    private val movieImageGenreViewModel = MutableLiveData<MovieImageGenreViewModel>()
    private val _movieImageGenreViewModel = MediatorLiveData<MovieImageGenreViewModel>()

    private val _movieModel = MutableLiveData<MovieModel>()
    val movieModel: LiveData<MovieModel>
        get() = _movieModel

    private val _trailerList = MutableLiveData<List<MovieTrailerModel>>()
    val trailerList: LiveData<List<MovieTrailerModel>>
        get() = _trailerList

    private val _reviewList = MutableLiveData<List<MovieReviewModel>>()
    val reviewList: LiveData<List<MovieReviewModel>>
        get() = _reviewList

    private val _runtime = MutableLiveData<Int>()
    val runtime: LiveData<Int>
        get() = _runtime

    init {
        _movieImageGenreViewModel.addSource(movieImageGenreViewModel) { it ->
            _movieModel.postValue(it.movieModel)

            //movieRepository.getMovieDetail(it.movieModel.id, parentDisposableJob).
        }
    }

    fun setMovieImageGenreViewModel(movieImageGenreViewModel: MovieImageGenreViewModel) {
        _movieImageGenreViewModel.postValue(movieImageGenreViewModel)
    }

    override fun onCleared() {
        parentDisposableJob.cancel()
        super.onCleared()
    }
}