package com.themovielist.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.themovielist.model.MovieModel
import com.themovielist.model.response.Resource
import com.themovielist.model.view.MovieImageGenreViewModel
import com.themovielist.repository.home.HomeRepository
import com.themovielist.util.ApiUtil
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import kotlinx.coroutines.experimental.Job
import javax.inject.Inject

class HomeViewModel @Inject constructor(homeRepository: HomeRepository): ViewModel() {
    val isLoading = MutableLiveData<Boolean>()

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