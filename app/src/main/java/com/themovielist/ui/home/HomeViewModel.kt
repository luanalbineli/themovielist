package com.themovielist.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.themovielist.model.response.Result
import com.themovielist.model.view.MovieImageGenreViewModel
import com.themovielist.repository.home.HomeRepository
import com.themovielist.ui.base.BaseViewModel
import com.themovielist.ui.common.MovieCommonAction
import com.themovielist.util.ApiUtil
import kotlinx.coroutines.Job
import javax.inject.Inject

class HomeViewModel @Inject constructor(
        private val homeRepository: HomeRepository,
        private val movieCommonAction: MovieCommonAction
): BaseViewModel(), MovieCommonAction by movieCommonAction {

    private lateinit var _moviesSortedByRating: LiveData<Result<List<MovieImageGenreViewModel>>>
    val moviesSortedByRating = MediatorLiveData<Result<List<MovieImageGenreViewModel>>>()

    private lateinit var _moviesSortedByPopularity: LiveData<Result<List<MovieImageGenreViewModel>>>
    val moviesSortedByPopularity = MediatorLiveData<Result<List<MovieImageGenreViewModel>>>()

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