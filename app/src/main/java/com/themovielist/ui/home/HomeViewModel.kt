package com.themovielist.ui.home

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.themovielist.model.response.Result
import com.themovielist.model.view.MovieImageGenreViewModel
import com.themovielist.repository.home.HomeRepository
import com.themovielist.ui.common.MovieCommonAction
import com.themovielist.util.ApiUtil
import kotlinx.coroutines.experimental.Job
import javax.inject.Inject

class HomeViewModel @Inject constructor(
        private val homeRepository: HomeRepository,
        private val movieCommonAction: MovieCommonAction
): ViewModel(), MovieCommonAction by movieCommonAction {

    private val disposableParentJob = Job()
    val moviesSortedByRating = MediatorLiveData<Result<List<MovieImageGenreViewModel>>>()
    val moviesSortedByPopularity = homeRepository.getMoviesSortedByPopularity(ApiUtil.INITIAL_PAGE_INDEX, disposableParentJob)

    init {
        moviesSortedByRating.addSource(homeRepository.getMoviesSortedByRating(ApiUtil.INITIAL_PAGE_INDEX, disposableParentJob)) {}
    }

    fun tryLoadAgain() {
        moviesSortedByRating.addSource(homeRepository.getMoviesSortedByRating(ApiUtil.INITIAL_PAGE_INDEX, disposableParentJob)) {}
    }

    override fun onCleared() {
        disposableParentJob.cancel()
        super.onCleared()
    }
}