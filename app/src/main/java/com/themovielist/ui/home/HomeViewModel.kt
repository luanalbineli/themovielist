package com.themovielist.ui.home

import androidx.lifecycle.ViewModel
import com.themovielist.repository.home.HomeRepository
import com.themovielist.ui.common.MovieCommonAction
import com.themovielist.ui.common.MovieCommonActionDelegate
import com.themovielist.util.ApiUtil
import kotlinx.coroutines.experimental.Job
import javax.inject.Inject

class HomeViewModel @Inject constructor(homeRepository: HomeRepository, movieCommonAction: MovieCommonAction): ViewModel(), MovieCommonAction by movieCommonAction {
    private val disposableParentJob = Job()
    val moviesSortedByRating = homeRepository.getMoviesSortedByRating(ApiUtil.INITIAL_PAGE_INDEX, disposableParentJob)
    val moviesSortedByPopularity = homeRepository.getMoviesSortedByPopularity(ApiUtil.INITIAL_PAGE_INDEX, disposableParentJob)

    init {

    }

    override fun onCleared() {
        disposableParentJob.cancel()
        super.onCleared()
    }
}