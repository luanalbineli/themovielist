package com.themovielist.ui.moviedetail

import androidx.lifecycle.ViewModel
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

    private val disposableParentJob = Job()
   /* val moviesSortedByRating = homeRepository.getMoviesSortedByRating(ApiUtil.INITIAL_PAGE_INDEX, disposableParentJob)
    val moviesSortedByPopularity = homeRepository.getMoviesSortedByPopularity(ApiUtil.INITIAL_PAGE_INDEX, disposableParentJob)*/

    override fun onCleared() {
        disposableParentJob.cancel()
        super.onCleared()
    }
}