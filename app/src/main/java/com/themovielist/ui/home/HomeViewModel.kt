package com.themovielist.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.themovielist.model.response.Result
import com.themovielist.model.view.CompleteMovieModel
import com.themovielist.repository.home.HomeRepository
import com.themovielist.ui.base.BaseViewModel
import com.themovielist.ui.common.MovieCommonAction
import com.themovielist.util.ApiUtil
import timber.log.Timber
import javax.inject.Inject

class HomeViewModel @Inject constructor(
        private val homeRepository: HomeRepository,
        private val movieCommonAction: MovieCommonAction
): BaseViewModel(), MovieCommonAction by movieCommonAction {

    init {
        Timber.d("Starting the HomeViewModel")
    }

    private lateinit var _moviesSortedByRating: LiveData<Result<List<CompleteMovieModel>>>
    val moviesSortedByRating = MediatorLiveData<Result<List<CompleteMovieModel>>>()

    private lateinit var _moviesSortedByPopularity: LiveData<Result<List<CompleteMovieModel>>>
    val moviesSortedByPopularity = MediatorLiveData<Result<List<CompleteMovieModel>>>()

    init {
        fetchMovieSortedByRating()
        fetchMoviesSortedByPopularity()
    }

    fun tryLoadAgain() {

    }

    fun fetchMovieSortedByRating() {
        if (::_moviesSortedByRating.isInitialized) {
            moviesSortedByRating.removeSource(_moviesSortedByRating)
        }
        _moviesSortedByRating = homeRepository.getMoviesSortedByRating(ApiUtil.INITIAL_PAGE_INDEX, viewModelJob)
        moviesSortedByRating.addSource(_moviesSortedByRating) {
            moviesSortedByRating.value = it
        }
    }

    fun fetchMoviesSortedByPopularity() {
        if (::_moviesSortedByPopularity.isInitialized) {
            moviesSortedByPopularity.removeSource(_moviesSortedByRating)
        }
        _moviesSortedByPopularity = homeRepository.getMoviesSortedByPopularity(ApiUtil.INITIAL_PAGE_INDEX, viewModelJob)
        moviesSortedByPopularity.addSource(_moviesSortedByPopularity) {
            moviesSortedByPopularity.value = it
        }
    }
}